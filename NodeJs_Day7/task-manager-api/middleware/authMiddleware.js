const User = require('../models/User');

const protect = async(req, res, next) =>{
    let token;
    //check if header has "Bearer ID"
    if(req.headers.authorization && req.headers.authorization.startsWith('Bearer')){
        try{
            //Get id from the header
            token = req.headers.authorization.split(' ')[1];

            

            //Find the user in db
            const user = await User.findById(token);

            if(!user){
                return res.status(401).json({error:'User not found'});
            }

            //Controller knows who is logged in
            req.user = user;

            next();
        }catch(error){
            res.status(401).json({error:'Not authorized'});
        }
    }

    if(!token){
        res.status(401).json({error:'Not authorized, no token'});
    }
};
module.exports = { protect };