var fs = require('fs');
var express = require('express');
var app = express();

app.get('/api/v2/:account/videos.json' , function(req, res) {
  fs.readFile('xebia_videos.json', 'utf-8', function (err, data) {
    res.type('application/json; charset=utf-8');
    if (err) {
      res.send(500);
    } else {
      res.send(data);
    }
  });
});

app.get('^*$', function(req, res) {
 res.type('text/plain');
 res.send('Nothing to see here');
});

app.listen(8080);
