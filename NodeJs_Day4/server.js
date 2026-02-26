const http = require('http');
const fs = require('fs');
const path = require('path');

//Http server
const server = http.createServer((req,res)=>{
    //Routing
    if (req.url ==='/'){
        const filePath = path.join(__dirname,'index.html');

        //Read file using fs
        fs.readFile(filePath,(err, data) =>{
            if(err){
                res.writeHead(500, {'Content-Type': 'text/plain'});
                res.end('Server Error: Could not read file');
            }else{
                res.writeHead(200,{'Content-Type': 'text/html'});
                res.end(data);//it sends the html file content
            }
        });
    }
    else if(req.url ==='/about'){
        res.writeHead(200,{'Content-Type': 'text/plain'});
        res.end('About Page : I am a Node.js Intern learning servers.');
    }
    else{
        res.writeHead(404,{'Content-Type':'text/palin'});
        res.end('404 Not Found');
    }
});

//Start the server
server.listen(3000, () =>{
    console.log('Server running at http://localhost:3000');
});