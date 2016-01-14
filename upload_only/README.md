# S3 Direct Upload Demo using Spring

# Provisioning

There is a [Terraform]() configuration under
`src/main/terraform`. You can build the necessary infrastructure to run
each demo as follows.

1. Change into `src/main/terraform` under the demo directory.

2. Set the following environment variables:
  * `TF_VAR_AWS_SECRET_KEY`
  * `TF_VAR_AWS_ACCESS_KEY`

3. Configure the remaining AWS variables in
   `variables.tf`.

4. Run the following to check all is well:

    `terraform plan`

5. Apply the config:

    `terraform apply`

# Running the demo

1. Edit `src/main/resources/application.properties` to set the appropriate
   values.

2. Run maven with `mvn`.

3. Navigate to [http://localhost:8080/](http://localhost:8080/).
