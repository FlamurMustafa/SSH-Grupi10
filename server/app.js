const express = require('express');
const pool = require("./config/db");
const bodyParser = require('body-parser');
const app = express();
const bcrypt = require('bcrypt');

app.use(bodyParser.json());
app.use(bodyParser.urlencoded({
  extended: true
}));


//signup route
app.post('/user/signup', (req, res)=>{
    const {username, name, lastname, is_professor, email, password} = req.body;
    const hashedPassword = bcrypt.hashSync(password, 5);
    try{
    pool.query('INSERT into user(username, name, lastname, is_professor, email, password) VALUES '+
    '(?,?,?,?,?,?)', [username, name, lastname, is_professor, email, hashedPassword ], 
    (error, results) => {
        if(error) {
            if(error.errno==1062) return res.status(409).json({message: 'Username already exists'});
            return res.status(400).json({error});
        }
        return res.status(201);
    });
} catch(e){
    console.log(e);
}
});

app.get('/user/:email', (req, res)=>{
    const email = req.params.email;

    try{
        pool.query('SELECT name, lastname, is_professor, email FROM user where email = ?', [email], (error, result)=>{
            if(error) return res.status(400).json({error});
            result = JSON.parse(JSON.stringify(result));
            console.log(result);
            return res.status(200).json(result);
        })
    } catch(e){
        
    }


});

app.listen(3000);