//-----CLASSES-----
class Garden {
  constructor(id, name) {
    this.id = id;
    this.name = name;
    this.gardenZones = [];
  }
}

class GardenZone {
  constructor(id, name) {
    this.id = id;
    this.name = name;
    this.plants = []
  }
}

class Plant{
  constructor(id, name){
    this.id=id;
    this.name=name;
  }
}

class TrackerPolicy {
  constructor(id, name, description, targetType, interval) {
    this.id = id;
    this.name = name;
    this.description = description;
    this.targetType = targetType;
    this.interval = interval;
    this.assignments = [];
  }
}

class TrackerAssignment{
  constructor(id, assignedToId, startDate){
    this.id=id;
    this.assignedToId=assignedToId;
    this.startDate=startDate;
    this.events=[];
  }
}

class TrackerEvent{
  constructor(id, recordedTime, details){
    this.id=id;
    this.recordedTime=recordedTime;
    this.details=details;
  }
}

//-----MODEL GLOBALS-----
let gardens = [];
let trackerPolicies = [];

//-----INITIALIZATION-----
init();

async function init() {
  const initialInfo = await fetchInitialInfo();
  buildModel(initialInfo);
  populateNav();
}

//Fetch initial info
async function fetchInitialInfo() {
  //Fetch all gardens + gardenzones inside gardens
  try {
    const gardenResponse = await fetch("http://localhost:8080/api/gardens");
    if (!gardenResponse.ok) {
      throw new Error("Could not fetch garden information");
    }
    const gardenJson = await gardenResponse.json();

    const trackerPolicyResponse = await fetch(
      "http://localhost:8080/api/trackers"
    );
    if (!trackerPolicyResponse.ok) {
      throw new Error("Could not fetch tracker policy information");
    }
    const trackerPolicyJson = await trackerPolicyResponse.json();

    console.log(gardenJson);
    console.log(trackerPolicyJson);
    return [gardenJson, trackerPolicyJson];
  } catch (error) {
    console.error(error.message);
  }
}

//Build model
function buildModel(initialInfo) {
  //Build gardens + gardenzones + plants
  const gardenJson = initialInfo[0];
  for (const garden of gardenJson) {
    const newGarden = gardenMapper(garden);
    for (const gardenZone of garden["gardenZones"]) {
      const newGardenZone = gardenZoneMapper(gardenZone);
      for(const plant of gardenZone["plants"]){
        const newPlant = plantMapper(plant);
        newGardenZone.plants.push(newPlant);
      }
      newGarden.gardenZones.push(newGardenZone);
    }
    gardens.push(newGarden);
  }

  //Build tracker policies
  const trackerPolicyJson = initialInfo[1];
  for (const trackerPolicy of trackerPolicyJson) {
    const newTrackerPolicy = trackerPolicyMapper(trackerPolicy);
    for(const trackerAssignment of trackerPolicy["assignments"]){
      const newTrackerAssignment = trackerAssignmentMapper(trackerAssignment);
      for(const trackerEvent of trackerAssignment["events"]){
        const newTrackerEvent = trackerEventMapper(trackerEvent);
        newTrackerAssignment.events.push(newTrackerEvent);
      }
      newTrackerPolicy.assignments.push(newTrackerAssignment);
    }
    trackerPolicies.push(newTrackerPolicy);
  }
}

