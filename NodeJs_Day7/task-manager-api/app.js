const express = require('express');
const connectDB = require('./config/db');
const errorHandler = require('./middleware/errorHandler');

// 1. Connect to Database
connectDB();

const app = express();

// 2. Built-in Middleware
app.use(express.json()); // Allows parsing JSON body

// 3. Logger Middleware 
app.use((req, res, next) => {
    console.log(`${req.method} ${req.protocol}://${req.get('host')}${req.originalUrl}`);
    next();
});

// 4. Mount Routers
// Any URL starting with /api/tasks will go to taskRoutes
app.use('/api/tasks', require('./routes/taskRoutes'));
app.use('/api/users', require('./routes/userRoutes'));

// 5. Error Handler Middleware 
app.use(errorHandler);

const PORT = 3000;

app.listen(PORT, () => {
    console.log(`Server running on port ${PORT}`);
});