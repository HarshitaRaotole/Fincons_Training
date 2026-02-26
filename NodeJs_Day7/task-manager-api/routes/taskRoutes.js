const express = require('express');
const router = express.Router();

// Import the controller functions
const { 
    getTasks, 
    createTask, 
    updateTask, 
    deleteTask 
} = require('../controllers/taskController');

//Import the protection middleware
const { protect } = require('../middleware/authMiddleware');

// Define routes
router.route('/').get(protect, getTasks).post(protect, createTask);
router.route('/:id').put(protect, updateTask).delete(protect, deleteTask);

module.exports = router;