//Populate navigation bar
function populateNav() {
  //Populate gardens + gardenzones nav
  const gardensNavList = document.querySelector("#gardennavlist");
  gardensNavList.innerHTML = "";

  for (const garden of gardens) {
    const gardenLi = document.createElement("li");
    gardenLi.dataset.id = garden.id;
    gardenLi.dataset.type = "GARDEN";
    gardenLi.textContent = garden.name;
    gardenLi.addEventListener("click", clickNavItem);
    gardensNavList.appendChild(gardenLi);

    const gardenZoneUl = document.createElement("ul");
    for (const gardenZone of garden.gardenZones) {
      const gardenZoneLi = document.createElement("li");
      gardenZoneLi.dataset.id = gardenZone.id;
      gardenZoneLi.dataset.type = "GARDENZONE";
      gardenZoneLi.textContent = gardenZone.name;
      gardenZoneLi.addEventListener("click", clickNavItem);
      gardenZoneUl.appendChild(gardenZoneLi);
    }
    gardensNavList.appendChild(gardenZoneUl);
  }

  //Populate tracker policies nav
  const gardenTrackerPolicyNavList = document.querySelector(
    "#gardentrackerpolicynavlist"
  );
  gardenTrackerPolicyNavList.innerHTML = "";
  const gardenZoneTrackerPolicyNavList = document.querySelector(
    "#gardenzonetrackerpolicynavlist"
  );
  gardenZoneTrackerPolicyNavList.innerHTML = "";
  const plantTrackerPolicyNavList = document.querySelector(
    "#planttrackerpolicynavlist"
  );
  plantTrackerPolicyNavList.innerHTML = "";

  for (const trackerPolicy of trackerPolicies) {
    const trackerPolicyLi = document.createElement("li");
    trackerPolicyLi.dataset.id = trackerPolicy.id;
    trackerPolicyLi.dataset.type = "TRACKERPOLICY";
    trackerPolicyLi.textContent = trackerPolicy.name;
    trackerPolicyLi.addEventListener("click", clickNavItem);

    let listToAddTo;
    switch (trackerPolicy.targetType) {
      case "GARDEN":
        listToAddTo = gardenTrackerPolicyNavList;
        break;
      case "GARDENZONE":
        listToAddTo = gardenZoneTrackerPolicyNavList;
        break;
      case "PLANT":
        listToAddTo = plantTrackerPolicyNavList;
        break;
    }
    listToAddTo.appendChild(trackerPolicyLi);
  }
}

//-----EVENT LISTENERS-----
//Nav
function clickNavItem(event) {
  switch (event.target.dataset.type) {
    case "GARDEN":
      displayGardenInfo(event);
      break;
    case "GARDENZONE":
      displayGardenZoneInfo(event);
      break;
    case "TRACKERPOLICY":
      displayTrackerPolicyInfo(event);
      break;

      async function displayGardenInfo(event) {
        // const garden = gardens.find((garden)=>garden.id==event.target.dataset.id);
        // fetchPlantInfo(garden);
        console.log(garden.gardenZones);
      }
      function displayGardenZoneInfo(event) {
        console.log(event.target);
      }
      function displayTrackerPolicyInfo(event) {
        console.log(event.target);
      }
  }
}

// async function fetchPlantInfo(garden){
//   for(const gardenZone of garden.gardenZones){
//     gardenZone.plants=[];
//     try{
//       const plantResponse = await fetch(`http://localhost:8080/api/gardens/${garden.id}/zones/${gardenZone.id}/plants`);
//       if(!plantResponse.ok){
//         throw new Error(`Could not fetch plant info for garden zone: ${gardenZone}`);
//       }
//       const plantJson = await plantResponse.json();
//       for(const plant of plantJson){
//         const newPlant = plantMapper(plant);
//         gardenZone.plants.push(newPlant);
//       }
//     }
//     catch(error){
//       console.error(error.message);
//     }
//   }
// }

const createGardenBtn = document.querySelector("#creategarden");
createGardenBtn.addEventListener("click", clickCreateGarden);
async function clickCreateGarden() {
  const newGardenName = prompt("New garden name:");

  try {
    const gardenResponse = await fetch("http://localhost:8080/api/gardens", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        gardenName: newGardenName,
      }),
    });

    if (!gardenResponse.ok) {
      throw new Error(`Response status: ${gardenResponse.status}`);
    }

    const newGardenResponse = await gardenResponse.json();
    gardens.push(new Garden(newGardenResponse["gardenId"], newGardenName));

    populateNav();
  } catch (error) {
    console.error(error.message);
  }
}

