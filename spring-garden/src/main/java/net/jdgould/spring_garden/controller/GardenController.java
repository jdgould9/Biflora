package net.jdgould.spring_garden.controller;

import net.jdgould.spring_garden.dto.garden.GardenCreationRequestDTO;
import net.jdgould.spring_garden.dto.garden.GardenCreationResponseDTO;
import net.jdgould.spring_garden.dto.garden.GardenGetResponseDTO;
import net.jdgould.spring_garden.dto.garden.GardenUpdateRequestDTO;
import net.jdgould.spring_garden.service.GardenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
@CrossOrigin
@RestController
@RequestMapping("/api/gardens")
public class GardenController {
    private final GardenService gardenService;

    public GardenController(GardenService gardenService) {
        this.gardenService = gardenService;
    }


    //Create garden
    @PostMapping("")
    public ResponseEntity<GardenCreationResponseDTO> createGarden(@RequestBody GardenCreationRequestDTO request) {
        GardenCreationResponseDTO response = gardenService.addGarden(request);
        URI location = URI.create("/api/gardens/" + response.gardenId());
        return ResponseEntity.created(location).body(response);
    }

    //Get all gardens
    @GetMapping("")
    public List<GardenGetResponseDTO> getAllGardens() {
        return gardenService.findAllGardens();
    }

    //Get garden
    @GetMapping("/{gardenId}")
    public GardenGetResponseDTO getGardenById(@PathVariable("gardenId") Long gardenId) {
        return gardenService.findGardenById(gardenId);
    }

    //Delete garden
    @DeleteMapping("/{gardenId}")
    public ResponseEntity<Void> deleteGardenById(@PathVariable("gardenId") Long gardenId){
        gardenService.deleteGardenById(gardenId);
        return ResponseEntity.noContent().build();
    }

    //Update garden info
    @PatchMapping("/{gardenId}")
    public ResponseEntity<GardenGetResponseDTO> updateGardenById(@PathVariable("gardenId") Long gardenId, @RequestBody GardenUpdateRequestDTO request){
        GardenGetResponseDTO response = gardenService.updateGardenById(gardenId, request);
        return ResponseEntity.ok(response);
    }
}
