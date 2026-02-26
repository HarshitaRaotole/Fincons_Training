const mongoose = require('mongoose');

//structure of a User
const userSchema = new mongoose.Schema({
     _id: { 
        type: Number, 
        required: true 
    },
    name: {
        type: String,
        required: true // Validation: Name is mandatory
    },
    email: {
        type: String,
        required: true,
        unique: true   // Validation: No duplicate emails
    },
    age: {
        type: Number,
        min: 18        // Validation: Must be at least 18
    },
    createdAt: {
        type: Date,
        default: Date.now // Auto-generate timestamp
    }
});

// Create the Model
const User = mongoose.model('User', userSchema);

module.exports = User;