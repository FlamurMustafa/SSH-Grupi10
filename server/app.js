const express = require("express");

const bodyParser = require("body-parser");
const app = express();
const userRoute = require("./routes/user");
const classRoute = require("./routes/class");

app.use("/user", userRoute);
app.use("/class", classRoute);

app.get("/classes", Auth, (req, res) => {
  if (req.role_Id === 0) {
    //fetch student's classes
    pool.query(
      "select room_id, start_time, end_time, class_name from schedule join class on schedule.classid = class.classid join `student-classes` on `student-classes`.class_classid = class.classid join user on user.userid = `student-classes`.user_userid where user.userid = ?;",
      [req.userId],
      (errorq, result) => {
        if(errorq) res.status(500).send({errorq});
        return res.send(result[0]);
      }
    );
  } else {
    pool.query(
      "select class_name, start_time, end_time from schedule join class on class.classid = schedule.classid join user on user.userid = class.lecturer where userid = ?;",[req.userId], (errorq, resultq)=>{
        if(errorq) res.status(500).send(err);
        else return res.send(resultq[0]);
      });
  }
});

app.listen(3000);
