const express = require('express');
const pool = require("./config/db")
const app = express();


app.get('/', (req, res)=>{
    res.send("Hello World");
});

pool.query("select * from user", (err, result, fields )=>{
    if(err){
        console.log(err);
    }
    return console.log(result);
});

app.listen(3000);