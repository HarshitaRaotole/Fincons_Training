//Promise function
function getUser(){
    return new Promise((resolve)=>{
        setTimeout(()=>{
            resolve("Harshita");
        },1000);
    });
}

//Promise version
getUser().then(user =>{
    console.log("Promise:",user);
});

//async/await version
async function showUser(){
    const user = await getUser();
    console.log("Async/Await:", user);
}
showUser();