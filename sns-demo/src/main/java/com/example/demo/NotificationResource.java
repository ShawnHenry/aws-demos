package com.example.demo;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.PublicKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Map;
import javax.inject.Inject;

import com.amazonaws.services.sns.util.SignatureChecker;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/notify", method = RequestMethod.POST)
@ResponseStatus(HttpStatus.NO_CONTENT)
public class NotificationResource {

	private static Logger LOG = org.slf4j.LoggerFactory.getLogger(NotificationResource.class);

	@Inject
	private ObjectMapper mapper;

	@SuppressWarnings("unchecked")
	@RequestMapping(headers = "x-amz-sns-message-type=SubscriptionConfirmation")
	public void handleSubscriptionMessage(@RequestBody String payload) throws IOException {
		LOG.info("Subscribing");
		Map<String,String> request = (Map<String,String>) mapper.readValue(payload, Map.class);

		ResponseEntity<String> response = new RestTemplate().getForEntity(request.get("SubscribeURL"), String.class);

		LOG.info("{}", response);
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(headers = "x-amz-sns-message-type=Notification")
	public void handleNotificationMessage(@RequestBody String payload) throws IOException, CertificateException {
		Map<String,String> request = (Map<String,String>) mapper.readValue(payload, Map.class);
		String signingCertURL = request.get("SigningCertURL");
		new SignatureChecker().verifySignature(request, getSigningKey(signingCertURL));
		LOG.info("Payload: {}", payload);
		LOG.info("Message: {}", mapper.readValue(request.get("Message"), Map.class));
	}

	@RequestMapping(headers = "x-amz-sns-message-type=UnsubscribeConfirmation")
	public void handleUnsubscribeMessage() {
		LOG.info("Unsubscribe received");
	}

	private PublicKey getSigningKey(String urlStr) throws IOException, CertificateException {
		URL url = new URL(urlStr);
		InputStream inStream = url.openStream();
		CertificateFactory cf = CertificateFactory.getInstance("X.509");
		X509Certificate cert = (X509Certificate) cf.generateCertificate(inStream);
		return cert.getPublicKey();
	}
}
