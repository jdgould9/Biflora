package net.jdgould.spring_garden.controller;

import net.jdgould.spring_garden.dto.plant.PlantCreationRequestDTO;
import net.jdgould.spring_garden.dto.plant.PlantCreationResponseDTO;
import net.jdgould.spring_garden.dto.plant.PlantGetResponseDTO;
import net.jdgould.spring_garden.dto.tracker.PlantTrackerEventCreationRequestDTO;
import net.jdgould.spring_garden.dto.tracker.TrackerEventCreationResponseDTO;
import net.jdgould.spring_garden.dto.tracker.TrackerEventDTO;
import net.jdgould.spring_garden.model.plant.PlantTrackerEventType;
import net.jdgould.spring_garden.service.PlantService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

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
    public PlantGetResponseDTO getPlantById(@PathVariable("gardenId")  Long gardenId,
                                            @PathVariable("gardenZoneId") Long gardenZoneId,
                                            @PathVariable("plantId") Long plantId){
        return plantService.findPlantInZoneById(gardenId, gardenZoneId, plantId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "IMPLEMENT ME"));
    }

    //Create plant
    @PostMapping("")
    public PlantCreationResponseDTO createPlant(@PathVariable("gardenId")  Long gardenId,
                                                @PathVariable("gardenZoneId") Long gardenZoneId,
                                                @RequestBody PlantCreationRequestDTO request){
        return plantService.addPlantToGardenZone(gardenId, gardenZoneId, request);
    }

    //Record tracker event
    @PostMapping("/{plantId}/tracker")
    public TrackerEventCreationResponseDTO createTrackerEvent(@PathVariable("gardenId") Long gardenId,
                                                              @PathVariable("gardenZoneId") Long gardenZoneId,
                                                              @PathVariable("plantId") Long plantId,
                                                              @RequestBody PlantTrackerEventCreationRequestDTO request){
        return plantService.recordEvent(gardenId, gardenZoneId, plantId, request);

    }

    //Get full tracker event history
    @GetMapping("/{plantId}/tracker/{eventType}")
    public List<TrackerEventDTO> getTrackerEventHistory(@PathVariable("gardenId") Long gardenId,
                                                        @PathVariable("gardenZoneId") Long gardenZoneId,
                                                        @PathVariable("plantId") Long plantId,
                                                        @PathVariable("eventType") PlantTrackerEventType eventType){
        return plantService.getEventHistory(gardenId, gardenZoneId, plantId, eventType);
    }

    //Get most recent tracker event
    @GetMapping("/{plantId}/tracker/{eventType}/latest")
    public TrackerEventDTO getMostRecentTrackerEvent(@PathVariable("gardenId") Long gardenId,
                                                        @PathVariable("gardenZoneId") Long gardenZoneId,
                                                        @PathVariable("plantId") Long plantId,
                                                        @PathVariable("eventType") PlantTrackerEventType eventType){
        return plantService.getMostRecentEvent(gardenId, gardenZoneId, plantId, eventType).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "IMPLEMENT ME"));
    }

}
