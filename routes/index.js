
/*
 * only servers web stuff
 */
var jade = require('jade');
exports.index = function(req, res){
  if(req.url == '/') {
    jade.renderFile('./views/index.jade', {}, function (err, html) {
      if (err) { console.error('error', err.stack); return;}
      res.end(html);
    });
  }
  else if(req.url == '/db/mark') {
    console.log('*** matched /db/mark in routes/index.index');
    console.log('*** did you meant to use https://?');
  }
  else {
    console.log('*** unmatched url in routes/index.index: ' + req.url);
  }
}
