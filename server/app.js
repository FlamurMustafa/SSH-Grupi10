const express = require("express");


const app = express();
const userRoute = require("./routes/user");
const classRoute = require("./routes/class");

app.use("/user", userRoute);
app.use("/class", classRoute);

app.listen(3000);
