
$(document).ready(function(){
  var h = $('#the-navbar').height() * 2;
  $('#the-jumbo').height(parseInt($(window).height(), 10) - h);
});

/*
  even better, find out how to make #the-jubmo's height
  be windowHight - 2*navbarHeight

  that way the jumbo has equal top and bottom parts take out and it
  looks like a movie
*/
