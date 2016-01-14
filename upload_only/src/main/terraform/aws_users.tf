resource "aws_iam_user" "test-user" {
    name = "${var.aws_conf.user}"
    path = "/"
}

resource "aws_iam_group" "test-group" {
    name = "${var.aws_conf.group}"
    path = "/"
}

resource "aws_iam_group_membership" "group-user-membership" {
    name = "group-user-membership"
    users = [
        "${aws_iam_user.test-user.name}",
    ]
    group = "${aws_iam_group.test-group.name}"
}

resource "aws_iam_group_policy" "group_policy" {
    name = "${var.aws_conf.group}_policy"
    group = "${aws_iam_group.test-group.id}"
    policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Effect": "Allow",
      "Action": "s3:*",
      "Resource": "${aws_s3_bucket.input_bucket.arn}/*"
    }
  ]
}
EOF
}

resource "aws_iam_access_key" "access-key" {
    user = "${aws_iam_user.test-user.name}"
}
