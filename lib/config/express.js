'use strict';

var express = require('express');
var morgan = require('morgan');
var bodyParser = require('body-parser');
var cookieParser = require('cookie-parser');
var methodOverride = require('method-override');
var session = require('express-session');
var path = require('path');
var expressValidator = require('express-validator');

// Express configuration1
module.exports = function (app, passport) {

	app.use('/public', express.static(path.resolve(__dirname, '../..') + '/public'));
	app.set('view engine', 'ejs');
	app.set('views', 'views/');	
	app.use(morgan('dev'));
	app.use(methodOverride());
	app.use(cookieParser());
	app.use(bodyParser.json({ type: 'application/json' }))
	app.use(bodyParser.urlencoded({ extended: true}))
	app.use(session({
		secret: 'threshold', 
		resave: true, 
		saveUninitialized: true
	}));
}
