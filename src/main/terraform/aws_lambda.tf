# http://tonym.us/using-aws-lambda-for-web-video-transcoding.html

resource "aws_iam_role" "lambda_s3_exec_role" {
    name = "lambda_s3_exec_role"
    assume_role_policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Action": "sts:AssumeRole",
      "Effect": "Allow",
      "Principal": {
        "Service": "lambda.amazonaws.com"
      },
    }
  ]
}
EOF
}

resource "aws_iam_role_policy" "lambda_policy" {
    name = "lambda_policy"
    role = "${aws_iam_role.lambda_s3_exec_role.id}"
    policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Action": [
        "elastictranscoder:Read*",
        "elastictranscoder:List*",
        "elastictranscoder:*Job",
        "elastictranscoder:*Preset",
        "s3:List*",
        "iam:List*",
        "sns:List*"
      ],
      "Effect": "Allow",
      "Resource": "*"
    },
    {
      "Effect": "Allow",
      "Action": [
        "logs:CreateLogGroup",
        "logs:CreateLogStream",
        "logs:PutLogEvents"
      ],
      "Resource": "arn:aws:logs:*:*:*"
    },
    {
      "Effect": "Allow",
      "Action": [
        "s3:GetObject",
        "s3:PutObject"
      ],
      "Resource": [
        "arn:aws:s3:::*"
      ]
    }
  ]
}
EOF
}

# resource "aws_lambda_function" "test_lambda" {
#     filename = "lambda_function_payload.zip"
#     function_name = "lambda_function_name"
#     role = "${aws_iam_role.iam_for_lambda.arn}"
#     handler = "exports.test"
# }
