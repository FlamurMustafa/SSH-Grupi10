const express = require('express');
const pool = require("./config/db");
const bodyParser = require('body-parser');
const app = express();

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
  extended: true
}));



app.post('/user/signup', (req, res)=>{
    console.log(req.body);
    const {username, name, lastname, is_professor, email, password} = req.body;
    pool.query('INSERT into user(username, name, lastname, is_professor, email, password) VALUES '+
    '(?,?,?,?,?,?)', [username, name, lastname, is_professor, email, password ], (error, results)=>{
        if(error) return res.json({error:error});
        else(res.send(results));
    });
});

// pool.query("select * from user", (err, result, fields )=>{
//     if(err){
//         console.log(err);
//     }
//     return console.log(result);
// });

app.listen(3000);