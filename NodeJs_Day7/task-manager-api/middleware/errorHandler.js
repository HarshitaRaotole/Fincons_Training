const errorHandler = (err, req, res, next) => {
    console.error(err.stack); // Log the error stack for debugging

    // Default to 500 Server Error
    let statusCode = res.statusCode === 200 ? 500 : res.statusCode;
    let message = err.message || 'Server Error';

    // Mongoose Bad ObjectId (sending "123" instead of a valid ID)
    if (err.name === 'CastError') {
        statusCode = 404;
        message = `Resource not found with id of ${err.value}`;
    }

    // Mongoose Validation Error ( missing required field)
    if (err.name === 'ValidationError') {
        statusCode = 400;
        message = Object.values(err.errors).map(val => val.message).join(', ');
    }

    // Mongoose Duplicate Key (email already exists)
    if (err.code === 11000) {
        statusCode = 400;
        message = 'Duplicate field value entered';
    }

    res.status(statusCode).json({
        success: false,
        error: message
    });
};

module.exports = errorHandler;