const express = require("express");
const Auth = require("../middlewares/authorization");
const bodyParser = require("body-parser");

const pool = require("../config/db");

const classRoute = express.Router();

classRoute.use(bodyParser.json());
classRoute.use(
  bodyParser.urlencoded({
    extended: true,
  })
);

classRoute.post("/post", Auth, async (req, res) => {
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

classRoute.get("/", Auth, (req, res) => {
  if (req.role_Id === 0) {
    //fetch student's classes
    pool.query(
      "select room_id, start_time, end_time, class_name from schedule join class on schedule.classid = class.classid join `student-classes` on `student-classes`.class_classid = class.classid join user on user.userid = `student-classes`.user_userid where user.userid = ?;",
      [req.userId],
      (errorq, result) => {
        if (errorq) res.status(500).send({ errorq });
        return res.send(result[0]);
      }
    );
  } else {
    pool.query(
      "select class_name, start_time, end_time from schedule join class on class.classid = schedule.classid join user on user.userid = class.lecturer where userid = ?;",
      [req.userId],
      (errorq, resultq) => {
        if (errorq) res.status(500).send(err);
        else return res.send(resultq[0]);
      }
    );
  }
});

classRoute.delete("/", Auth, (req, res) => {
  pool.query(
    "DELETE from schedule where scheduleid=?",
    [req.query.scheduleid],
    (error, result) => {
      if (error) return res.status(500).json({ error });
      return res.sendStatus(200);
    }
  );
});
module.exports = classRoute;
