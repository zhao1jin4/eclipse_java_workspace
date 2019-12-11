var url = require('url'); 
var http = require('http'); 
var server = http.createServer(function (req, res)
{ 
  var pathname=url.parse(req.url).pathname;
  res.writeHead(200,{'Content-Type':'application/json;charset=utf8'});
  if(pathname=='/health.json')
  {
	res.end(JSON.stringify({"status":"UP"}));
  }else if(pathname='/')
  {
	res.end("welcome to node.js");
  }else
  {
	  res.end("404");
  }
})
server.listen(8000,function(){
	console.log("server started");
});


