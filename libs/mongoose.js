var mongoose    = require('mongoose');
var log         = require('./log')(module);
var config      = require('./config');

//mongoose.connect(config.get('mongoose:uri'));

mongoose.connect('localhost','quax');
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
var User = new Schema({
    email: { type: String, required: true },
    handle: { type: String, required: true },
    sha1pass: { type: String, required: true },
    face: [{
        type: ObjectId,
        ref: 'Images'
    }],
    geo: {type: [Number], index: '2d'},
    tags : {type: [String], index: true},
    modified: { type: Date, default: Date.now },
    created: { type: Date, default: Date.now }
 });

// validation
Marker.path('text').validate(function (v) {
    return v.length > 2 && v.length < 800;
});
User.path('email').validate(function (v) {
    return v.length > 2 && v.length < 140;
});
User.path('handle').validate(function (v) {
    return v.length > 2 && v.length < 140;
});

var MarkerModel = mongoose.model('Marker', Marker);

var UserModel = mongoose.model('User', User);

module.exports.MarkerModel = MarkerModel;
module.exports.User = User;