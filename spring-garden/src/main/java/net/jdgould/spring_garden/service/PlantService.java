package net.jdgould.spring_garden.service;

import net.jdgould.spring_garden.dto.plant.PlantCreationRequestDTO;
import net.jdgould.spring_garden.dto.plant.PlantCreationResponseDTO;
import net.jdgould.spring_garden.dto.plant.PlantGetResponseDTO;
import net.jdgould.spring_garden.dto.tracker.PlantTrackerDTO;
import net.jdgould.spring_garden.model.GardenZone;
import net.jdgould.spring_garden.model.Plant;
import net.jdgould.spring_garden.repository.PlantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlantService {
    private final PlantRepository plantRepository;
    private final GardenZoneService gardenZoneService;

    public PlantService(PlantRepository plantRepository, GardenZoneService gardenZoneService) {
        this.plantRepository = plantRepository;
        this.gardenZoneService = gardenZoneService;
    }

    public List<PlantGetResponseDTO> findAllPlantsInZone(Long gardenId, Long gardenZoneId) {
        GardenZone gardenZone = gardenZoneService.findGardenZoneEntityById(gardenZoneId, gardenId).get();//.orElseThrow(()
        //->new ResponseStatusException(HttpStatus.NOT_FOUND));
        return plantRepository.findAllByGardenZone(gardenZone).stream()
                .map(this::entityToGetResponseDTO)
                .toList();
    }

    public Optional<PlantGetResponseDTO> findPlantInZoneById(Long gardenId, Long gardenZoneId, Long plantId) {
        GardenZone gardenZone = gardenZoneService.findGardenZoneEntityById(gardenZoneId, gardenId).get();//.orElseThrow(()
        //->new ResponseStatusException(HttpStatus.NOT_FOUND));
        return plantRepository.findByPlantIdAndGardenZone(plantId, gardenZone)
                .map(this::entityToGetResponseDTO);
    }

    public PlantCreationResponseDTO addPlantToGardenZone(Long gardenId, Long gardenZoneId, PlantCreationRequestDTO plantCreationRequestDTO) {
        GardenZone gardenZone = gardenZoneService.findGardenZoneEntityById(gardenZoneId, gardenId).get();//.orElseThrow(()
        //->new ResponseStatusException(HttpStatus.NOT_FOUND));
        Plant savedPlant = plantRepository.save(new Plant(gardenZone, plantCreationRequestDTO.plantName()));
        return new PlantCreationResponseDTO(savedPlant.getPlantId());
    }

    /// HELPERS
    private PlantGetResponseDTO entityToGetResponseDTO(Plant plant) {
        return new PlantGetResponseDTO(
                plant.getPlantId(),
                plant.getPlantName(),
                new PlantTrackerDTO(plant.getTracker())
        );
    }
}

