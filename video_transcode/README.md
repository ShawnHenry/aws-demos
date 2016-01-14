# S3 Direct Upload And Video Transcoding Demo

# Provisioning

Unlike the [upload only](../upload_only/) demo, provisioning for this demo
is harder because it's not yet possible to automate all the steps with
Terraform. There is a configuration under `src/main/terraform` but it's not
complete (it lacks Elastic Transcoder support).

1. Change into `src/main/terraform` under the demo directory.

2. Set the following environment variables:
  * `TF_VAR_AWS_SECRET_KEY`
  * `TF_VAR_AWS_ACCESS_KEY`

3. Configure the remaining AWS variables in
   `variables.tf`.

4. Disable the Lambda config:

    `mv aws_lambda.tf aws_lambda.tf.disabled`

5. Run the following to check all is well:

    `terraform plan`

6. Apply the config:

    `terraform apply`

7. Log in to the [AWS console](https://console.aws.amazon.com/) and create
   an Elastic Transcoder pipeline. It needs to read form and write to the
   S3 buckets created by Terraform. Note the pipeline ID once it is
   created.

8. Update `src/main/js/index.js` with the pipeline ID.

9. Re-enable the Terraform Lambda config, in `src/main/terraform`:

    `mv aws_lambda.tf.disabled aws_lambda.tf`

10. Run the plan again to check all is well:

    `terraform plan`

11. Apply the remainin config:

    `terraform apply`

12. In the [S3 Console](https://console.aws.amazon.com/s3/home), open the
    input bucket's properties. Change the Events sections and add a
    notification on `ObjectCreated` to call the lambda created above.

# Running the demo

1. Edit `src/main/resources/application.properties` to set the appropriate
   values.

2. Run maven with `mvn`.

3. Navigate to [http://localhost:8080/](http://localhost:8080/).

4. Upload a video.

5. After a short delay, check the [Elastic
   Transcoder](https://console.aws.amazon.com/elastictranscoder/home)
   console to see if a job has been scheduled. If not, check the [Lamba
   console](https://console.aws.amazon.com/lambda/home) and see if there
   were any errors.