const createTrackerPolicyBtn = document.querySelector("#createtrackerpolicy");
createTrackerPolicyBtn.addEventListener("click", clickCreateTrackerPolicy);
async function clickCreateTrackerPolicy() {
  const newTrackerPolicyName = prompt("New tracker policy name:");
  const newTrackerPolicyDescription = prompt("New tracker policy description:");
  const newTrackerPolicyTargetType = prompt(
    "New tracker policy target (GARDEN, GARDENZONE, PLANT):"
  );
  const newTrackerPolicyIntervalHours = prompt("Interval (hours):");

  try {
    const trackerResponse = await fetch("http://localhost:8080/api/trackers", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        trackerName: newTrackerPolicyName,
        trackerDescription: newTrackerPolicyDescription,
        targetType: newTrackerPolicyTargetType,
        intervalHours: newTrackerPolicyIntervalHours,
      }),
    });

    if (!trackerResponse.ok) {
      throw new Error(`Response status: ${trackerResponse.status}`);
    }

    const newTrackerResponse = await trackerResponse.json();

    trackerPolicies.push(
      new TrackerPolicy(
        newTrackerResponse["trackerPolicyId"],
        newTrackerPolicyName,
        newTrackerPolicyDescription,
        newTrackerPolicyTargetType,
        newTrackerPolicyIntervalHours
      )
    );

    populateNav();
  } catch (error) {
    console.error(error.message);
  }
}

//-----Information populating-----
//Aside
function clickEditDetails() {
  //Edit management entity details
}
function clickDeleteDetails() {
  //Delete management entity
}

//-----MAPPERS-----
function gardenMapper(gardenJson) {
  return new Garden(gardenJson["gardenId"], gardenJson["gardenName"]);
}

function gardenZoneMapper(gardenZoneJson) {
  return new GardenZone(
    gardenZoneJson["gardenZoneId"],
    gardenZoneJson["gardenZoneName"]
  );
}

function plantMapper(plantJson){
  return new Plant(
    plantJson["plantId"],
    plantJson["plantName"]
  );
}

function trackerPolicyMapper(trackerPolicyJson) {
  return new TrackerPolicy(
    trackerPolicyJson["trackerPolicyId"],
    trackerPolicyJson["name"],
    trackerPolicyJson["description"],
    trackerPolicyJson["targetType"],
    trackerPolicyJson["intervalHours"]
  );
}

function trackerAssignmentMapper(trackerAssignmentJson){
  return new TrackerAssignment(
    trackerAssignmentJson["trackerAssignmentId"],
    trackerAssignmentJson["trackableAssignedToId"],
    trackerAssignmentJson["startDate"]
  );
}

function trackerEventMapper(trackerEventJson){
  return new TrackerEvent(
    trackerEventJson["trackerEventId"],
    trackerEventJson["recordedTime"],
    trackerEventJson["details"]
  );
}

// //-----GARDEN CREATION-----
// async function createNewGarden() {
//   console.log("Creating new garden");
//   //TODO: Change to center window pop, rest of screen vignettes
//   const newGardenName = prompt("New garden name:");
//   console.log(newGardenName);

//   try {
//     const gardenResponse = await fetch("http://localhost:8080/api/gardens", {
//       method: "POST",
//       headers: {
//         "Content-Type": "application/json",
//       },
//       body: JSON.stringify({
//         gardenName: newGardenName,
//       }),
//     });

//     if (!gardenResponse.ok) {
//       throw new Error(`Response status: ${gardenResponse.status}`);
//     }

//     const newGarden = await gardenResponse.json();

//     gardens.push(new Garden(newGarden["gardenId"], newGardenName));
//     console.log(gardens.at(-1));
//     populateNavBar();
//   } catch (error) {
//     console.error(error.message);
//   }
// }

//--TRACKER CREATION
// async function createNewTracker(){
//     console.log("Creating new tracker");

//     const newTrackerName=prompt("New tracker name:");
//     const newTrackerDescription=prompt("New tracker description");
//     const newTrackerTargetType=prompt("New tracker target type (GARDEN GARDENZONE PLANT)");
//     const newTrackerIntervalHours=prompt("New tracker interval (hours)");

//     try{
//         const trackerResponse = await fetch("http://localhost:8080/api/trackers",{
//             method:"POST",
//             headers:{
//                 "Content-Type":"application/json"
//             },
//             body: JSON.stringify({
//                 "trackerName":newTrackerName,
//                 "trackerDescription":newTrackerDescription,
//                 "targetType":newTrackerTargetType,
//                 "intervalHours":newTrackerIntervalHours
//             })
//         })

//         if(!trackerResponse.ok){
//             throw new Error(`Response status: ${trackerResponse.status}`)
//         }

//         const newTracker = await trackerResponse.json();

