var http = require("http");
var cp = require("child_process");

var port = 8899;
module.exports = function(timeout) {
    if(timeout === undefined) timeout = 30000;


    var server = http.createServer(function (req, res) {
       res.writeHead(200);
       res.end('Hello, World!');
    });
    server.listen(port);


    var url = "http://localhost:" + port;
    var start = (process.platform == "darwin"? "open": process.platform == "win32"? "start": "xdg-open");
    cp.exec(start + " " + url);
}