package com.example.demo.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.ServletException;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SignResource {

	private static final Logger LOG = LoggerFactory.getLogger(SignResource.class);

	@Value("${aws.s3.accessKey}")
	private String S3_ACCESS_KEY;

	@Value("${aws.s3.secretKey}")
	private String S3_SECRET_KEY;

	@Value("${aws.s3.bucket}")
	private String S3_BUCKET;

	@Value("https://${aws.s3.bucket}.s3-${aws.s3.region}.amazonaws.com")
	private String S3_URL;

	private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";

	private int EXPIRE_DURATION = 60 * 5; // 5 minutes


	@RequestMapping(value = "/signput", method = RequestMethod.GET)
	public ResponseEntity signPut(@RequestParam("name") String name, @RequestParam("mimeType") String mimeType)
		throws ServletException, URISyntaxException {

		if (name == null || name.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}
		if (mimeType == null || mimeType.isEmpty()) {
			return ResponseEntity.badRequest().build();
		}

		long expireTime = System.currentTimeMillis() / 1000 + EXPIRE_DURATION;

		String amzHeaders = "x-amz-acl:public-read";

		String stringToSign = String.format("PUT\n\n%s\n%d\n%s\n/%s/%s",
			mimeType, expireTime, amzHeaders, S3_BUCKET, name);

		String signed = calculateRFC2104HMAC(stringToSign, S3_SECRET_KEY);

		URI uri = new URI(String.format("%s/%s?AWSAccessKeyId=%s&Expires=%d&Signature=%s",
			S3_URL, name, S3_ACCESS_KEY, expireTime, signed));

		Map<String,Object> responseBody = new HashMap<>();
		responseBody.put("uri", uri);

		return ResponseEntity.ok(responseBody);
	}

	/**
	 * Largely taken from:
	 *
	 * https://github.com/TTLabs/EvaporateJS/blob/master/example/SigningExample.java
	 * @param data
	 * @param key
	 * @return
	 * @throws ServletException
     */
	public static String calculateRFC2104HMAC(String data, String key) throws ServletException
	{
		try
		{
			// get an hmac_sha1 key from the raw key bytes
			SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);

			// get an hmac_sha1 Mac instance and initialize with the signing key
			Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
			mac.init(signingKey);

			// compute the hmac on input data bytes
			byte[] rawHmac = mac.doFinal(data.getBytes());

			// base64-encode the hmac
			return new String(Base64.encodeBase64(rawHmac));
		}
		catch (Exception e)
		{
			LOG.error("Failed to generate HMAC", e);
			throw new ServletException("Failed to generate HMAC: " + e.getMessage());
		}
	}
}
