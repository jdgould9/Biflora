package net.jdgould.spring_garden.controller;

import net.jdgould.spring_garden.dto.plant.PlantCreationRequestDTO;
import net.jdgould.spring_garden.dto.plant.PlantCreationResponseDTO;
import net.jdgould.spring_garden.dto.plant.PlantGetResponseDTO;
import net.jdgould.spring_garden.dto.plant.PlantUpdateRequestDTO;
import net.jdgould.spring_garden.service.PlantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/gardens/{gardenId}/zones/{gardenZoneId}/plants")
public class PlantController {
    private PlantService plantService;

    public PlantController(PlantService plantService) {
        this.plantService = plantService;
    }

    //Create plant
    @PostMapping("")
    public ResponseEntity<PlantCreationResponseDTO> createPlant(@PathVariable("gardenId") Long gardenId,
                                                                @PathVariable("gardenZoneId") Long gardenZoneId,
                                                                @RequestBody PlantCreationRequestDTO request) {
        PlantCreationResponseDTO response = plantService.addPlantToGardenZone(gardenId, gardenZoneId, request);
        URI location = URI.create("/api/gardens/" + gardenId + "/zones/" + gardenZoneId + "/plants/" + response.plantId());
        return ResponseEntity.created(location).body(response);
    }

    //Get all plants in a garden zone
    @GetMapping("")
    public List<PlantGetResponseDTO> getAllPlantsInGardenZone(@PathVariable("gardenId") Long gardenId, @PathVariable("gardenZoneId") Long gardenZoneId) {
        return plantService.findAllPlantsInZone(gardenId, gardenZoneId);
    }

    //Get plant
    @GetMapping("/{plantId}")
    public PlantGetResponseDTO getPlantById(@PathVariable("gardenId") Long gardenId,
                                            @PathVariable("gardenZoneId") Long gardenZoneId,
                                            @PathVariable("plantId") Long plantId) {
        return plantService.findPlantInZoneById(gardenId, gardenZoneId, plantId);
    }

    //Delete plant
    @DeleteMapping("/{plantId}")
    public ResponseEntity<Void> deletePlantById(@PathVariable("gardenId") Long gardenId,
                                                @PathVariable("gardenZoneId") Long gardenZoneId,
                                                @PathVariable("plantId") Long plantId){
        plantService.deletePlantById(gardenId, gardenZoneId, plantId);
        return ResponseEntity.noContent().build();
    }

    //Update plant info
    @PatchMapping("/{plantId}")
    public ResponseEntity<PlantGetResponseDTO> updatePlantById(@PathVariable("gardenId") Long gardenId,
                                                               @PathVariable("gardenZoneId") Long gardenZoneId,
                                                               @PathVariable("plantId") Long plantId,
                                                               @RequestBody PlantUpdateRequestDTO request){
        PlantGetResponseDTO response = plantService.updatePlantById(gardenId,gardenZoneId,plantId,request);
        return ResponseEntity.ok(response);
    }



//
//    //Record tracker event
//    @PostMapping("/{plantId}/tracker")
//    public ResponseEntity<TrackerEventCreationResponseDTO> createTrackerEvent(@PathVariable("gardenId") Long gardenId,
//                                                              @PathVariable("gardenZoneId") Long gardenZoneId,
//                                                              @PathVariable("plantId") Long plantId,
//                                                              @RequestBody PlantTrackerEventCreationRequestDTO request) {
//        TrackerEventCreationResponseDTO response = plantService.recordEvent(gardenId, gardenZoneId, plantId, request);
//        URI location = URI.create("/api/gardens/" + gardenId + "/zones/" + gardenZoneId + "/plants/" + plantId + "/tracker/" + request.plantTrackerEventType() + "/latest");
//        return ResponseEntity.created(location).body(response);
//    }
//
//    //Get full tracker event history
//    @GetMapping("/{plantId}/tracker/{eventType}")
//    public List<TrackerEventDTO> getTrackerEventHistory(@PathVariable("gardenId") Long gardenId,
//                                                        @PathVariable("gardenZoneId") Long gardenZoneId,
//                                                        @PathVariable("plantId") Long plantId,
//                                                        @PathVariable("eventType") PlantTrackerEventType eventType) {
//        return plantService.getEventHistory(gardenId, gardenZoneId, plantId, eventType);
//    }
//
//    //Get most recent tracker event
//    @GetMapping("/{plantId}/tracker/{eventType}/latest")
//    public ResponseEntity<TrackerEventDTO> getMostRecentTrackerEvent(@PathVariable("gardenId") Long gardenId,
//                                                                     @PathVariable("gardenZoneId") Long gardenZoneId,
//                                                                     @PathVariable("plantId") Long plantId,
//                                                                     @PathVariable("eventType") PlantTrackerEventType eventType) {
//        return plantService.getMostRecentEvent(gardenId, gardenZoneId, plantId, eventType)
//                .map(ResponseEntity::ok)
//                .orElseGet(()->ResponseEntity.noContent().build());
//    }

}
