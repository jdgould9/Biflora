// =====================
// CLASSES
// =====================
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
    this.plants = [];
  }
}

class Plant {
  constructor(id, name) {
    this.id = id;
    this.name = name;
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

class TrackerAssignment {
  constructor(id, assignedToId, startDate) {
    this.id = id;
    this.assignedToId = assignedToId;
    this.startDate = startDate;
    this.events = [];
  }
}

class TrackerEvent {
  constructor(id, recordedTime, details) {
    this.id = id;
    this.recordedTime = recordedTime;
    this.details = details;
  }
}

// =====================
// GLOBAL STATE
// =====================
let gardens = [];
let trackerPolicies = [];

// =====================
// INITIALIZATION
// =====================
init();

async function init() {
  const initialInfo = await fetchInitialInfo();
  buildModel(initialInfo);
  populateNav();
}

// =====================
// FETCHING
// =====================
async function fetchInitialInfo() {
  try {
    const gardenResponse = await fetch("http://localhost:8080/api/gardens");
    if (!gardenResponse.ok) throw new Error("Could not fetch gardens");
    const gardenJson = await gardenResponse.json();

    const trackerResponse = await fetch("http://localhost:8080/api/trackers");
    if (!trackerResponse.ok) throw new Error("Could not fetch trackers");
    const trackerJson = await trackerResponse.json();

    return [gardenJson, trackerJson];
  } catch (error) {
    console.error(error.message);
  }
}

// =====================
// MODEL BUILDING
// =====================
function buildModel([gardenJson, trackerPolicyJson]) {
  for (const garden of gardenJson) {
    const newGarden = gardenMapper(garden);

    for (const zone of garden.gardenZones) {
      const newZone = gardenZoneMapper(zone);

      for (const plant of zone.plants) {
        newZone.plants.push(plantMapper(plant));
      }

      newGarden.gardenZones.push(newZone);
    }

    gardens.push(newGarden);
  }

  for (const policy of trackerPolicyJson) {
    const newPolicy = trackerPolicyMapper(policy);

    for (const assignment of policy.assignments) {
      const newAssignment = trackerAssignmentMapper(assignment);

      for (const event of assignment.events) {
        newAssignment.events.push(trackerEventMapper(event));
      }

      newPolicy.assignments.push(newAssignment);
    }

    trackerPolicies.push(newPolicy);
  }
}

// =====================
// DOM REFERENCES
// =====================
const managingHeader = document.querySelector("#managingheader");
const interiorDetailsHeader = document.querySelector("#interiordetailsheader");
const interiorDetailsList = document.querySelector("#interiordetailslist");
const assignmentDetailsHeader = document.querySelector(
  "#assignmentdetailsheader"
);
const assignmentDetailsList = document.querySelector("#assignmentdetailslist");
const selectedDetailsHeader = document.querySelector("#selecteddetailsheader");
const selectedDetailsList = document.querySelector("#selecteddetailslist");

// =====================
// UTILITIES
// =====================
function clearMainInnerHtml() {
  managingHeader.textContent = "";
  interiorDetailsHeader.textContent = "";
  interiorDetailsList.innerHTML = "";
  assignmentDetailsHeader.textContent = "";
  assignmentDetailsList.innerHTML = "";
  selectedDetailsHeader.textContent = "";
  selectedDetailsList.innerHTML = "";
}

// =====================
// NAVIGATION
// =====================
function populateNav() {
  const gardensNavList = document.querySelector("#gardennavlist");
  gardensNavList.innerHTML = "";

  for (const garden of gardens) {
    const gardenLi = createNavItem(garden.name, garden.id, "GARDEN");
    gardensNavList.appendChild(gardenLi);

    const zoneUl = document.createElement("ul");
    for (const zone of garden.gardenZones) {
      const zoneLi = createNavItem(zone.name, zone.id, "GARDENZONE");
      zoneLi.dataset.parentGardenId = garden.id;
      zoneUl.appendChild(zoneLi);
    }
    gardensNavList.appendChild(zoneUl);
  }

  populateTrackerNav();
}

function populateTrackerNav() {
  const lists = {
    GARDEN: document.querySelector("#gardentrackerpolicynavlist"),
    GARDENZONE: document.querySelector("#gardenzonetrackerpolicynavlist"),
    PLANT: document.querySelector("#planttrackerpolicynavlist"),
  };

  Object.values(lists).forEach((ul) => (ul.innerHTML = ""));

  for (const policy of trackerPolicies) {
    const li = createNavItem(policy.name, policy.id, "TRACKERPOLICY");
    lists[policy.targetType].appendChild(li);
  }
}

function createNavItem(text, id, type) {
  const li = document.createElement("li");
  li.textContent = text;
  li.dataset.id = id;
  li.dataset.type = type;
  li.addEventListener("click", clickNavItem);
  return li;
}

// =====================
// NAV HANDLER
// =====================
function clickNavItem(event) {
  clearMainInnerHtml();

  switch (event.target.dataset.type) {
    case "GARDEN":
      displayGardenInfo(gardens.find((g) => g.id == event.target.dataset.id));
      break;

    case "GARDENZONE":
      const garden = gardens.find(
        (g) => g.id == event.target.dataset.parentGardenId
      );
      displayGardenZoneInfo(
        garden.gardenZones.find((z) => z.id == event.target.dataset.id)
      );
      break;

    case "TRACKERPOLICY":
      displayTrackerPolicyInfo(
        trackerPolicies.find((p) => p.id == event.target.dataset.id)
      );
      break;
  }
}

// =====================
// DISPLAY FUNCTIONS
// =====================
function displayGardenInfo(garden) {
  managingHeader.textContent = `Managing garden: ${garden.name}`;
  interiorDetailsHeader.textContent = "Zones:";
  selectedDetailsHeader.textContent = "Garden details:";

  for (const zone of garden.gardenZones) {
    const div = document.createElement("div");

    div.appendChild(
      Object.assign(document.createElement("p"), {
        textContent: zone.name,
      })
    );

    const viewBtn = document.createElement("button");
    viewBtn.textContent = "View zone";
    viewBtn.onclick = () => {
      clearMainInnerHtml();
      displayGardenZoneInfo(zone);
    };

    selectedDetailsList.innerHTML = `
    <p>${garden.name}</p>
    <p>${garden.gardenZones.length} zones</p>
  `;

    const deleteBtn = document.createElement("button");
    deleteBtn.textContent = "Delete zone";
    deleteBtn.onclick = () => {
      const deleteConfirm = confirm(
        "Are you sure you want to delete this zone?"
      );
      if (deleteConfirm) {
        const newZones = garden.gardenZones.filter((z) => z != zone);
        garden.gardenZones = newZones;
        populateNav();
        div.remove();
        selectedDetailsList.innerHTML = `
    <p>${garden.name}</p>
    <p>${garden.gardenZones.length} zones</p>
  `;
      }
      deleteZoneFetch(zone);
    };

    div.appendChild(viewBtn);
    div.appendChild(deleteBtn);
    interiorDetailsList.appendChild(div);
  }
}

async function deleteZoneFetch(zoneId) {
  try {
    const res = await fetch(
      `http://localhost:8080/api/gardenzones/${zoneId}`,
      { method: "DELETE" }
    );

    if (!res.ok) throw new Error("Failed to delete zone");
  } catch (err) {
    console.error(err.message);
  }
}


function displayGardenZoneInfo(zone) {
  managingHeader.textContent = `Managing garden zone: ${zone.name}`;
  interiorDetailsHeader.textContent = "Plants:";
  selectedDetailsHeader.textContent = "Zone details:";

  for (const plant of zone.plants) {
    const div = document.createElement("div");

    div.appendChild(
      Object.assign(document.createElement("p"), {
        textContent: plant.name,
      })
    );

    const viewBtn = document.createElement("button");
    viewBtn.textContent = "View plant";
    viewBtn.onclick = () => {
      clearMainInnerHtml();
      displayPlantInfo(plant);
    };

    const deleteBtn = document.createElement("button");
    deleteBtn.textContent = "Delete plant";
    deleteBtn.onclick = () => {
      const deleteConfirm = confirm(
        "Are you sure you want to delete this plant?"
      );
      if (deleteConfirm) {
        const newPlants = zone.plants.filter((p) => p != plant);
        zone.plants = newPlants;
        populateNav();
        div.remove();
        selectedDetailsList.innerHTML = `
    <p>${zone.name}</p>
    <p>${zone.plants.length} plants</p>
  `;
      }
      deletePlantFetch();
    };

    div.appendChild(viewBtn);
    div.appendChild(deleteBtn);
    interiorDetailsList.appendChild(div);
  }

  selectedDetailsList.innerHTML = `
    <p>${zone.name}</p>
    <p>${zone.plants.length} plants</p>
  `;

  const editDetailsBtn = document.querySelector("#editdetails");
  editDetailsBtn.onclick = () =>{
    const deleteConfirm = confirm(
        "Are you sure you want to delete this zone?"
      );

  }

  const deleteItemBtn = document.querySelector("#deleteitem");
}

function deletePlantFetch() {}

async function displayTrackerPolicyInfo(policy) {
  managingHeader.textContent = `Managing tracker: ${policy.name}`;
  assignmentDetailsHeader.textContent = "Assignments:";
  selectedDetailsHeader.textContent = "Policy details:";

  for (const assignment of policy.assignments) {
    const hierarchy = await getHierarchy(assignment.assignedToId);
    const div = document.createElement("div");

    div.appendChild(
      Object.assign(document.createElement("p"), {
        textContent: hierarchy.name,
      })
    );

    const viewBtn = document.createElement("button");
    viewBtn.textContent = "View";
    viewBtn.onclick = () => clickViewAssignment(assignment, hierarchy);

    div.appendChild(viewBtn);
    assignmentDetailsList.appendChild(div);
  }

  selectedDetailsList.innerHTML = `
    <p>${policy.name}</p>
    <p>${policy.description}</p>
    <p>${policy.assignments.length} assignments</p>
  `;
}

function displayPlantInfo(plant) {
  managingHeader.textContent = `Managing plant: ${plant.name}`;
  selectedDetailsHeader.textContent = "Plant details:";
  selectedDetailsList.innerHTML = `<p>${plant.name}</p>`;
}

// =====================
// HIERARCHY
// =====================
async function getHierarchy(id) {
  const res = await fetch(
    `http://localhost:8080/api/trackers/trackables/${id}/hierarchy`
  );
  return res.json();
}

function clickViewAssignment(_, hierarchy) {
  clearMainInnerHtml();

  switch (hierarchy.type) {
    case "GARDEN": {
      const garden = gardens.find((g) => g.id == hierarchy.id);
      if (!garden) return;
      displayGardenInfo(garden);
      break;
    }

    case "GARDENZONE": {
      const parentGarden = gardens.find((g) => g.id == hierarchy.parentId);
      if (!parentGarden) return;

      const gardenZone = parentGarden.gardenZones.find(
        (z) => z.id == hierarchy.id
      );
      if (!gardenZone) return;

      displayGardenZoneInfo(gardenZone);
      break;
    }

    case "PLANT": {
      const grandParentGarden = gardens.find(
        (g) => g.id == hierarchy.grandParentId
      );
      if (!grandParentGarden) return;

      const parentZone = grandParentGarden.gardenZones.find(
        (z) => z.id == hierarchy.parentId
      );
      if (!parentZone) return;

      const plant = parentZone.plants.find((p) => p.id == hierarchy.id);
      if (!plant) return;

      displayPlantInfo(plant);
      break;
    }

    default:
      console.warn("Unknown hierarchy type:", hierarchy.type);
  }
}

// =====================
// MAPPERS
// =====================
const gardenMapper = (g) => new Garden(g.gardenId, g.gardenName);
const gardenZoneMapper = (z) =>
  new GardenZone(z.gardenZoneId, z.gardenZoneName);
const plantMapper = (p) => new Plant(p.plantId, p.plantName);
const trackerPolicyMapper = (t) =>
  new TrackerPolicy(
    t.trackerPolicyId,
    t.name,
    t.description,
    t.targetType,
    t.intervalHours
  );
const trackerAssignmentMapper = (a) =>
  new TrackerAssignment(
    a.trackerAssignmentId,
    a.trackableAssignedToId,
    a.startDate
  );
const trackerEventMapper = (e) =>
  new TrackerEvent(e.trackerEventId, e.recordedTime, e.details);
