package net.jdgould.spring_garden.controller;

import net.jdgould.spring_garden.dto.garden.GardenCreationRequestDTO;
import net.jdgould.spring_garden.dto.garden.GardenCreationResponseDTO;
import net.jdgould.spring_garden.dto.garden.GardenGetResponseDTO;
import net.jdgould.spring_garden.service.GardenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/gardens")
public class GardenController {
    private final GardenService gardenService;

    public GardenController(GardenService gardenService) {
        this.gardenService = gardenService;
    }

    //Get all gardens
    @GetMapping("")
    public List<GardenGetResponseDTO> getAllGardens() {
        return gardenService.findAllGardens();
    }

    //Get garden by Id
    @GetMapping("/{gardenId}")
    public GardenGetResponseDTO getGardenById(@PathVariable("gardenId") Long gardenId) {
        return gardenService.findGardenById(gardenId);
    }

    //Create garden
    @PostMapping("")
    public ResponseEntity<GardenCreationResponseDTO> createGarden(@RequestBody GardenCreationRequestDTO request) {
        GardenCreationResponseDTO response = gardenService.addGarden(request);
        URI location = URI.create("/api/gardens/" + response.gardenId());
        return ResponseEntity.created(location).body(response);
    }
}
