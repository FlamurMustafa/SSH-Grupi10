const jwt = require("jsonwebtoken");

async function Auth(req, res, next){
    try{
    const token = req.header('Authorization').replace('Bearer ', '');
    

    if(!process.env.KEY){
        throw new Error("No key found");
    }

    if (!token) return new Error(`Couldn't fetch a token`);
    const decoded = jwt.verify(token, process.env.KEY);

    req.token = token;
    req.userId = parseInt(decoded.id);
    req.role_Id = parseInt(decoded.role_id);
    next();
}
catch(e){
    return res.sendStatus(400);
}
}

module.exports = Auth;