# S3 Direct Upload And Video Transcoding Demo With SNS Notifications

## Provisioning Part 1

The same caveats about provision apply here as in the
[video_transcode](../video_transcode/) demo. Furthermore, in order for the
application to receive notifications from SNS, it must be possible for AWS to
make a call to an HTTP(S) endpoint. That means that this demo app must be hosted
somewhere that AWS can access.

1. Change into `src/main/terraform` under the demo directory.

2. Set the following environment variables:
  * `TF_VAR_AWS_SECRET_KEY`
  * `TF_VAR_AWS_ACCESS_KEY`

3. Configure the remaining AWS variables in
   `variables.tf`.

4. Disable the Lambda and SNS config:

    `mv aws_lambda.tf aws_lambda.tf.disabled`
    `mv aws_sns.tf aws_sns.tf.disabled`

5. Run the following to check all is well:

    `terraform plan`

6. Apply the config:

    `terraform apply`

7. Log in to the [AWS console](https://console.aws.amazon.com/) and create
   an Elastic Transcoder pipeline. It needs to read from and write to the
   S3 buckets created by Terraform. Note the pipeline ID once it is
   created.

8. Update `src/main/js/index.js` with the pipeline ID.

9. Re-enable the Terraform Lambda config, in `src/main/terraform`:

    `mv aws_lambda.tf.disabled aws_lambda.tf`

10. Run the plan again to check all is well:

    `terraform plan`

11. Apply the remaining config:

    `terraform apply`

12. In the [S3 Console](https://console.aws.amazon.com/s3/home), open the
    input bucket's properties. Change the Events sections and add a
    notification on `ObjectCreated` to call the lambda created above.

## Installing the demo

1. Build the app with:

    `mvn package`

2. Deploy it somewhere accessible from the public internet e.g. ECS,
DigitalOcean, etc. Alongside the jar, you'll need an appropriately configured `application.properties`.

## Provisioning Part 2

1. With the app running, we can now create the SNS topic and subscribe the app
as an endpoint for it. Re-enable the Terraform file:

    `mv aws_sns.tf.disabled aws_sns.tf`

2. Ensure the HTTP endpoint is configured in `variables.tf`.

3. Run the plan again to check all is well:

    `terraform plan`

4. Apply the remaining config:

    `terraform apply`

5. Check in the [SNS Console](https://console.aws.amazon.com/sns/v2/home) that
the endpoint has successfully confirmed itself as an endpoint.

## Running the demo

3. Navigate to wherever you hosted your application.

4. Upload a video.

5. After a short delay, check the [Elastic
   Transcoder](https://console.aws.amazon.com/elastictranscoder/home)
   console to see if a job has been scheduled. If not, check the [Lamba
   console](https://console.aws.amazon.com/lambda/home) and see if there
   were any errors.

6. TODO - where can we check for received notifications?
