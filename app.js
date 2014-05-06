

/**
 * Module dependencies.
 */

var express = require('express')
  , routes = require('./routes')
  , user = require('./routes/user')
  , https = require('https')
  , ht = require('http')
  , path = require('path')
  , log = require('./libs/log')(module)
  , fs = require('fs')
  , config = require('./libs/config.js')
  , mongoose = require('./libs/mongoose.js').mongoose
  , oauth2 = require('./libs/oauth2.js')
  , auth = require('./libs/auth.js')
  , index = require('./routes/action/index')
  , passport = require('passport')
  , app_site = express();


require('./libs/auth');

app_site.use(express.logger('dev'));
app_site.use(express.bodyParser());
app_site.engine('.html', require('jade').__express);
app_site.use(express.static(__dirname + '/public'));
app_site.use(app_site.router);
app_site.get(/\/(\?next=true)?/, routes.index);

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
    console.log('*** matched /db/mark');
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
app.get('/api/userInfo',
    passport.authenticate('bearer', { session: false }),
        function(req, res) {
            // req.authInfo is set using the `info` argument supplied by
            // `BearerStrategy`.  It is typically used to indicate scope of the token,
            // and used in access control checks.  For illustrative purposes, this
            // example simply returns the scope in the response.
            res.json({ user_id: req.user.userId, name: req.user.username, scope: req.authInfo.scope })
        }
);


app.post('/db/mark', function(req, res) {
    var mark
 = new MarkerModel({
        title: req.body.title,
        author: req.body.author,
        text: req.body.text,
        images: [req.body.images],
        geo: [{latitude: req.body.latitude, longitude: req.body.longitude}],
        markedby: [req.body.markedby],

    });

    mark.save(function (err) {
        if (!err) {
            log.info("marker created");
            return res.send({ status: 'OK', mark:mark });
        } else {
            console.log(err);
            if(err.name == 'ValidationError'){
                res.statusCode = 400;
                res.send({ error: 'Validation error' });
            } else {
                res.statusCode = 500;
                res.send({ error: 'Server error' });
            }
            log.error('Internal error(%d): %s',res.statusCode,err.message);
        }
    });
});


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



ht.createServer(app_site).listen(app.get('port'));
https.createServer(opts, app).listen(8081);