//         trackerPolicies.push(new TrackerPolicy(newTracker["trackerPolicyId"],
//             newTrackerName,
//             newTrackerDescription,
//             newTrackerTargetType,
//             newTrackerIntervalHours
//         ));
//         console.log(trackerPolicies.at(-1));
//         populateNavBar();
//     }
//     catch(error){
//         console.error(error.message);
//     }
// }

// //--CLASS DECLARATIONS--
// class Garden{
//     constructor(gardenId, gardenName){
//         this.gardenId=gardenId;
//         this.gardenName=gardenName;
//         this.gardenZones=[]
//     }
// }

// class GardenZone{
//     constructor(gardenZoneId, gardenZoneName){
//         this.gardenZoneId = gardenZoneId;
//         this.gardenZoneName=gardenZoneName;
//         this.plants=[]
//     }
// }

// class Plant{
//     constructor(plantId, plantName){
//         this.plantId=plantId;
//         this.plantName=plantName;
//     }
// }

// class TrackerPolicy{
//     constructor(trackerPolicyId, trackerPolicyName, trackerPolicyDescription,
//         trackerPolicyTargetType, trackerPolicyInterval){
//             this.trackerPolicyId=trackerPolicyId;
//             this.trackerPolicyName=trackerPolicyName;
//             this.trackerPolicyDescription=trackerPolicyDescription;
//             this.trackerPolicyTargetType=trackerPolicyTargetType;
//             this.trackerPolicyInterval=trackerPolicyInterval;
//             this.trackerPolicyAssignments=[];
//         }
// }

// //--GLOBAL VARIABLES--
// let gardens=[];
// let trackerPolicies=[];

// const createGardenBtn = document.querySelector("#createGarden");
// createGardenBtn.addEventListener("click", createNewGarden);
// const createTrackerBtn = document.querySelector("#createTracker");
// createTrackerBtn.addEventListener("click", createNewTracker);

// const gardensNavList = document.querySelector("#gardensNavList");
// gardensNavList.addEventListener("click", navListSelector);

// const trackersNavList = document.querySelector("#trackersNavList");
// trackersNavList.addEventListener("click", navListSelector)

// function findGardenById(id){
//     return gardens.find((garden)=>garden.gardenId==id);
// }
// function findGardenZoneById(gardenId, gardenZoneId){
//     const garden = findGardenById(gardenId);
//     return garden.gardenZones.find((gardenZone)=>gardenZone.gardenZoneId==gardenZoneId);
// }
// function findTrackerById(id){
//     return trackerPolicies.find((tracker)=>tracker.trackerPolicyId==id);
// }

// //--NAV LIST SELECTION--
// function navListSelector(e){
//     if(e.target.hasAttribute("gardenid")){
//         displayGardenInfo(findGardenById(e.target.getAttribute("gardenid")));
//     }
//     else if(e.target.hasAttribute("gardenzoneid")){
//         const gardenZoneId = e.target.getAttribute("gardenzoneid");
//         const gardenId = e.target.parentNode.parentNode.getAttribute("gardenid");
//         displayGardenZoneInfo(findGardenZoneById(gardenId, gardenZoneId));

//     }
//     else if(e.target.hasAttribute("trackerid")){
//         displayTrackerInfo(findTrackerById(e.target.getAttribute("trackerid")));
//     }
// }

// const managingHeader = document.querySelector("#managingheader");
// const interiorDetailsHeader = document.querySelector("#interiordetailsheader");
// const interiorDetailsList = document.querySelector("#interiordetailslist");
// const assignmentDetailsHeader = document.querySelector("#assignmentdetailsheader");

// const selectedDetailsHeader = document.querySelector("#selecteddetailsheader");
// const selectedDetailsList = document.querySelector("#selecteddetailslist");

// function sumOfPlantsInGarden(garden){
//     return 999;
// }

// //--DISPLAY SELECTED NAV INFO--
// function displayGardenInfo(garden){
//     interiorDetailsList.innerHTML="";
//     selectedDetailsList.innerHTML="";
//     console.log("Displaying garden info for: ", garden);
//     managingHeader.textContent=`Managing garden: ${garden.gardenName}`
//     const interiorDetailsHeader = document.querySelector("#interiordetailsheader");
//     interiorDetailsHeader.textContent="Zones";
//     selectedDetailsHeader.textContent="Garden details";
//     assignmentDetailsHeader.textContent="Attached trackers";
//     interiorDetailsHeader.display="none";

