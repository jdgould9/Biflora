package net.jdgould.spring_garden.service;

import net.jdgould.spring_garden.dto.plant.PlantCreationRequestDTO;
import net.jdgould.spring_garden.dto.plant.PlantCreationResponseDTO;
import net.jdgould.spring_garden.dto.plant.PlantGetResponseDTO;
import net.jdgould.spring_garden.dto.tracker.PlantTrackerDTO;
import net.jdgould.spring_garden.dto.tracker.PlantTrackerEventCreationRequestDTO;

import net.jdgould.spring_garden.dto.tracker.TrackerEventCreationResponseDTO;
import net.jdgould.spring_garden.dto.tracker.TrackerEventDTO;
import net.jdgould.spring_garden.model.gardenzone.GardenZone;
import net.jdgould.spring_garden.model.plant.Plant;
import net.jdgould.spring_garden.model.plant.PlantTrackerEventType;
import net.jdgould.spring_garden.model.tracker.TrackerEvent;
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
                .map(this::plantEntityToResponseDTO)
                .toList();
    }

    public Optional<PlantGetResponseDTO> findPlantInZoneById(Long gardenId, Long gardenZoneId, Long plantId) {
        GardenZone gardenZone = gardenZoneService.findGardenZoneEntityById(gardenZoneId, gardenId).get();//.orElseThrow(()
        //->new ResponseStatusException(HttpStatus.NOT_FOUND));

        return plantRepository.findByPlantIdAndGardenZone(plantId, gardenZone)
                .map(this::plantEntityToResponseDTO);
    }

    public PlantCreationResponseDTO addPlantToGardenZone(Long gardenId, Long gardenZoneId, PlantCreationRequestDTO request) {
        GardenZone gardenZone = gardenZoneService.findGardenZoneEntityById(gardenZoneId, gardenId).get();//.orElseThrow(()
        //->new ResponseStatusException(HttpStatus.NOT_FOUND));

        Plant savedPlant = plantRepository.save(new Plant(gardenZone, request.plantName()));
        return new PlantCreationResponseDTO(savedPlant.getPlantId());
    }

    public TrackerEventCreationResponseDTO recordEvent(Long gardenId, Long gardenZoneId, Long plantId, PlantTrackerEventCreationRequestDTO request) {
        GardenZone gardenZone = gardenZoneService.findGardenZoneEntityById(gardenZoneId, gardenId).get();//.orElseThrow(()
        //->new ResponseStatusException(HttpStatus.NOT_FOUND));
        Plant plant = plantRepository.findByPlantIdAndGardenZone(plantId, gardenZone).get();//.orElseThrow(()
        //->new ResponseStatusException(HttpStatus.NOT_FOUND));

        TrackerEvent event = plant.recordEvent(request.plantTrackerEventType(), request.details());
        plantRepository.save(plant);
        return new TrackerEventCreationResponseDTO(event.getTime());
    }

    public List<TrackerEventDTO> getEventHistory(Long gardenId, Long gardenZoneId, Long plantId, PlantTrackerEventType eventType) {
        GardenZone gardenZone = gardenZoneService.findGardenZoneEntityById(gardenZoneId, gardenId).get();//.orElseThrow(()
        //->new ResponseStatusException(HttpStatus.NOT_FOUND));
        Plant plant = plantRepository.findByPlantIdAndGardenZone(plantId, gardenZone).get();//.orElseThrow(()
        //->new ResponseStatusException(HttpStatus.NOT_FOUND));

        return plant.getEventHistory(eventType).stream().map(TrackerEventDTO::new).toList();
    }

    public Optional<TrackerEventDTO> getMostRecentEvent(Long gardenId, Long gardenZoneId, Long plantId, PlantTrackerEventType eventType) {
        GardenZone gardenZone = gardenZoneService.findGardenZoneEntityById(gardenZoneId, gardenId).get();//.orElseThrow(()
        //->new ResponseStatusException(HttpStatus.NOT_FOUND));
        Plant plant = plantRepository.findByPlantIdAndGardenZone(plantId, gardenZone).get();//.orElseThrow(()
        //->new ResponseStatusException(HttpStatus.NOT_FOUND));

       return plant.getMostRecentEvent(eventType).map(TrackerEventDTO::new);
    }

    //HELPERS
    private PlantGetResponseDTO plantEntityToResponseDTO(Plant plant) {
        return new PlantGetResponseDTO(
                plant.getPlantId(),
                plant.getPlantName(),
                new PlantTrackerDTO(plant.getTracker())
        );
    }
}




