const express = require('express');
const app = express();
const PORT = 3000;

// Middleware to parse JSON body
app.use(express.json());

// MOCK DATABASE
let users = [
    { id: 1, name: "Harshita", role: "Manager" },
    { id: 2, name: "Steves", role: "Developer" }
];

// ROUTES

// 1. GET /users (Read All)
app.get('/users', (req, res) => {
    res.status(200).json(users);
});

// 2. GET /users/:id (Read One)
app.get('/users/:id', (req, res) => {
    const userId = parseInt(req.params.id);
    const user = users.find(u => u.id === userId);

    if (!user) {
        return res.status(404).json({ message: "User not found" });
    }

    res.status(200).json(user);
});

// 3. POST /users (Create)
app.post('/users', (req, res) => {
    const { name, role } = req.body;

    if (!name || !role) {
        return res.status(400).json({ message: "Name and Role are required" });
    }

    const newUser = {
        id: users.length + 1,
        name,
        role
    };

    users.push(newUser);
    res.status(201).json({ message: "User created", user: newUser });
});

// 4. PUT /users/:id (Update)
app.put('/users/:id', (req, res) => {
    const userId = parseInt(req.params.id);
    const user = users.find(u => u.id === userId);

    // If user doesn't exist, we can't update them
    if (!user) {
        return res.status(404).json({ message: "User not found" });
    }

    // Update the data (req.body contains the new values)
    const { name, role } = req.body;

    // only update if the data is provided, otherwise keep old data
    if (name) user.name = name;
    if (role) user.role = role;

    res.status(200).json({ message: "User updated", user: user });
});

// 5. DELETE /users/:id (Delete)
app.delete('/users/:id', (req, res) => {
    const userId = parseInt(req.params.id);
    const userIndex = users.findIndex(u => u.id === userId);

    if (userIndex === -1) {
        return res.status(404).json({ message: "User not found" });
    }

    users.splice(userIndex, 1); // Remove 1 item at index
    res.status(200).json({ message: "User deleted successfully" });
});

// Start Server
app.listen(PORT, () => {
    console.log(`Server running on http://localhost:${PORT}`);
});