//     //zones
//     const gardenZones = garden.gardenZones;
//     for(const gardenZone of gardenZones){
//         const gardenZoneLi = document.createElement("li");
//         gardenZoneLi.textContent=gardenZone.gardenZoneName;
//         interiorDetailsList.appendChild(gardenZoneLi);
//     }

//     //attached trackers

//     //selected details
//     const selectedName = document.createElement("li");
//     selectedName.textContent=`Name: ${garden.gardenName}`;
//     selectedDetailsList.appendChild(selectedName);

//     const selectedNumZones = document.createElement("li");
//     selectedNumZones.textContent=`# zones: ${garden.gardenZones.length}`;
//     selectedDetailsList.appendChild(selectedNumZones);

//     const selectedNumPlants = document.createElement("li");
//     selectedNumPlants.textContent=`# plants: ${sumOfPlantsInGarden(garden)}`;
//     selectedDetailsList.appendChild(selectedNumPlants);

// }

// function displayGardenZoneInfo(gardenZone){
//     interiorDetailsList.innerHTML="";
//     selectedDetailsList.innerHTML="";
//     console.log("Displaying zone info for: ", gardenZone);
//     managingHeader.textContent=`Managing zone: ${gardenZone.gardenZoneName}`
//     interiorDetailsHeader.textContent="Plants";
//     selectedDetailsHeader.textContent="Zone details";
//     assignmentDetailsHeader.textContent="Attached trackers";
//     interiorDetailsHeader.display="none";
//     const plants = gardenZone.plants;
//     for(const plant of plants){
//         const plantLi = document.createElement("li");
//         plantLi.textContent=plant.plantName;
//         interiorDetailsList.appendChild(plantLi);
//     }

//     //selected details
//     const selectedName = document.createElement("li");
//     selectedName.textContent=`Name: ${gardenZone.gardenZoneName}`;
//     selectedDetailsList.appendChild(selectedName);

//     const selectedNumPlants = document.createElement("li");
//     selectedNumPlants.textContent=`# plants: ${gardenZone.plants.length}`;
//     selectedDetailsList.appendChild(selectedNumPlants);

// }

// function displayPlantInfo(plant){

// }

// function displayTrackerInfo(tracker){
//     interiorDetailsList.innerHTML="";
//     selectedDetailsList.innerHTML="";
//     console.log("Displaying tracker info for: ", tracker);
//     managingHeader.textContent=`Managing tracker: ${tracker.trackerPolicyName}`
//     selectedDetailsHeader.textContent="Tracker details";
//     assignmentDetailsHeader.textContent="Assigned to";
//     interiorDetailsHeader.textContent="";

//     const selectedName = document.createElement("li");
//     selectedName.textContent=`Name: ${tracker.trackerPolicyName}`;
//     selectedDetailsList.appendChild(selectedName);

//     const selectedDescription = document.createElement("li");
//     selectedDescription.textContent=`Description: ${tracker.trackerPolicyDescription}`;
//     selectedDetailsList.appendChild(selectedDescription);

//     const selectedInterval = document.createElement("li");
//     selectedInterval.textContent=`Interval: ${tracker.trackerPolicyInterval} hours`;
//     selectedDetailsList.appendChild(selectedInterval);
// }

// //--INITIALIZATION--
// //Fetches all gardens and tracker policies
// async function fetchInitialInfo(){
//     try{
//         const gardenResponse = await fetch("http://localhost:8080/api/gardens");
//         if(!gardenResponse.ok){
//             throw new Error("Could not fetch gardens info");
//         }
//         const trackerPolicyResponse = await fetch("http://localhost:8080/api/trackers");
//         if(!trackerPolicyResponse.ok){
//             throw new Error("Could not fetch tracker policy info");
//         }

//         const gardenJson=await gardenResponse.json();
//         const trackerPoliciesJson= await trackerPolicyResponse.json();

//         return [gardenJson, trackerPoliciesJson];
//     }
//     catch(error){
//         console.error(error.message);
//     }
// }

