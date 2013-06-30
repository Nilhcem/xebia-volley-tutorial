var fs = require('fs');
var express = require('express');
var app = express();

app.get('/orgs/:organization/members', function(req, res) {
  fs.readFile('github_members.json', 'utf-8', function (err, data) {
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
