variable "AWS_ACCESS_KEY" {}
variable "AWS_SECRET_KEY" {}

variable "aws_conf" {
  default = {
    region = "__REGION__"
    user = "__USER__"
    group = "__GROUP__"
    environment = "__ENVIRONMENT__"
    input_bucket = "__INPUT_BUCKET__"
    output_bucket = "__OUTPUT_BUCKET__"
  }
}
