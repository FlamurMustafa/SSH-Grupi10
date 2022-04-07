const express = require('express');
const pool = require("./config/db");
const bodyParser = require('body-parser');
const app = express();
const bcrypt = require('bcrypt');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
  extended: true
}));



app.post('/user/signup', (req, res)=>{
    const {username, name, lastname, is_professor, email, password} = req.body;
    bcrypt.hash(password, 8, process.env.KEY, (err, hash)=>{
        password = hash;
    });
    const hashedPassword = bcrypt.hashSync(password, 5);
    pool.query('INSERT into user(username, name, lastname, is_professor, email, password) VALUES '+
    '(?,?,?,?,?,?)', [username, name, lastname, is_professor, email, hashedPassword ], (error, results)=>{
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