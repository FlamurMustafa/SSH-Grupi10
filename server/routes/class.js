const express = require('express');
const Auth = require("../middlewares/authorization");
const pool = require("./config/db");

const classRoute = express.Route();


classRoute.use(bodyParser.json());
classRoute.use(
  bodyParser.urlencoded({
    extended: true,
  })
);

classRoute.post("/class/post", Auth, async (req, res) => {
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

  module.exports = classRoute;
  