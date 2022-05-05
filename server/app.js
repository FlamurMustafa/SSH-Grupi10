const express = require("express");
const pool = require("./config/db");
const bodyParser = require("body-parser");
const app = express();
const bcrypt = require("bcrypt");
const Auth = require("./middlewares/authorization");
const jwt = require("jsonwebtoken");

app.use(bodyParser.json());
app.use(
  bodyParser.urlencoded({
    extended: true,
  })
);

//signup route
app.post("/user/signup", async (req, res) => {
  const { username, name, lastname, is_professor, email, password } = req.body;
  const hashedPassword = await bcrypt.hash(password, 5);
  try {
    pool.query(
      "INSERT into user(username, name, lastname, is_professor, email, password) VALUES " +
        "(?,?,?,?,?,?)",
      [username, name, lastname, is_professor, email, hashedPassword],
      (error, results) => {
        if (error) {
          if (error.errno == 1062)
            return res.status(409).json({ message: "Username already exists" });
          return res.status(400).json({ error });
        }
        return res.status(201);
      }
    );
  } catch (e) {
    console.log(e);
  }
});
//login route
app.post("/user/login", async (req, res) => {
  let password = req.body.password;
  let email = req.body.email;

  try {
    pool.query(
      "SELECT * FROM user where email = ?",
      [email],
      (error, result) => {
        if (error) return res.status(400).json({ error });
        finishCall(result);
      }
    );
  } catch (e) {}
  async function finishCall(result) {
    const isMatch = await bcrypt.compare(password, result[0].password);
    if (!isMatch) {
      return res.status(404).send("Not the password");
    }

    res.send(jwt.sign({ id: result[0].userid.toString() }, process.env.KEY));
  }
});

app.get("/user/classes", Auth, (req, res) => {
  res.json(req.userId);
});

app.get("/user/:email", (req, res) => {
  const email = req.params.email;

  try {
    pool.query(
      "SELECT name, lastname, is_professor, email FROM user where email = ?",
      [email],
      (error, result) => {
        if (error) return res.status(400).json({ error });
        result = JSON.parse(JSON.stringify(result));
        console.log(result);
        return res.status(200).json(result);
      }
    );
  } catch (e) {}
});

app.post("/class/post", async (req, res) => {
  pool.query(
    `SELECT * from classes where start_time>="${req.body.start_time}" and end_time<="${req.body.end_time}" and class_number=${req.body.class_number}`,
    (error, result) => {
      if (error) return res.sendStatus(500);
      if (result.length!==0){        
        return res.status(409).json({ error: "The schedule is already taken" });
      }
      pool.query(
        "Insert into classes(start_time, end_time, class_name, class_number) values (?, ?, ?, ?)",
        [
          req.body.start_time,
          req.body.end_time,
          req.body.class_name,
          req.body.class_number,
        ],
        (result, err) => {
          if (err.warningStatus!==0){ return res.status(500).json({error});}
          return res.sendStatus(201);
        }
      );
    }
  );

  //if(req)
  //pool.query('Insert INTO ')
});

app.listen(3000);
