const express = require('express');
const app  = express();
const PORT = 3000;

//1. BUilt-in Middleware
app.use(express.json());

//2.Custom Logger Middleware
const myLogger = (req, res, next)=> {
    const timestamp = new Date().toISOString();
    const method  = req.method;
    const url = req.url;
    console.log(`[${timestamp}] ${method} request to ${url}`);
// If forget next(), the server will hang.
    next();
};

// Register the custom middleware
app.use(myLogger);

//2. Data
let users = [
    { id: 1, name: "Alice", role: "Intern" },
    { id: 2, name: "Bob", role: "Developer" }
];

//3. ROUTES SECTION

// GET Route: Fetch data
// Endpoint: http://localhost:3000/users
app.get('/users', (req, res) => {
    // Respond with JSON data and a 200 (OK) status
    res.status(200).json({
        message: "Users fetched successfully",
        data: users
    });
});

// POST Route: Send data
// Endpoint: http://localhost:3000/users
app.post('/users', (req, res) => {
    // 1. Get data from the request body
    const newUser = req.body;

    // 2. Simple validation 
    if (!newUser.name) {
        return res.status(400).json({ error: "Name is required" });
    }

    // 3. Add ID and push to our "database"
    newUser.id = users.length + 1;
    users.push(newUser);
    // 4. Send back the created resource with 201 (Created) status
    res.status(201).json({
        message: "User created successfully",
        user: newUser
    });
});

//4. Start server
app.listen(PORT, () => {
    console.log(`Server is running on http://localhost:${PORT}/users`);
});