# AWS demos using Spring Boot

This repository contains a number of demonstration projects, showing how to
integrate a Spring Boot application with AWS services. The demos are as
follows:

* [upload_only](upload_only/) - directly uploading files from a browser to an S3 bucket.
* [video_transcode](video_transcode/) - upload to S3, then kick off an
  [Elastic Transcoder](https://aws.amazon.com/elastictranscoder/) job via a
  [Lambda](https://aws.amazon.com/lambda/).
* [sns-demo](sns-demo) - Receiving notifications from
  [SNS](https://aws.amazon.com/sns/).
