var config                  = require('./config');
var passport                = require('passport');
var BasicStrategy           = require('passport-http').BasicStrategy;
var ClientPasswordStrategy  = require('passport-oauth2-client-password').Strategy;
var BearerStrategy          = require('passport-http-bearer').Strategy;
var UserModel               = require('./mongoose').UserModel;
var ClientModel             = require('./mongoose').ClientModel;
var AccessTokenModel        = require('./mongoose').AccessTokenModel;
var RefreshTokenModel       = require('./mongoose').RefreshTokenModel;
var auth = require('./auth.js')


exports.findUser = function(userId) {
    UserModel.findOne({ email: userId }, function(err, user) {
        if(err) {return false;}
        if(!user){return findUserByHandle()} 
        console.log("user found - %s", user.username)
        return user;
    });
}
function findUserByHandle(res, err) {
	    UserModel.findOne({ handle: userId }, function(err, user) {
        if(err) {return false;}
        if(!user){return false;}  
        console.log("user found - %s", user.username)
        return user;
    });
}