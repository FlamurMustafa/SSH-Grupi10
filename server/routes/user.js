const express = require("express");
const bodyParser = require("body-parser");

const Auth = require("../middlewares/authorization");
const jwt = require("jsonwebtoken");
const bcrypt = require("bcrypt");
const pool = require("../config/db");


const userRoute = express.Router();

userRoute.use(bodyParser.json());
userRoute.use(
  bodyParser.urlencoded({
    extended: true,
  })
);


userRoute.post("/signup", async (req, res) => {
  const { username, name, lastname, role_id, email, password } = req.body;
  const hashedPassword = await bcrypt.hash(password, 5);
  try {
    pool.query(
      "SELECT * FROM user where email = ? or username=?",
      [req.body.email, req.body.username],
      (err, result) => {
        if (err) res.send(err);
        if (result.length !== 0)
          return res.status(409).json({ error: "User already exists" });
        pool.query(
          "INSERT INTO user(username, email, password, role_id, name) values(?,?,?,?, ?)",
          [username, email, hashedPassword, role_id, name],
          (err, result) => {
            if (err) console.log(err);
            res.sendStatus(201);
          }
        );
      }
    );
  } catch (e) {
    return res.status(500).json(e);
  }
});

userRoute.post("/login", async (req, res) => {
  let { email, username, password } = req.body;

  try {
    pool.query(
      "SELECT * FROM user where email = ? or username=?",
      [email, username],
      (error, result) => {
        if (error) return res.status(400).json({ error });
        if(result.length===0) return res.status(400).json({ error });
        finishCall(result);
      }
    );
  } catch (e) {}
  async function finishCall(result) {
    const isMatch = await bcrypt.compare(password, result[0].password);
    if (!isMatch) {
      return res.status(404).send("Not the password");
    }

    return res.send(jwt.sign({ id: result[0].userid.toString(), role_id: result[0].role_id.toString }, process.env.KEY));
  }
});

userRoute.get("/a",  (req, res)=>{
  console.log("hello");
  return res.send("Hello");
})

userRoute.get("/", Auth, (req, res) => {
  try {
    pool.query(
      "SELECT username, email, role_id, name FROM user where userid = ?",
      [req.userId],
      (error, result) => {
        if (error) return res.status(400).json({ error });
        result = JSON.parse(JSON.stringify(result));
        return res.status(200).json(result);
      }
    );
  } catch (e) {
    res.status(500).json({ error: "An error occurred" });
    console.log(e);
  }
});

module.exports = userRoute;
