# S3 Direct Upload Demo using Spring

This is a demonstration of how to perform direct uploads to AWS's S3
service from a browser. It's mostly based upon the blog post "[Direct
Browser Uploading – Amazon S3, CORS, FileAPI, XHR2 and Signed
PUTs](http://www.ioncannon.net/programming/1539/direct-browser-uploading-amazon-s3-cors-fileapi-xhr2-and-signed-puts/)",
with some Java inspiration from the
[EvaporateJS](https://github.com/TTLabs/EvaporateJS/blob/master/example/SigningExample.java)
repo.

The demos are as follows:

* [upload_only](upload_only/) - just uploading files to an S3 bucket.
* [video_transcode](video_transcode/) - upload to S3, then kick off an
  [Elastic Transcoder](https://aws.amazon.com/elastictranscoder/) job via
  [Lambda](https://aws.amazon.com/lambda/).
