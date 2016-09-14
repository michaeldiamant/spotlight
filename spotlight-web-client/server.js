var express = require('express');
var server = express();
server.use('/', express.static(__dirname + '/public'));

var port = 3000;
server.listen(port);
console.log("Listening on port " + port);
