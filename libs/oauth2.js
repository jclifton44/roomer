var oauth2orize         = require('oauth2orize');
var passport            = require('passport');
var crypto              = require('crypto');
var config              = require('./config.js');
var UserModel           = require('./mongoose.js').UserModel;
var ClientModel         = require('./mongoose.js').ClientModel;
var AccessTokenModel    = require('./mongoose.js').AccessTokenModel;
var RefreshTokenModel   = require('./mongoose.js').RefreshTokenModel;
var AuthorizationCodeModel   = require('./mongoose.js').AuthorizationCodeModel;


// create OAuth 2.0 server
var server = oauth2orize.createServer();

// Exchange username & password for access token.
server.exchange(oauth2orize.exchange.code(function(client, username, password, scope, done) {
    UserModel.findOne({ username: username }, function(err, user) {
        console.log("hey");
        if (err) { return done(err); }
        if (!user) { return done(null, false); }
        if (!user.checkPassword(password)) { return done(null, false); }

        RefreshTokenModel.remove({ userId: user.userId, clientId: client.clientId }, function (err) {
            if (err) return done(err);
        });
        AccessTokenModel.remove({ userId: user.userId, clientId: client.clientId }, function (err) {
            if (err) return done(err);
        });

        var tokenValue = crypto.randomBytes(32).toString('base64');
        var refreshTokenValue = crypto.randomBytes(32).toString('base64');
        var token = new AccessTokenModel({ token: tokenValue, clientId: client.clientId, userId: user.userId });
        var refreshToken = new RefreshTokenModel({ token: refreshTokenValue, clientId: client.clientId, userId: user.userId });
        refreshToken.save(function (err) {
            if (err) { return done(err); }
        });
        var info = { scope: '*' }
        token.save(function (err, token) {
            if (err) { return done(err); }
            done(null, tokenValue, refreshTokenValue, { 'expires_in': config.get('security:tokenLife') });
        });
    });
}));

// Exchange refreshToken for access token.
server.exchange(oauth2orize.exchange.refreshToken(function(client, refreshToken, scope, done) {
    RefreshTokenModel.findOne({ token: refreshToken }, function(err, token) {
        console.log("aye");
        if (err) { return done(err); }
        if (!token) { return done(null, false); }
        if (!token) { return done(null, false); }

        UserModel.findById(token.userId, function(err, user) {
            if (err) { return done(err); }
            if (!user) { return done(null, false); }

            RefreshTokenModel.remove({ userId: user.userId, clientId: client.clientId }, function (err) {
                if (err) return done(err);
            });
            AccessTokenModel.remove({ userId: user.userId, clientId: client.clientId }, function (err) {
                if (err) return done(err);
            });

            var tokenValue = crypto.randomBytes(32).toString('base64');
            var refreshTokenValue = crypto.randomBytes(32).toString('base64');
            var token = new AccessTokenModel({ token: tokenValue, clientId: client.clientId, userId: user.userId });
            var refreshToken = new RefreshTokenModel({ token: refreshTokenValue, clientId: client.clientId, userId: user.userId });
            refreshToken.save(function (err) {
                if (err) { return done(err); }
            });
            var info = { scope: '*' }
            token.save(function (err, token) {
                if (err) { return done(err); }
                done(null, tokenValue, refreshTokenValue, { 'expires_in': config.get('security:tokenLife') });
            });
        });
    });
}));

// token endpoint
exports.token = [
    passport.authenticate(['basic', 'oauth2-client-password'], { session: false }),
    server.token(),
    server.errorHandler()
]

exports.requestToken = function(req, res) {
    var code = req.body.code;
    var grantType = req.body.grant_type;
    if( grantType == 'authorization_code' ) {
        AuthorizationCodeModel.findOne ({ token: code }, function(err, authorizationCode) {
            if(err) {
                res.end();
            }
            if(!authorizationCode){
                res.end();
            }
            var accessToken = crypto.randomBytes(32).toString('base64');
            console.log(accessToken);
            var AccessToken = new AccessTokenModel({ userId: authorizationCode.userId, clientId: authorizationCode.userId, token: accessToken, scope:authorizationCode.scope});
            AccessToken.save(function (err) {
            if (err) { console.log(err);}
            else {console.log("New token made - %s",Date.now); }
            });
        });
    }
}

exports.requestGrant = function(req, res) {
    if( req.query.response_type == 'code' ) {
        var id = req.query.client_id;
        var redirect_uri = req.query.redirect_uri;
        var scope = req.query.scope;
        var state = req.query.state
        /*ClientModel.findOne ({ client : id }, function(err, client) {
            if(err) {}
            if(!client){

            }
            
        });*/
        var AuthValue = crypto.randomBytes(32).toString('base64');
        console.log(AuthValue);
        var AuthorizationCode = new AuthorizationCodeModel({ userId: req.params.userId, scope: req.query.scope, clientId: id, token: AuthValue});
        AuthorizationCode.save(function (err) {
            if (err) { console.log(err);}
            else {console.log("New authCode - %s",Date.now); }
        });
        res.send({code:AuthValue, state:'0'});
        res.end();
        console.log(req.params.userId);
    } else if( req.query.response_type == 'token' ) {
        console.log(req.query.responsetype);
    } else if( req.body.grant_type == 'password' ) {
        console.log(req.body.grant_type);
    } else if( req.body.grant_type == 'client_credentials' ) {
        console.log(req.body.grant_type);
    }
    res.end();
}
