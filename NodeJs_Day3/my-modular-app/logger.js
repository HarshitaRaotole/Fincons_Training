const logInfo = (message)=>{
    console.log(`[INFO]: ${message}`);
};

const logResult = (label, value)=>{
    console.log(`[RESULT] ${label}: ${value}`);
};

module.exports = {
    logInfo,
    logResult
};
