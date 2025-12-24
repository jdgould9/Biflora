package net.jdgould.spring_garden.service;

import net.jdgould.spring_garden.dto.plant.PlantCreationRequestDTO;
import net.jdgould.spring_garden.dto.plant.PlantCreationResponseDTO;
import net.jdgould.spring_garden.dto.plant.PlantGetResponseDTO;

import net.jdgould.spring_garden.exception.PlantNotFoundException;
import net.jdgould.spring_garden.model.gardenzone.GardenZone;
import net.jdgould.spring_garden.model.plant.Plant;
import net.jdgould.spring_garden.model.plant.PlantTrackerEventType;
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

    //PLANTS
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

    public PlantCreationResponseDTO addPlantToGardenZone(Long gardenId, Long gardenZoneId, PlantCreationRequestDTO request) {
        GardenZone gardenZone = gardenZoneService.findGardenZoneEntityById(gardenZoneId, gardenId);

        Plant savedPlant = plantRepository.save(new Plant(gardenZone, request.plantName()));
        return new PlantCreationResponseDTO(savedPlant.getPlantId());
    }

//    //TRACKER EVENTS
//    public TrackerEventCreationResponseDTO recordEvent(Long gardenId, Long gardenZoneId, Long plantId, PlantTrackerEventCreationRequestDTO request) {
//        Plant plant = findPlantEntityById(gardenId, gardenZoneId, gardenId);
//        TrackerEvent event = plant.recordEvent(request.plantTrackerEventType(), request.details());
//
//        plantRepository.save(plant);
//        return new TrackerEventCreationResponseDTO(event.getTime());
//    }
//
//    public List<TrackerEventDTO> getEventHistory(Long gardenId, Long gardenZoneId, Long plantId, PlantTrackerEventType eventType) {
//        Plant plant = findPlantEntityById(gardenId, gardenZoneId, plantId);
//        return plant.getEventHistory(eventType).stream().map(TrackerEventDTO::new).toList();
//    }
//
//    public Optional<TrackerEventDTO> getMostRecentEvent(Long gardenId, Long gardenZoneId, Long plantId, PlantTrackerEventType eventType) {
//        Plant plant = findPlantEntityById(gardenId, gardenZoneId, plantId);
//
//       return plant.getMostRecentEvent(eventType).map(TrackerEventDTO::new);
//    }

    //HELPERS
    private PlantGetResponseDTO plantEntityToResponseDTO(Plant plant) {
        return new PlantGetResponseDTO(
                plant.getPlantId(),
                plant.getPlantName()
//                new PlantTrackerDTO(plant.getTracker())
        );
    }

    protected Plant findPlantEntityById(Long gardenId, Long gardenZoneId, Long plantId){
        GardenZone gardenZone = gardenZoneService.findGardenZoneEntityById(gardenZoneId, gardenId);

        return plantRepository.findByPlantIdAndGardenZone(plantId, gardenZone).orElseThrow(()->new PlantNotFoundException("Plant not found with id: " + plantId));
    }

}




