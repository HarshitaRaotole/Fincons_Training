const mongoose = require('mongoose');

const connectDB = async () => {
    try {
        // Connect to MongoDB 
        const conn = await mongoose.connect('mongodb://127.0.0.1:27017/task-manager-db');
        console.log(` MongoDB Connected: ${conn.connection.host}`);
    } catch (error) {
        console.error(`Error: ${error.message}`);
        process.exit(1); // Stop the app if DB fails
    }
};

module.exports = connectDB;