

/**
 * Module dependencies.
 */
var express = require('express')
  , routes = require('./routes')
  , otis = require('./routes/otisAPI.js')
  , https = require('https')
  , ht = require('http')
  , path = require('path')
  , log = require('./libs/log')(module)
  , fs = require('fs')
  , config = require('./libs/config.js')
  , mongoose = require('./libs/mongoose.js').mongoose
  , oauth2 = require('./libs/oauth2.js')
  , oauthserver = require('node-oauth2-server')
  , auth = require('./libs/auth.js')
  , passport = require('passport')
  , app_site = express();


require('./libs/auth');

/*
  app_site is for website
*/

app_site.use(express.logger('dev'));
app_site.use(express.bodyParser());
app_site.engine('.html', require('jade').__express);
app_site.use(express.static(__dirname + '/public'));
app_site.use(app_site.router);
app_site.get('/', routes.index);

/*
  ssl enabalization
*/

var opts = {

  // Specify the key file for the server
  key: fs.readFileSync('./ssl/server/keys/roomer-key.pem'),

  // Specify the certificate file
  ca: fs.readFileSync('./ssl/server/keys/roomer-csr.pem'),

  // Specify the Certificate Authority certificate
  cert: fs.readFileSync('./ssl/server/keys/roomer-cert.pem'),

  // This is where the magic happens in Node.  All previous
  // steps simply setup SSL (except the CA).  By requesting
  // the client provide a certificate, we are essentially
  // authenticating the user.
  requestCert: true,

  // If specified as "true", no unauthenticated traffic
  // will make it to the route specified.
  rejectUnauthorized: false
};


/*
  everything down, is for roomer app, not website
*/

var app = express();

var Mongoose = require('mongoose');

// all environments
app.set('port', process.env.PORT || 8080);
app.set('views', __dirname + '/views');
app.set('view engine', 'jade');
app.use(express.favicon());
app.use(express.logger('dev'));
app.use(express.bodyParser());
app.use(express.methodOverride());
app.use(app.router);
app.use(express.static(path.join(__dirname, 'public')));
app.use(express.json());       // to support JSON-encoded bodies
app.use(express.urlencoded()); // to support URL-encoded bodies
app.use(passport.initialize());



// development only

//app.oauth = oauthserver({
//  model: models.oauth,
//  grants: ['password', 'authorization_code', 'refresh_token', 'client-credentials'],
//  debug: true
//});
/*
 * Grant types: 
 *    |____ 'password' or 'resource owner credentials'      - used 
 *    |____ 'authorization_code'                            - used by ThirdParty APIs to use our Info (sustained usage)
 *    |____ 'refresh_token'                                 - used to regain an access token after expiration
 *    |____ 'client-credentials'                            - used to authenticate public clients (native apps) 
 */

if ('development' == app.get('env')) {
  app.use(express.errorHandler());
}



var MarkerModel    = require('./libs/mongoose').MarkerModel;
app.use(function(req, res, next){
    res.status(404);
    log.debug('Not found URL: %s',req.url);
    res.send({ error: 'Not found' });
    return;
});



app.use(function(err, req, res, next){
    res.status(err.status || 500);
    log.error('Internal error(%d): %s',res.statusCode,err.message);
    res.send({ error: err.message });
    return;
});



/* example */
app.get('/ErrorExample', function(req, res, next){
    next(new Error('Random error!'));
});
/* example end */



app.get('/', routes.index);



app.get('/db/mark', function(req, res) {
    console.log('*** matched /db/mark in app.get');
    return mark.find(function (err, mark) {
        if (!err) {
            return res.send(mark);
        } else {
            res.statusCode = 500;
            log.error('Internal error(%d): %s',res.statusCode,err.message);
            return res.send({ error: 'Server error' });
        }
    });
});



app.post('/oauth/token', oauth2.token);

app.all('/oauth2/authorize/:userId', oauth2.requestGrant);
app.all('/oauth2/token/', oauth2.requestToken);




app_site.get(/\/(\?next=true)?/, routes.index);

app.post('/db/mark', otis.createMark);



app.use(function(request, response, next) {
var subject = req.connection
      .getPeerCertificate().subject;
  		if (request.url == "/") {

        console.log('*** matched / in app.use');

  			response.type('json');
   			response.writeHead(200, { "Content-Type": "application/json" });
    		response.end(JSON.stringify({a: request.body.p1}));
    // The middleware stops here.
  		} else {
    		next();
  		}
});



// About page
app.use(function(request, response, next) {
  if (request.url == "/user") {
    response.writeHead(200, { "Content-Type": "application/json" });
    response.end(JSON.stringify({test:true}));
    // The middleware stops here.
  } else {
    next();
  }
});



app.use(function(request, response, next) {
  if (request.url == "/post") {

    response.writeHead(200, { "Content-Type": "application/json" });
    if(request.body.type == "add"){

    } else if(request.body.type == "modify") {

    } else if(request.body.type == "delete") {
    	response.end(JSON.stringify({test:true}));
	} else {

	}
    // The middleware stops here.
  } else {
    next();
  }
});



app.use(function(request, response, next) {
  if (request.url == "/auth") {
    response.writeHead(200, { "Content-Type": "application/json" });
    response.end(JSON.stringify({a:2}));
    // The middleware stops here.
  } else {
    next();
  }
});



app.use(function(request, response, next) {
  if (request.url == "/roomer") {
    response.writeHead(200, { "Content-Type": "application/json" });
    response.end(JSON.stringify({a:2}));
    // The middleware stops here.
  } else {
    next();
  }
});



// 404'd!
app.use(function(request, response) {
  response.writeHead(404, { "Content-Type": "application/json" });
  response.end("404 error!\n");
});



ht.createServer(app_site).listen(80);
https.createServer(opts, app).listen(81);
