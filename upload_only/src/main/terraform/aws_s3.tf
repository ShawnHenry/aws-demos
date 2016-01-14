resource "aws_s3_bucket" "input_bucket" {
    bucket = "${var.aws_conf.input_bucket}"
    acl = "private"

    tags {
        Name = "Input Bucket - ${var.aws_conf.environment}"
        Environment = "${var.aws_conf.environment}"
    }

    cors_rule {
        allowed_headers = [
            "Content-Type",
            "x-amz-acl",
            "origin"
        ]
        allowed_methods = ["PUT"]
        allowed_origins = ["*"]
        max_age_seconds = 3000
    }
}
