resource "aws_s3_bucket" "video_input_bucket" {
    bucket = "${var.aws_conf.input_bucket}"
    acl = "public-read"

    tags {
        Name = "Video Transcoding Input Bucket - ${var.aws_conf.environment}"
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

resource "aws_s3_bucket" "video_output_bucket" {
    bucket = "${var.aws_conf.output_bucket}"
    acl = "public-read"

    tags {
        Name = "Video Transcoding Output Bucket - ${var.aws_conf.environment}"
        Environment = "${var.aws_conf.environment}"
    }
}
