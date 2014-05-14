var config                  = require('./config');
var passport                = require('passport');
var BasicStrategy           = require('passport-http').BasicStrategy;
var ClientPasswordStrategy  = require('passport-oauth2-client-password').Strategy;
var BearerStrategy          = require('passport-http-bearer').Strategy;
var UserModel               = require('./mongoose').UserModel;
var ClientModel             = require('./mongoose').ClientModel;
var AccessTokenModel        = require('./mongoose').AccessTokenModel;
var RefreshTokenModel       = require('./mongoose').RefreshTokenModel;
var search                  = require('./searchUser.js')


passport.use(new ClientPasswordStrategy(
    function(clientId, clientSecret, done) {
        console.log("in basic stsrategy");
        ClientModel.findOne({ clientId: clientId }, function(err, client) {
            if (err) { return done(err); }
            if (!client) { return done(null, false); }
            if (client.clientSecret != clientSecret) { return done(null, false); }

            return done(null, client);
        });
    }
));


passport.use(new BasicStrategy(
    function(username, password, done) {
        console.log('in basic strategy');
        search.findUser(username);
      //  UserModel.findOne({ userId: username }, function(err, user) {
      //       if (err) { return done(err); }
      //      if (!user) { return done(null, false); }
      //      if (user.clientSecret != password) { return done(null, false); }

      //      return done(null, client);
        //});
    }
));



passport.use(new
 BearerStrategy(
    function(accessToken, done) {
                    console.log('in bearers strategy');

        AccessTokenModel.findOne({ token: accessToken }, function(err, token) {
            if (err) { return done(err); }
            if (!token) { return done(null, false); }

            if( Math.round((Date.now()-token.created)/1000) > config.get('security:tokenLife') ) {
                AccessTokenModel.remove({ token: accessToken }, function (err) {
                    if (err) return done(err);
                });
                return done(null, false, { message: 'Token expired' });
            }

            UserModel.findById(token.userId, function(err, user) {
                if (err) { return done(err); }
                if (!user) { return done(null, false, { message: 'Unknown user' }); }

                var info = { scope: '*' }
                done(null, user, info);
            });
        });
    }
));