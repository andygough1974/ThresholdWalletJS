'use strict';

var express = require('express');
var mongoose = require('mongoose');
var bunyan = require('bunyan');
var commander = require('commander');
var config = require('./lib/config/');
var app = express();
var log = bunyan.createLogger({
    name: 'Threshold'
});

commander
	.version('0.0.1')
	.option('-p, --port <Number>', 'Setting port', parseInt)
	.parse(process.argv);
 
config.port = commander.port || config.port;
require('./lib/config/express')(app);
require('./lib/app')(app);
require('./lib/api')(app);

app.listen(config.port, function() {
    log.info('Express is Running and Listening on port ' + config.port);
});

module.exports = app;
