


exports.createMark = function(req, res) {

  console.log('*** in createMark');

  var mark = new MarkerModel({
    title: req.body.title,
    author: req.body.author,
    text: req.body.text,
    images: [req.body.images],
    geo: [{latitude: req.body.latitude, longitude: req.body.longitude}],
    markedby: [req.body.markedby],
  });

  /*
  mark.save(function (err) {
    if (!err) {
      log.info("marker created");
      return res.send({ status: 'OK', mark:mark });
    }
    else {
      console.log(err);
      if(err.name == 'ValidationError'){
        res.statusCode = 400;
        res.send({ error: 'Validation error' });
      }
      else {
        res.statusCode = 500;
        res.send({ error: 'Server error' });
      }
      log.error('Internal error(%d): %s',res.statusCode,err.message);
    }
  });
  */

}
