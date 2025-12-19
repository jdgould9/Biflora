package net.jdgould.spring_garden.controller;

import net.jdgould.spring_garden.dto.plant.PlantCreationRequestDTO;
import net.jdgould.spring_garden.dto.plant.PlantCreationResponseDTO;
import net.jdgould.spring_garden.dto.plant.PlantGetResponseDTO;
import net.jdgould.spring_garden.model.Plant;
import net.jdgould.spring_garden.service.PlantService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gardens/{gardenId}/zones/{gardenZoneId}/plants")
public class PlantController {
    private PlantService plantService;

    public PlantController(PlantService plantService){
        this.plantService=plantService;
    }

    //Get all plants in a garden zone
    @GetMapping("")
    public List<PlantGetResponseDTO> getAllPlantsInGardenZone(@PathVariable("gardenId") Long gardenId, @PathVariable("gardenZoneId") Long gardenZoneId){
        return plantService.findAllPlantsInZone(gardenId, gardenZoneId);
    }

    //Get plant by plant Id and garden zone Id and garden Id
    @GetMapping("/{plantId}")
    public PlantGetResponseDTO getPlantById(@PathVariable("gardenId")  Long gardenId, @PathVariable("gardenZoneId") Long gardenZoneId, @PathVariable("plantId") Long plantId){
        return plantService.findPlantInZoneById(gardenId, gardenZoneId, plantId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Garden not found"));
    }

    //Create plant
    @PostMapping("")
    public PlantCreationResponseDTO createPlant(@PathVariable("gardenId")  Long gardenId, @PathVariable("gardenZoneId") Long gardenZoneId, @RequestBody PlantCreationRequestDTO plantCreationRequestDTO){
        return plantService.addPlantToGardenZone(gardenId, gardenZoneId, plantCreationRequestDTO);
    }
}