// //Populates gardens and tracker policies
// function populateInitialInfo(gardensJson, trackerPoliciesJson){
//     //Garden + zone info
//     for(const gardenJson of gardensJson){
//         const newGarden = gardenJsonToObject(gardenJson);
//         for(const gardenZone of gardenJson["gardenZones"]){
//             const newGardenZone=gardenZoneJsonToObject(gardenZone);
//             newGarden.gardenZones.push(newGardenZone);
//         }
//         gardens.push(newGarden);
//     }

//     //Policy info
//     for(const trackerPolicyJson of trackerPoliciesJson){
//         const newTrackerPolicy = trackerPolicyJsonToObject(trackerPolicyJson);
//         trackerPolicies.push(newTrackerPolicy);
//     }
// }

// //Populates nav bar from garden and tracker policy global variables
// function populateNavBar(){
//     const gardensNavList = document.querySelector("#gardensNavList");
//     gardensNavList.innerHTML="";

//     for (const garden of gardens) {
//         const gardenLi = document.createElement("li");
//         gardenLi.setAttribute("gardenId", garden.gardenId)
//         gardenLi.textContent = garden.gardenName;

//         const zonesUl = document.createElement("ul");

//         for (const gardenZone of garden.gardenZones) {
//             const zoneLi = document.createElement("li");
//             zoneLi.setAttribute("gardenZoneId", gardenZone.gardenZoneId);
//             zoneLi.textContent = gardenZone.gardenZoneName;
//             zonesUl.appendChild(zoneLi);
//         }

//         gardenLi.appendChild(zonesUl);   // nest zones under garden
//         gardensNavList.appendChild(gardenLi);
//     }

//     const trackersNavList=document.querySelector("#trackersNavList");
//     trackersNavList.innerHTML="";

//     //garden
//     const gardenTrackers = trackerPolicies.filter((tracker)=>tracker.trackerPolicyTargetType=="GARDEN");
//     const gardenTrackerTitleLi = document.createElement("li");
//     gardenTrackerTitleLi.textContent="Garden";
//     trackersNavList.appendChild(gardenTrackerTitleLi);

//     const gardenTrackerUl = document.createElement("ul");
//     trackersNavList.appendChild(gardenTrackerUl);
//     for(const gardenTracker of gardenTrackers){
//         const gardenTrackerLi = document.createElement("li");
//         gardenTrackerLi.setAttribute("trackerId", gardenTracker.trackerPolicyId)
//         gardenTrackerLi.textContent=gardenTracker.trackerPolicyName;
//         gardenTrackerUl.appendChild(gardenTrackerLi);
//     }

//     //zone
//     const gardenZoneTrackers = trackerPolicies.filter((tracker)=>tracker.trackerPolicyTargetType=="GARDENZONE");
//     const gardenZoneTrackerTitleLi = document.createElement("li");
//     gardenZoneTrackerTitleLi.textContent="Garden zone";
//     trackersNavList.appendChild(gardenZoneTrackerTitleLi);

//     const gardenZoneTrackerUl = document.createElement("ul");
//     trackersNavList.appendChild(gardenZoneTrackerUl);
//     for(const gardenZoneTracker of gardenZoneTrackers){
//         const gardenZoneTrackerLi = document.createElement("li");
//         gardenZoneTrackerLi.setAttribute("trackerId", gardenZoneTracker.trackerPolicyId)
//         gardenZoneTrackerLi.textContent=gardenZoneTracker.trackerPolicyName;
//         gardenZoneTrackerUl.appendChild(gardenZoneTrackerLi);
//     }

//     //plant
//     const plantTrackers = trackerPolicies.filter((tracker)=>tracker.trackerPolicyTargetType=="PLANT");
//     const plantTrackerTitleLi = document.createElement("li");
//     plantTrackerTitleLi.textContent="Plant";
//     trackersNavList.appendChild(plantTrackerTitleLi);

//     const plantTrackerUl = document.createElement("ul");
//     trackersNavList.appendChild(plantTrackerUl);
//     for(const plantTracker of plantTrackers){
//         const plantTrackerLi = document.createElement("li");
//         plantTrackerLi.setAttribute("trackerId", plantTracker.trackerPolicyId)
//         plantTrackerLi.textContent=plantTracker.trackerPolicyName;
//         plantTrackerUl.appendChild(plantTrackerLi);
//     }
// }

