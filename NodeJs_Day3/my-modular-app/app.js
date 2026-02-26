const math = require('./math');
const logger = require('./logger');

const _= require('lodash');
logger.logInfo("Application started...");

const num1 = 10;
const num2=5;

const sum=math.add(num1,num2);
logger.logResult("Addition",sum);

const diff = math.subtract(num1,num2);
logger.logResult("Subtraction",diff);

const numbers = [1,2,2,3,4,4,5];
const uniqueNumbers = _.uniq(numbers);

logger.logResult("Cleaned Array (via Lodash)", uniqueNumbers);

logger.logResult("Task completed","Success");
