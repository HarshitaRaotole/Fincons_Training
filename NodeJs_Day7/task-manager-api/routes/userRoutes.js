const express = require('express');
const router = express.Router();
const { registerUser, getUsers } = require('../controllers/userController');

router.route('/')
    .post(registerUser) // POST /api/users (Registration)
    .get(getUsers);     // GET /api/users (View users)

module.exports = router;