const User = require('../models/User');

// @desc    Register a new user
// @route   POST /api/users
exports.registerUser = async (req, res, next) => {
    try {
        const { username, email } = req.body;

        // Check if user already exists
        const userExists = await User.findOne({ email });
        if (userExists) {
            res.status(400);
            throw new Error('User already exists');
        }

        // Create user
        const user = await User.create({
            username,
            email
        });

        res.status(201).json({
            success: true,
            data: user
        });
    } catch (error) {
        next(error); // Sends error to our middleware
    }
};

// @desc    Get all users 
// @route   GET /api/users
exports.getUsers = async (req, res, next) => {
    try {
        const users = await User.find();
        res.status(200).json({ success: true, data: users });
    } catch (error) {
        next(error);
    }
};