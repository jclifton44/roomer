
/*
 * GET home page.
 */
var jade = require('jade');
exports.index = function(req, res){
  if(req.url == '/') {
    jade.renderFile('./views/index.jade', {}, function (err, html) {
      if (err) { console.error('error', err.stack); return;}
      console.log('*** about to send html: ' + html);
      res.end(html);
    });
  }
};
