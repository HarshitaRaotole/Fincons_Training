//async/await 
function fetchUser(){
    return new Promise((resolve)=>{
        setTimeout(()=>{
            console.log("User fetched");
            resolve({id:1, name: "Harshita"});
        },1000);
    });
}

function fetchOrders(userId){
    return new Promise((resolve)=>{
        setTimeout(()=>{
            console.log("Order fetched");
            resolve(["Laptop","Phone"]);
        },1000);
    });
}

function processPayment(order){
    return new Promise((resolve)=>{
        setTimeout(()=>{
            console.log("Payment processing");
            resolve("Payment completed successfully")
        })
    },1000);
}

async function main(){
    try{
        console.log("Application started");

        const user = await fetchUser();
        const order = await fetchOrders(user.id);
        const payment = await processPayment(order[0]);

        console.log(payment);
    }catch(error){
        console.log("Error:",error);
    }
}
main();