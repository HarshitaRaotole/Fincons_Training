const Task = require('../models/Task');

// @desc    Get all tasks
// @route   GET /api/tasks
exports.getTasks = async (req, res, next) => {
    try {
        const tasks = await Task.find({user: req.user.id});
        res.status(200).json({ success: true, count: tasks.length, data: tasks });
    } catch (error) {
        next(error); // Pass error to custom middleware
    }
};

// @desc    Create a task
// @route   POST /api/tasks
exports.createTask = async (req, res, next) => {
    try {
        req.body.user = req.user.id;
        const task = await Task.create(req.body);
        res.status(201).json({ success: true, data: task });
    } catch (error) {
        next(error);
    }
};

// @desc    Update task
// @route   PUT /api/tasks/:id
exports.updateTask = async (req, res, next) => {
    try {
        // 1. Find the task first
        let task = await Task.findById(req.params.id);
         if (!task) {
            return res.status(404).json({ success: false, error: 'Task not found' });
        }
        // task.user is an ObjectId, req.user._id is an ObjectId.
        // We convert both to String to compare them safely.
        if (task.user.toString() !== req.user._id.toString()) {
            return res.status(403).json({ success: false, error: 'You can only update your own task' });
        }

        // If ownership matches, proceed to update
        task = await Task.findByIdAndUpdate(req.params.id, req.body, {
            new: true,
            runValidators: true
        });


        res.status(200).json({ success: true, data: task });
    } catch (error) {
        next(error);
    }
};

// @desc    Delete task
// @route   DELETE /api/tasks/:id
exports.deleteTask = async (req, res, next) => {
    try {
        // Find the task
        const task = await Task.findById(req.params.id);

         // Check if exists
        if (!task) {
            return res.status(404).json({ success: false, error: 'Task not found' });
        }

        // CHECK OWNERSHIP
        if (task.user.toString() !== req.user._id.toString()) {
            return res.status(403).json({ success: false, error: 'You can only delete your own task' });
        }

         // If ownership matches, delete
        await task.deleteOne();

        res.status(200).json({ success: true, data: {} });
    } catch (error) {
        next(error);
    }
};