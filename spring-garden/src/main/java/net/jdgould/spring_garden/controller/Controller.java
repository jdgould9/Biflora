package net.jdgould.spring_garden.controller;

import net.jdgould.spring_garden.model.Garden;
import net.jdgould.spring_garden.model.GardenZone;
import net.jdgould.spring_garden.model.Plant;
import net.jdgould.spring_garden.service.GardenService;
import net.jdgould.spring_garden.service.GardenZoneService;
import net.jdgould.spring_garden.service.PlantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/gardens")
public class Controller {

    private final GardenService gardenService;
    private final GardenZoneService gardenZoneService;
    private final PlantService plantService;

    @Autowired
    public Controller(GardenService gardenService, GardenZoneService gardenZoneService, PlantService plantService) {
        this.gardenService = gardenService;
        this.gardenZoneService = gardenZoneService;
        this.plantService = plantService;

    }

    //GARDEN
    //Get all gardens
    @GetMapping("")
    public List<Garden> getAllGardens() {
        return gardenService.findAllGardens();
    }

    //Get garden by Id
    @GetMapping("/{gardenId}")
    public Garden getGardenById(@PathVariable Long gardenId) {
        return gardenService.findGardenById(gardenId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Garden not found"));
    }

    //Create garden
    @PostMapping("")
    public void createGarden(@RequestBody Garden gardenRequest) {
        gardenService.addGarden(gardenRequest);
    }

    //GARDENZONE
    //Get all garden zones in a garden
    @GetMapping("/{gardenId}/zones")
    public List<GardenZone> getAllGardenZonesInGarden(@PathVariable Long gardenId) {
        return gardenZoneService.findAllGardenZonesByGardenId(gardenId);
    }

    //Get garden zone by garden Id and garden zone Id
    @GetMapping("/{gardenId}/zones/{gardenZoneId}")
    public GardenZone getGardenZoneById(@PathVariable Long gardenId, @PathVariable Long gardenZoneId) {
        return gardenZoneService.findGardenZoneByGardenIdAndId(gardenZoneId, gardenId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Garden not found"));
    }

    //Create garden zone
    @PostMapping("/{gardenId}/zones")
    public GardenZone createGardenZone(@PathVariable Long gardenId, @RequestBody Map<String, String> gardenZoneRequest) {
        String gardenZoneName = gardenZoneRequest.get("gardenZoneName");
        return gardenZoneService.addGardenZone(gardenId, gardenZoneName);
    }

    //PLANT
    //Get all plants in a garden zone
    @GetMapping("/{gardenId}/zones/{gardenZoneId}/plants")
    public List<Plant> getAllPlantsInGardenZone(@PathVariable Long gardenId, @PathVariable Long gardenZoneId){

    }

    //Get plant by plant Id and garden zone Id and garden Id
    @GetMapping("/{gardenId}/zones/{gardenZoneId}/plants/{plantId}")
    public Plant getPlantById(@PathVariable Long gardenId, @PathVariable Long gardenZoneId, @PathVariable Long plantId){

    }

    //Create plant
    @PostMapping("/{gardenId}/zones/{gardenZoneId}/plants")
    public Plant createPlant(@PathVariable Long gardenId, @PathVariable Long gardenZoneId, @RequestBody Map<String,String> plantRequest){
        String plantName = plantRequest.get("plantName");

    }




    //Get all plants in a garden zone
//    public List<Plant> getallPlantsInGardenZone(Long gardenId, Long gardenZoneId) {
//
//    }

    //Get plant by Id
//    public Plant getPlantById(Long gardenId, Long gardenZoneId, Long plantId) {
//
//    }


//    //Get all plants
//    @GetMapping("")
//    public List<Plant> getAllPlants(){
//        return plantService.getAllPlants();
//    }
//
//    //Get plant by ID
//    @GetMapping("/{id}")
//    public Plant getPlantById(@PathVariable Long id){
//        return plantService.getPlantById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found"));
//    }
//
//    //Add a tree
//    @PostMapping("/tree/{name}")
//    public Plant addTree(@PathVariable String name) {
//        return plantService.addPlant(new Tree(name));
//    }
//
//    //Add a flower
//    @PostMapping("/flower/{name}")
//    public Plant addFlower(@PathVariable String name) {
//        return plantService.addPlant(new Flower(name));
//    }
//
//    //Water a plant
//    @PostMapping("/water/{id}")
//    public Plant waterPlant(@PathVariable Long id){
//        return plantService.waterPlant(id);
//    }


}