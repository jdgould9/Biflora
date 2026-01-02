package net.jdgould.spring_garden.controller;

import net.jdgould.spring_garden.dto.garden.GardenGetResponseDTO;
import net.jdgould.spring_garden.dto.gardenzone.GardenZoneCreationRequestDTO;
import net.jdgould.spring_garden.dto.gardenzone.GardenZoneCreationResponseDTO;
import net.jdgould.spring_garden.dto.gardenzone.GardenZoneGetResponseDTO;
import net.jdgould.spring_garden.dto.gardenzone.GardenZoneUpdateRequestDTO;
import net.jdgould.spring_garden.service.GardenZoneService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/gardens/{gardenId}/zones")
public class GardenZoneController {
    private final GardenZoneService gardenZoneService;

    public GardenZoneController(GardenZoneService gardenZoneService) {
        this.gardenZoneService = gardenZoneService;
    }

    //Create garden zone
    @PostMapping("")
    public ResponseEntity<GardenZoneCreationResponseDTO> createGardenZone(@PathVariable("gardenId") Long gardenId, @RequestBody GardenZoneCreationRequestDTO request) {
        GardenZoneCreationResponseDTO response = gardenZoneService.addGardenZoneToGarden(gardenId, request);
        URI location = URI.create("/api/gardens/" + gardenId + "/zones/" + response.gardenZoneId());
        return ResponseEntity.created(location).body(response);
    }

    //Get all garden zones in a garden
    @GetMapping("")
    public List<GardenZoneGetResponseDTO> getAllGardenZonesInGarden(@PathVariable("gardenId") Long gardenId) {
        return gardenZoneService.findAllGardenZonesInGarden(gardenId);
    }

    //Get garden zone
    @GetMapping("/{gardenZoneId}")
    public GardenZoneGetResponseDTO getGardenZoneById(@PathVariable("gardenId") Long gardenId, @PathVariable("gardenZoneId") Long gardenZoneId) {
        return gardenZoneService.findGardenZoneById(gardenZoneId, gardenId);
    }

    //Delete garden zone
    @DeleteMapping("/{gardenZoneId}")
    public ResponseEntity<Void> deleteGardenZoneById(@PathVariable("gardenId") Long gardenId, @PathVariable("gardenZoneId") Long gardenZoneId){
        gardenZoneService.deleteGardenZoneById(gardenZoneId, gardenId);
        return ResponseEntity.noContent().build();
    }

    //Update garden zone info
    @PatchMapping("/{gardenZoneId}")
    public ResponseEntity<GardenZoneGetResponseDTO> updateGardenById(@PathVariable("gardenId") Long gardenId,
                                                                 @PathVariable("gardenZoneId") Long gardenZoneId,
                                                                 @RequestBody GardenZoneUpdateRequestDTO request){
        GardenZoneGetResponseDTO response = gardenZoneService.updateGardenZoneById(gardenZoneId, gardenId, request);
        return ResponseEntity.ok(response);

    }
}
