package net.jdgould.spring_garden.service;

import net.jdgould.spring_garden.dto.gardenzone.GardenZoneCreationRequestDTO;
import net.jdgould.spring_garden.dto.gardenzone.GardenZoneCreationResponseDTO;
import net.jdgould.spring_garden.dto.gardenzone.GardenZoneGetResponseDTO;
import net.jdgould.spring_garden.dto.plant.PlantSummaryDTO;
import net.jdgould.spring_garden.exception.GardenZoneNotFoundException;
import net.jdgould.spring_garden.model.garden.Garden;
import net.jdgould.spring_garden.model.gardenzone.GardenZone;
import net.jdgould.spring_garden.model.gardenzone.GardenZoneTrackerEventType;
import net.jdgould.spring_garden.repository.GardenZoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GardenZoneService {
    private final GardenZoneRepository gardenZoneRepository;
    private final GardenService gardenService;

    public GardenZoneService(GardenZoneRepository gardenZoneRepository, GardenService gardenService) {
        this.gardenZoneRepository = gardenZoneRepository;
        this.gardenService = gardenService;
    }

    //GARDENZONES
    public List<GardenZoneGetResponseDTO> findAllGardenZonesInGarden(Long gardenId) {
        Garden garden = gardenService.findGardenEntityById(gardenId);
        return gardenZoneRepository.findAllByGarden(garden).stream()
                .map(this::entityToGetResponseDTO).toList();
    }

    public GardenZoneGetResponseDTO findGardenZoneById(Long gardenZoneId, Long gardenId) {
        GardenZone gardenZone = findGardenZoneEntityById(gardenZoneId, gardenId);
        return entityToGetResponseDTO(gardenZone);
    }

    public GardenZoneCreationResponseDTO addGardenZoneToGarden(Long gardenId, GardenZoneCreationRequestDTO requestDTO) {
        Garden garden = gardenService.findGardenEntityById(gardenId);
        GardenZone savedGardenZone = gardenZoneRepository.save(new GardenZone(garden, requestDTO.gardenZoneName()));

        return new GardenZoneCreationResponseDTO(savedGardenZone.getGardenZoneId());
    }

//    //TRACKER EVENTS
//    public TrackerEventCreationResponseDTO recordEvent(Long gardenId, Long gardenZoneId, GardenZoneTrackerEventCreationRequestDTO request) {
//        GardenZone gardenZone = findGardenZoneEntityById(gardenZoneId, gardenId);
//        TrackerEvent event = gardenZone.recordEvent(
//                request.gardenZoneTrackerEventType(), request.details()
//        );
//        gardenZoneRepository.save(gardenZone);
//
//        return new TrackerEventCreationResponseDTO(event.getTime());
//    }
//
//    public List<TrackerEventDTO> getEventHistory(Long gardenId, Long gardenZoneId, GardenZoneTrackerEventType eventType) {
//        GardenZone gardenZone = findGardenZoneEntityById(gardenZoneId, gardenId);
//        return gardenZone.getEventHistory(eventType).stream().map(TrackerEventDTO::new).toList();
//    }
//
//    public Optional<TrackerEventDTO> getMostRecentEvent(Long gardenId, Long gardenZoneId, GardenZoneTrackerEventType eventType) {
//        GardenZone gardenZone = findGardenZoneEntityById(gardenZoneId, gardenId);
//        return gardenZone.getMostRecentEvent(eventType).map(TrackerEventDTO::new);
//    }

    //HELPERS
    private GardenZoneGetResponseDTO entityToGetResponseDTO(GardenZone gardenZone) {
        return new GardenZoneGetResponseDTO(
                gardenZone.getGardenZoneId(),
                gardenZone.getGardenZoneName(),
//                new GardenZoneTrackerDTO(gardenZone.getTracker()),
                gardenZone.getPlants().stream().map(
                                p -> new PlantSummaryDTO(
                                        p.getPlantId(),
                                        p.getPlantName()
                                ))
                        .toList()
        );
    }

    protected GardenZone findGardenZoneEntityById(Long gardenZoneId, Long gardenId) {
        Garden garden = gardenService.findGardenEntityById(gardenId);
        return gardenZoneRepository.findByGardenZoneIdAndGarden(gardenZoneId, garden)
                .orElseThrow(() -> new GardenZoneNotFoundException("Garden zone not found with id: " + gardenZoneId));
    }
}
