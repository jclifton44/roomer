var mongoose    = require('mongoose');
var log         = require('./log')(module);
//var mongoose    = require('./libs/mongoose.js').mongoose;

var crypto = require('crypto');
var config    = require('./config').configuration;

//mongoose.connect(config.get('mongoose:uri'), 'quax');

mongoose.connect('localhost','roomer');
var db = mongoose.connection;

db.on('error', function (err) {
    log.error('connection error:', err.message);
});
db.once('open', function callback () {
    log.info("Connected to DB!");
});

var Schema = mongoose.Schema;
var ObjectId = Schema.Types.ObjectId;
// Schemas
var Images = new Schema({
    kind: {
        type: String,
        enum: ['thumbnail', 'detail'],
        required: true
    },
    url: { type: String, required: true }
});

var Marker = new Schema({
    title: { type: String, required: true },
    author: { type: String, required: true },
    text: { type: String, required: true },
    images: [{type:ObjectId, ref:'Images'}],
    geo: [{latitude:Number, longitude:Number}],
    modified: { type: Date, default: Date.now, index: true},
    markedby: [{
        type: ObjectId,
        ref: 'User'
    }]
});


// User
var User = new Schema({
    username: {
        type: String,
        unique: true,
        required: true
    },
    handle: { 
        type: String, 
        unique: true,
        required: true 
    },
    hashedPassword: {
        type: String,
        required: true
    },
    salt: {
        type: String,
        required: true
    },
    created: {
        type: Date,
        default: Date.now
    },
    clientId: [{
        type:ObjectId,
        ref: String
    }],
    face: [{
        type:ObjectId,
        ref: 'Images'
    }],
    geo: {type: [Number], index: '2d'},
});


var Client = new Schema({
    name: {
        type: String,
        unique: true,
        required: true
    },
    redirectUri: {
        type: String,
        unique: true,
        required: true
    },
    clientId: {
        type: String,
        unique: true,
        required: true
    },
    clientSecret: {
        type: String,
        required: true
    }


});
var AccessToken = new Schema({
    userId: {
        type: String,
        required: true
    },
    clientId: {
        type: String,
        required: true
    },
    token: {
        type: String,
        unique: true,
        required: true
    },
    created: {
        type: Date,
        default: Date.now,
        expires: 1800
    },
    scope: {
        type: Number,        //Visibility (Your data) (Friends data) (Posting ability)
        default: 0
    }
});

var AuthorizationCode = new Schema({
    userId: {
        type: String,
        required: true
    },
    clientId: {
        type: String,
        required: true
    },
    token: {
        type: String,
        unique: true,
        required: true
    },
    created: {
        type: Date,
        default: Date.now,
        expires: 240
    },
    scope: {
        type: Number,        //Visibility (Your data) (Friends data) (Posting ability)
        default: 0
    }
});

var RefreshToken = new Schema({
    userId: {
        type: String,
        required: true
    },
    clientId: {
        type: String,
        required: true
    },
    token: {
        type: String,
        unique: true,
        required: true
    },
    created: {
        type: Date,
        default: Date.now
    }
});

User.methods.encryptPassword = function(password) {
    return crypto.createHmac('sha1', this.salt).update(password).digest('hex');
    //more secure – return crypto.pbkdf2Sync(password, this.salt, 10000, 512);
};

User.virtual('userId')
    .get(function () {
        return this.id;
    });

User.virtual('password')
    .set(function(password) {
        this._plainPassword = password;
        this.salt = crypto.randomBytes(32).toString('base64');
        //more secure - this.salt = crypto.randomBytes(128).toString('base64');
        this.hashedPassword = this.encryptPassword(password);
    })
    .get(function() { return this._plainPassword; });


User.methods.checkPassword = function(password) {
    return this.encryptPassword(password) === this.hashedPassword;
};


// validation
Marker.path('text').validate(function (v) {
    return v.length > 2 && v.length < 800;
});
User.path('username').validate(function (v) {
    return v.length > 2 && v.length < 140;
});
//User.path('handle').validate(function (v) {
 //   return v.length > 2 && v.length < 140;
//});

var MarkerModel = mongoose.model('Marker', Marker);
var RefreshTokenModel = mongoose.model('RefreshToken', RefreshToken);
var UserModel = mongoose.model('User', User);
var ClientModel = mongoose.model('Client', Client);
var AccessTokenModel = mongoose.model('AccessToken', AccessToken);
var AuthorizationCodeModel = mongoose.model('AuthorizationCode', AuthorizationCode);

module.exports.MarkerModel = MarkerModel;
module.exports.UserModel = UserModel;
module.exports.ClientModel = ClientModel;
module.exports.AccessTokenModel = AccessTokenModel;
module.exports.RefreshTokenModel = RefreshTokenModel;
module.exports.AuthorizationCodeModel = AuthorizationCodeModel;