
/*
 * GET home page.
 */

exports.action = function(req, res){
  res.render('action', /*
  	{ extra info }*/
  	{ title: req.body.post1 }
  	);

};