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
//login route
app.post("/user/login", async (req, res) => {
  let { email, username, password } = req.body;

  try {
    pool.query(
      "SELECT * FROM user where email = ? or username=?",
      [email, username],
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

app.get("/user/:email", Auth, (req, res) => {
  try {
    pool.query(
      "SELECT username, email, role_id, name FROM user where id = ?",
      [req.userId],
      (error, result) => {
        if (error) return res.status(400).json({ error });
        result = JSON.parse(JSON.stringify(result));
        console.log(result);
        return res.status(200).json(result);
      }
    );
  } catch (e) {
    res.status(500).json({ error: "An error occurred" });
    console.log(e);
  }
});

app.post("/class/post", Auth, async (req, res) => {
  pool.query(
    `SELECT * from schedule where end_time>"${req.body.start_time}" and start_time<"${req.body.end_time}" and room_id=${req.body.room_id}`,
    (error, result) => {
      if (error) return res.sendStatus(500);
      if (result.length !== 0) {
        return res.status(409).json({ error: "The schedule is already taken" });
      }
      pool.query(
        `Select classid from class where lecturer=${req.userId}`,
        (err, result) => {
          if (err) return res.status(err);
          insertIntoClass(result);
        }
      );
      function insertIntoClass(result) {
        pool.query(
          "Insert into schedule(room_id, start_time, end_time, classid) values (?, ?, ?, ?)",
          [
            req.body.room_id,
            req.body.start_time,
            req.body.end_time,
            result[0].classid,
          ],
          (result, err) => {
            if (err) return res.status(500).json({ err });
            return res.sendStatus(201);
          }
        );
      }
    }
  );
});

app.listen(3000);
