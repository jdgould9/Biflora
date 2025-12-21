package net.jdgould.spring_garden.controller;

import net.jdgould.spring_garden.dto.gardenzone.GardenZoneCreationRequestDTO;
import net.jdgould.spring_garden.dto.gardenzone.GardenZoneCreationResponseDTO;
import net.jdgould.spring_garden.dto.gardenzone.GardenZoneGetResponseDTO;
import net.jdgould.spring_garden.dto.tracker.GardenZoneTrackerEventCreationRequestDTO;
import net.jdgould.spring_garden.dto.tracker.TrackerEventCreationResponseDTO;
import net.jdgould.spring_garden.dto.tracker.TrackerEventDTO;
import net.jdgould.spring_garden.model.gardenzone.GardenZoneTrackerEventType;
import net.jdgould.spring_garden.model.tracker.TrackerEvent;
import net.jdgould.spring_garden.service.GardenZoneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/gardens/{gardenId}/zones")
public class GardenZoneController {
    private final GardenZoneService gardenZoneService;

    public GardenZoneController(GardenZoneService gardenZoneService) {
        this.gardenZoneService = gardenZoneService;
    }

    //Get all garden zones in a garden
    @GetMapping("")
    public List<GardenZoneGetResponseDTO> getAllGardenZonesInGarden(@PathVariable("gardenId") Long gardenId) {
        return gardenZoneService.findAllGardenZonesInGarden(gardenId);
    }

    //Get garden zone by garden Id and garden zone Id
    @GetMapping("/{gardenZoneId}")
    public GardenZoneGetResponseDTO getGardenZoneById(@PathVariable("gardenId") Long gardenId, @PathVariable("gardenZoneId") Long gardenZoneId) {
        return gardenZoneService.findGardenZoneById(gardenZoneId, gardenId);
    }

    //Create garden zone
    @PostMapping("")
    public GardenZoneCreationResponseDTO createGardenZone(@PathVariable("gardenId") Long gardenId, @RequestBody GardenZoneCreationRequestDTO request) {
        return gardenZoneService.addGardenZoneToGarden(gardenId, request);
    }

    //Record tracker event
    @PostMapping("{gardenZoneId}/tracker")
    public TrackerEventCreationResponseDTO createTrackerEvent(@PathVariable("gardenId") Long gardenId,
                                                              @PathVariable("gardenZoneId") Long gardenZoneId,
                                                              @RequestBody GardenZoneTrackerEventCreationRequestDTO request) {

        return gardenZoneService.recordEvent(gardenId, gardenZoneId, request);
    }

    //Get full tracker event history
    @GetMapping("{gardenZoneId}/tracker/{eventType}")
    public List<TrackerEventDTO> getTrackerEventHistory(@PathVariable("gardenId") Long gardenId,
                                                        @PathVariable("gardenZoneId") Long gardenZoneId,
                                                        @PathVariable("eventType") GardenZoneTrackerEventType eventType) {
        return gardenZoneService.getEventHistory(gardenId, gardenZoneId, eventType);
    }

    //Get most recent tracker event
    @GetMapping("{gardenZoneId}/tracker/{eventType}/latest")
    public ResponseEntity<TrackerEventDTO> getMostRecentTrackerEvent(@PathVariable("gardenId") Long gardenId,
                                                                     @PathVariable("gardenZoneId") Long gardenZoneId,
                                                                     @PathVariable("eventType") GardenZoneTrackerEventType eventType) {
        return gardenZoneService.getMostRecentEvent(gardenId, gardenZoneId, eventType)
                .map(ResponseEntity::ok)
                .orElseGet(()->ResponseEntity.noContent().build());
    }
}
