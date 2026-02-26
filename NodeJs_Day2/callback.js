//Simulate fetching user using callbck
console.log("Start application...");

function fetchUser(callback){
    setTimeout(()=>{
        console.log("Fetching user...");
        callback({id:1, name:"Harshita"});
    },1000);
}

function fetchOrders(userId, callback){
    setTimeout(()=>{
        console.log("Fetching orders for user:", userId);
        callback(["Order1","Order2"]);
    },1000);
}

function processPayment(order,callback){
    setTimeout(()=>{
        console.log("Processing payment for: ", order);
        callback("Payment succesful");
    },1000);
}

//Callback chaining (callback hell example)
fetchUser((user)=>{
    fetchOrders(user.id,(orders)=>{
        processPayment(orders[0],(message)=>{
            console.log(message);
        });
    });
});



