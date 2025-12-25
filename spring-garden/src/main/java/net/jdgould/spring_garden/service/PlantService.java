package net.jdgould.spring_garden.service;

import net.jdgould.spring_garden.dto.plant.PlantCreationRequestDTO;
import net.jdgould.spring_garden.dto.plant.PlantCreationResponseDTO;
import net.jdgould.spring_garden.dto.plant.PlantGetResponseDTO;

import net.jdgould.spring_garden.dto.plant.PlantUpdateRequestDTO;
import net.jdgould.spring_garden.exception.PlantNotFoundException;
import net.jdgould.spring_garden.model.gardenzone.GardenZone;
import net.jdgould.spring_garden.model.plant.Plant;
import net.jdgould.spring_garden.repository.PlantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlantService {
    private final PlantRepository plantRepository;
    private final GardenZoneService gardenZoneService;

    public PlantService(PlantRepository plantRepository, GardenZoneService gardenZoneService) {
        this.plantRepository = plantRepository;
        this.gardenZoneService = gardenZoneService;
    }

    public PlantCreationResponseDTO addPlantToGardenZone(Long gardenId, Long gardenZoneId, PlantCreationRequestDTO request) {
        GardenZone gardenZone = gardenZoneService.findGardenZoneEntityById(gardenZoneId, gardenId);

        Plant savedPlant = plantRepository.save(new Plant(gardenZone, request.plantName()));
        return new PlantCreationResponseDTO(savedPlant.getId());
    }

    public List<PlantGetResponseDTO> findAllPlantsInZone(Long gardenId, Long gardenZoneId) {
        GardenZone gardenZone = gardenZoneService.findGardenZoneEntityById(gardenZoneId, gardenId);

        return plantRepository.findAllByGardenZone(gardenZone).stream()
                .map(this::plantEntityToResponseDTO)
                .toList();
    }

    public PlantGetResponseDTO findPlantInZoneById(Long gardenId, Long gardenZoneId, Long plantId) {
        Plant plant = findPlantEntityById(gardenId, gardenZoneId, plantId);
        return plantEntityToResponseDTO(plant);
    }

    public void deletePlantById(Long gardenId, Long gardenZoneId, Long plantId){
        Plant plant = findPlantEntityById(gardenId, gardenZoneId, plantId);
        plantRepository.delete(plant);
    }

    public PlantGetResponseDTO updatePlantById(Long gardenId, Long gardenZoneId, Long plantId, PlantUpdateRequestDTO request){
        Plant plant = findPlantEntityById(gardenId, gardenZoneId, plantId);
        plant.setName(request.plantName());
        plantRepository.save(plant);
        return plantEntityToResponseDTO(plant);
    }

    //HELPERS
    private PlantGetResponseDTO plantEntityToResponseDTO(Plant plant) {
        return new PlantGetResponseDTO(
                plant.getId(),
                plant.getName()
        );
    }

    protected Plant findPlantEntityById(Long gardenId, Long gardenZoneId, Long plantId) {
        GardenZone gardenZone = gardenZoneService.findGardenZoneEntityById(gardenZoneId, gardenId);

        return plantRepository.findPlantByIdAndGardenZone(plantId, gardenZone).orElseThrow(() -> new PlantNotFoundException("Plant not found with id: " + plantId));
    }

}