// async function init(){
//     const[gardensJson, trackerPoliciesJson] = await fetchInitialInfo();
//     populateInitialInfo(gardensJson, trackerPoliciesJson);
//     console.log("OBJECTS...");
//     console.log("Gardens:", gardens);
//     console.log("Tracker Policies:", trackerPolicies);

//     populateNavBar(gardens, trackerPolicies);
// }

// init();

// //--GARDEN CREATION--
// async function createNewGarden(){
//     console.log("Creating new garden");
//     //TODO: Change to center window pop, rest of screen vignettes
//     const newGardenName = prompt("New garden name:");
//     console.log(newGardenName);

//     try{
//         const gardenResponse = await fetch("http://localhost:8080/api/gardens",{
//             method: "POST",
//             headers: {
//                 "Content-Type": "application/json"
//             },
//             body: JSON.stringify({
//                 "gardenName":newGardenName
//             })
//         })

//         if(!gardenResponse.ok){
//             throw new Error(`Response status: ${gardenResponse.status}`)
//         }

//         const newGarden = await gardenResponse.json();

//         gardens.push(new Garden(newGarden["gardenId"], newGardenName));
//         console.log(gardens.at(-1));
//         populateNavBar();
//     }
//     catch(error){
//         console.error(error.message);
//     }
// }

// //--TRACKER CREATION
// async function createNewTracker(){
//     console.log("Creating new tracker");

//     const newTrackerName=prompt("New tracker name:");
//     const newTrackerDescription=prompt("New tracker description");
//     const newTrackerTargetType=prompt("New tracker target type (GARDEN GARDENZONE PLANT)");
//     const newTrackerIntervalHours=prompt("New tracker interval (hours)");

//     try{
//         const trackerResponse = await fetch("http://localhost:8080/api/trackers",{
//             method:"POST",
//             headers:{
//                 "Content-Type":"application/json"
//             },
//             body: JSON.stringify({
//                 "trackerName":newTrackerName,
//                 "trackerDescription":newTrackerDescription,
//                 "targetType":newTrackerTargetType,
//                 "intervalHours":newTrackerIntervalHours
//             })
//         })

//         if(!trackerResponse.ok){
//             throw new Error(`Response status: ${trackerResponse.status}`)
//         }

//         const newTracker = await trackerResponse.json();

//         trackerPolicies.push(new TrackerPolicy(newTracker["trackerPolicyId"],
//             newTrackerName,
//             newTrackerDescription,
//             newTrackerTargetType,
//             newTrackerIntervalHours
//         ));
//         console.log(trackerPolicies.at(-1));
//         populateNavBar();
//     }
//     catch(error){
//         console.error(error.message);
//     }
// }

// //--MAPPERS--
// function gardenJsonToObject(gardenJson){
//     return new Garden(
//         gardenJson["gardenId"],
//         gardenJson["gardenName"]
//     );
// }

// function gardenZoneJsonToObject(gardenZoneJson){
//     return new GardenZone(
//         gardenZoneJson["gardenZoneId"],
//         gardenZoneJson["gardenZoneName"]
//     );
// }

// function plantJsonToObject(plantJson){
//     return new Plant(
//         plantJson["plantId"],
//         plantJson["plantName"]
//     );

// }

// function trackerPolicyJsonToObject(trackerPolicyJson){
//     return new TrackerPolicy(
//         trackerPolicyJson["trackerPolicyId"],
//         trackerPolicyJson["name"],
//         trackerPolicyJson["description"],
//         trackerPolicyJson["targetType"],
//         trackerPolicyJson["intervalHours"]
//     );
// }

//Nav
//Garden
//Click: Garden name
//Click: Zone name
//Tracking
//Click: Tracker policy

//Create
//Click: create garden
//Click: create tracker

//Main
//GARDEN, GARDENZONE
//Subentity list
//Foreach subentity:
//Click: view subentity
//Click: delete subentity

//Assigned tracker list
//Foreach policy:
//Click: view policy
//Click: detach policy
//Click: record event

//Click: attach policy

//PLANT
//Assigned tracker list
//Foreach policy:
//Click: view policy
//Click: detach policy
//Click: record event

//Click: attach policy

//POLICY ENTITY
//Assignment list
//Foreach assignment:
//Click: view assignment entity
//Click: detach assignment

//Click: attach assignment

//Aside
//ENTITY, POLICY
//Click: edit entity/policy details
//Click: delete entity/policy
