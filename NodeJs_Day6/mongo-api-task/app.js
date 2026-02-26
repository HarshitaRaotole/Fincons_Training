// app.js
const express = require('express');
const mongoose = require('mongoose');
const User = require('./models/User'); // Import the User model

const app = express();
const PORT = 3000;

// Middleware
app.use(express.json());

// 1. CONNECT TO MONGODB
// 'task' is the name of the database. It will be created automatically.
mongoose.connect('mongodb://127.0.0.1:27017/task')
    .then(() => console.log('✅ Connected to MongoDB Successfully'))
    .catch((err) => console.error('❌ MongoDB Connection Error:', err));


// CRUD ROUTES FOR USER

// 1. CREATE: POST /users
app.post('/users', async (req, res) => {
    try {
        // Create a new User instance with data from Body
        const newUser = new User(req.body);
        
        // Save to database (Async operation)
        const savedUser = await newUser.save();
        
        res.status(201).json(savedUser);
    } catch (error) {
        // Handle validation errors (e.g., missing name)
        res.status(400).json({ message: error.message });
    }
});

// 2. READ ALL: GET /users
app.get('/users', async (req, res) => {
    try {
        // .find() returns an array of all documents
        const users = await User.find();
        res.status(200).json(users);
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
});

// 3. READ ONE: GET /users/:id
app.get('/users/:id', async (req, res) => {
    try {
        // .findById() looks for the _id field
        const user = await User.findById(req.params.id);
        
        if (!user) {
            return res.status(404).json({ message: "User not found" });
        }
        res.status(200).json(user);
    } catch (error) {
        res.status(500).json({ message: "Invalid ID format or Server Error" });
    }
});

// 4. UPDATE: PUT /users/:id
app.put('/users/:id', async (req, res) => {
    try {
        // findByIdAndUpdate(id, data, options)
        // { new: true } means "return the updated object, not the old one"
        const updatedUser = await User.findByIdAndUpdate(
            req.params.id, 
            req.body, 
            { new: true, runValidators: true } 
        );

        if (!updatedUser) {
            return res.status(404).json({ message: "User not found" });
        }
        
        res.status(200).json(updatedUser);
    } catch (error) {
        res.status(400).json({ message: error.message });
    }
});

// 5. DELETE: DELETE /users/:id
app.delete('/users/:id', async (req, res) => {
    try {
        const deletedUser = await User.findByIdAndDelete(req.params.id);

        if (!deletedUser) {
            return res.status(404).json({ message: "User not found" });
        }

        res.status(200).json({ message: "User deleted successfully" });
    } catch (error) {
        res.status(500).json({ message: error.message });
    }
});

// Start Server
app.listen(PORT, () => {
    console.log(`Server running on http://localhost:${PORT}`);
});