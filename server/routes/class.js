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
  if(req.role_Id===0) return res.status(401).send({"Error":"You can't do this action"});
  pool.query(
    `SELECT * from schedule where end_time>"2022-05-27 13:00:00" and start_time<"2022-05-27 15:00:00" and room_id=3;`,
    (error, result) => {
      if (error) return res.sendStatus(500);
      if (result.length !== 0) {
        return res.status(409).json({ error: "The schedule is already taken" });
      }
      pool.query(
        `Select classid, class_name from class where lecturer=${req.userId}`,
        (err, result) => {
          if (err) return res.status(err);
          if(result.length===0) res.status(400).send({"error":"User does not have classes"});
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
            if (err.affectedRows!==1) return res.status(500).json({ err });
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
      "select scheduleid, room_id, start_time, end_time, class.class_name from schedule join class on schedule.classid = class.classid join `student-classes` on `student-classes`.class_classid = class.classid join user on user.userid = `student-classes`.user_userid where user.userid = ? group by scheduleid;",
      [req.userId],
      (errorq, result) => {
        if (errorq) res.status(500).send({ errorq });
        return res.send(result);
      }
    );
  } else {
    pool.query(
      "select scheduleid, room_id, start_time, end_time, class_name from schedule join class on schedule.classid = class.classid where class.lecturer = ?",
      [req.userId],
      (errorq, resultq) => {
        if (errorq) return res.status(500).send(errorq);
        else return res.send(resultq);
      }
    );
  }
});

classRoute.get("/search", Auth, (req, res)=>{
console.log(req.query.sql);
  if (req.role_Id === 0) {
      pool.query(
        "SELECT c.class_name, s.scheduleid, s.room_id, s.start_time, s.end_time FROM managmentsys.class c, managmentsys.schedule s, managmentsys.`student-classes` sc WHERE c.classid = s.classid AND sc.class_classid = c.classid AND class_name=?",
        [req.query.sql],
        (errorq, result) => {
          if (errorq) res.status(500).send({ errorq });
          return res.send(result);
        }
      );
    } else {
      pool.query(
        `SELECT c.class_name, s.scheduleid, s.room_id, s.start_time, s.end_time FROM managmentsys.class c, managmentsys.schedule s WHERE c.classid = s.classid AND class_name=?`,
        [req.query.sql],
        (errorq, resultq) => {
          if (errorq) return res.status(500).send(errorq);
          else return res.send(resultq);
        }
      );
    }
  });

classRoute.delete("/", Auth, (req, res) => {
  if(req.role_Id===0) return res.status(401).send({"mistake":"You can't delete schedules"});
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
