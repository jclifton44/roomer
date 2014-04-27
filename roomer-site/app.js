var express = require('express'),
    index = require('./routes/index');
var app = express();



app.use(express.logger('dev'));
app.use(express.bodyParser());
app.engine('.html', require('jade').__express);
app.use(express.static(__dirname + '/public'));



app.get(/\/(\?next=true)?/, index.main);



app.listen(3000);
console.log('Listening on port 3000...');
