var aws = require('aws-sdk');
var elastictranscoder = new aws.ElasticTranscoder();

var config = {
  prefix: 'transcoder/output/',
  pipeline: {
    name: '__ELASTIC_TRANSCODER_PIPELINE_NAME__', // for information only
    id: '__ELASTIC_TRANSCODER_PIPELINE_ID__'
  }
};

function basename(filename) {
  return filename.split('/').reverse()[0].split('.')[0];
}

// return output file name with timestamp and extension
function outputKey(name, ext) {
   return name + '-' + Date.now().toString() + '.' + ext;
}

exports.handler = function(event, context) {
    console.log('Received event:', JSON.stringify(event, null, 2));
    // Get the object from the event and show its content type
    var key = decodeURIComponent(event.Records[0].s3.object.key.replace(/\+/g, ' '));
    var params = {
      Input: {
        Key: key
      },
      PipelineId: config.pipeline.id,
      OutputKeyPrefix: config.prefix,
      Outputs: [
        {
          Key: outputKey(basename(key), 'mp4'),
          PresetId: '1351620000001-100070', // "System preset: Web"
        }
      ]
    };

    elastictranscoder.createJob(params, function(err, data) {
      if (err){
        console.log(err, err.stack); // an error occurred
        context.fail();
        return;
      }
      context.succeed();
    });
};
