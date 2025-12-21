package net.jdgould.spring_garden.controller;

import net.jdgould.spring_garden.dto.garden.GardenCreationRequestDTO;
import net.jdgould.spring_garden.dto.garden.GardenCreationResponseDTO;
import net.jdgould.spring_garden.dto.garden.GardenGetResponseDTO;
import net.jdgould.spring_garden.service.GardenService;
import net.jdgould.spring_garden.service.GardenZoneService;
import net.jdgould.spring_garden.service.PlantService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
    public GardenCreationResponseDTO createGarden(@RequestBody GardenCreationRequestDTO request) {
        return gardenService.addGarden(request);
    }
}
