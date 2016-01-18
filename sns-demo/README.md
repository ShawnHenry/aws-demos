# SNS Endpoint demo using Spring

## Provisioning

You'll need to run the demo somewhere publically available, so that AWS can
send messages to the app. For example, an EC2 instance or DigitalOcean
droplet.

## Running the app

1. Build it with `mvn clean package`

2. Copy the jar file from `target/` to the public host

3. Run it with `java -jar sns-demo-0.0.1-SNAPSHOT.jar`


## Setting up SNS

1. In the [SNS console](https://console.aws.amazon.com/sns/v2/home), create
   a new topic. Call it whatever you want. Copy the ARN for a subsequent
   step.

2. Create an HTTP subscription using the ARN from above and a URL to your
   app e.g. [http://1.2.3.4/notify](http://1.2.3.4/notify).

4. The demo app's logs should show AWS sending a subscription request.


## Sending a test message

1. Ihe SNS console, with your topic selected, click "Publish to topic".

2. Select "JSON" as the message type.

3. Enter the following in the message box.

    `{ "default": "{\"foo\": \"bar\"}" }`

4. The demo app's logs should show AWS delivering the message and the demo
   app logging the payload.
