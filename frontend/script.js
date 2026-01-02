const gardenBtn = document.querySelector("#displaygardens");
gardenBtn.addEventListener("click", displayInfo);
const output = document.querySelector("ul");


async function displayInfo(){
    try{
        const response = await fetch('http://localhost:8080/api/gardens');
        if(!response.ok){
            throw new Error(`Response status: ${response.status}`);
        }
        const result = await response.json();



        const newList = document.createElement("ul");

        for (const item of result){
            console.log(item);
        }



    }
    catch(error){
        console.error(error.message);
    }
}





    // console.log("Click!");
    // try{
    //     const response = await fetch('http://localhost:8080/api/gardens');
    //     if(!response.ok){
    //         throw new Error(`Response status: ${response.status}`);
    //     }
    //     const result = await response.json();

    //     const newListResult = document.createElement("ul");
    //     for(const item of result){
        

    //         const newListItem = document.createElement("li");
    //         let  zones="";
    //         for(const zone of item.gardenZones){
    //             zones+=zone.gardenZoneId;
    //             zones+= ".";
    //             zones+=zone.gardenZoneName;

    //         }
    //         newListItem.textContent=`ID: ${item.gardenId}, NAME: ${item.gardenName}, ZONES:${zones}`;
    //         newListResult.appendChild(newListItem);
    //     }

    //     output.appendChild(newListResult);



        
    // }
    // catch (error){
    //     console.error(error.message);
    // }



// fetch('http://localhost:8080/api/gardens')
// .then(response=>response.json())
// .then(data=>console.log(data));


// fetch('http://localhost:8080/api/content')
// .then(response => response.json())
// .then(data => console.log(data));
