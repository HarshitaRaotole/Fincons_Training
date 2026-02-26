function fetchUser(){
    return new Promise((resolve,reject)=>{
        const success = false;

        setTimeout(()=>{
            if(success){
                resolve("User fetched");
            }else{
                reject("Failed to fetch user");
            }
        },1000);
    });
}

async function main() {
    try{
        const result = await fetchUser();
        console.log(result);
    }catch(error){
        console.log("Handled error:",error);
    }
}
main();