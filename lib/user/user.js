'use strict';

var mongoose = require("mongoose"),
    Schema = mongoose.Schema;

//
// User Page Schema
//
var User = new Schema({
    address: { type: String, default: ''},
});


module.exports = mongoose.model("User", User);
