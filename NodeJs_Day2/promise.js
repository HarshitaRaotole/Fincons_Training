//promise based 
function fetchUser(){
    return new Promise((resolve,reject)=>{
        setTimeout(()=>{
            console.log("Fetching user..");
            resolve({id : 1, name:"Harshita"});
        },1000);
    });
}

function fetchOrders(userId){
    return new Promise((resolve,reject)=>{
        setTimeout(()=>{
            console.log("Fetching orders...");
            resolve(["Order1", "Order2"]);
        },1000);
    });
}

function processPayment(order){
    return new Promise((resolve,reject)=>{
        setTimeout(()=>{
            console.log("Processing payment...");
            resolve("Payment successful");
        },1000);
    });
}

//Promise chaining
fetchUser()
.then(user => fetchOrders(user.id))
.then(orders => processPayment(orders[0]))
.then(result => console.log(result))
.catch(error => console.log(error));