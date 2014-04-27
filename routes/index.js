
/*
 * GET home page.
 */

exports.index = function(req, res){
  console.log('*** in routes/index');
  res.render('index', { title: 'Express' });
};
