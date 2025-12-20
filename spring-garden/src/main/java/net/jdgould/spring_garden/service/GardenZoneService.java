package net.jdgould.spring_garden.service;

import net.jdgould.spring_garden.dto.gardenzone.GardenZoneCreationRequestDTO;
import net.jdgould.spring_garden.dto.gardenzone.GardenZoneCreationResponseDTO;
import net.jdgould.spring_garden.dto.gardenzone.GardenZoneGetResponseDTO;
import net.jdgould.spring_garden.dto.plant.PlantSummaryDTO;
import net.jdgould.spring_garden.dto.tracker.GardenZoneTrackerDTO;
import net.jdgould.spring_garden.dto.tracker.GardenZoneTrackerEventCreationRequestDTO;
import net.jdgould.spring_garden.dto.tracker.TrackerEventCreationResponseDTO;
import net.jdgould.spring_garden.dto.tracker.TrackerEventDTO;
import net.jdgould.spring_garden.model.garden.Garden;
import net.jdgould.spring_garden.model.gardenzone.GardenZone;
import net.jdgould.spring_garden.model.gardenzone.GardenZoneTrackerEventType;
import net.jdgould.spring_garden.model.tracker.TrackerEvent;
import net.jdgould.spring_garden.repository.GardenZoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GardenZoneService {
    private final GardenZoneRepository gardenZoneRepository;
    private final GardenService gardenService;

    public GardenZoneService(GardenZoneRepository gardenZoneRepository, GardenService gardenService) {
        this.gardenZoneRepository = gardenZoneRepository;
        this.gardenService = gardenService;
    }

    public List<GardenZoneGetResponseDTO> findAllGardenZonesInGarden(Long gardenId) {
        Garden garden = gardenService.findGardenEntityById(gardenId).get();//.orElseThrow(()
        //-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Garden not found"));
        return gardenZoneRepository.findAllByGarden(garden).stream()
                .map(this::entityToGetResponseDTO).toList();
    }

    public Optional<GardenZoneGetResponseDTO> findGardenZoneById(Long gardenZoneId, Long gardenId) {
        Garden garden = gardenService.findGardenEntityById(gardenId).get();//.orElseThrow(()
        //-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Garden not found"));
        return gardenZoneRepository.findByGardenZoneIdAndGarden(gardenZoneId, garden)
                .map(this::entityToGetResponseDTO);
    }

    public GardenZoneCreationResponseDTO addGardenZoneToGarden(Long gardenId, GardenZoneCreationRequestDTO requestDTO) {
        Garden garden = gardenService.findGardenEntityById(gardenId).get();//.orElseThrow(()
        //-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Garden not found"));

        GardenZone savedGardenZone = gardenZoneRepository.save(new GardenZone(garden, requestDTO.gardenZoneName()));
        return new GardenZoneCreationResponseDTO(savedGardenZone.getGardenZoneId());
    }

    public TrackerEventCreationResponseDTO recordEvent(Long gardenId, Long gardenZoneId, GardenZoneTrackerEventCreationRequestDTO request) {
        Garden garden = gardenService.findGardenEntityById(gardenId).get();//.orElseThrow(()
        //-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Garden not found"));
        GardenZone gardenZone = gardenZoneRepository.findByGardenZoneIdAndGarden(gardenZoneId, garden).get();//.orElseThrow(()
        //->new ResponseStatusException(HttpStatus.NOT_FOUND));

        TrackerEvent event = gardenZone.recordEvent(
                request.gardenZoneTrackerEventType(), request.details()
        );
        gardenZoneRepository.save(gardenZone);
        return new TrackerEventCreationResponseDTO(event.getTime());
    }

    public List<TrackerEventDTO> getEventHistory(Long gardenId, Long gardenZoneId, GardenZoneTrackerEventType eventType) {
        Garden garden = gardenService.findGardenEntityById(gardenId).get();//.orElseThrow(()
        //-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Garden not found"));
        GardenZone gardenZone = gardenZoneRepository.findByGardenZoneIdAndGarden(gardenZoneId, garden).get();//.orElseThrow(()
        //->new ResponseStatusException(HttpStatus.NOT_FOUND));

        return gardenZone.getEventHistory(eventType).stream().map(TrackerEventDTO::new).toList();
    }

    public Optional<TrackerEventDTO> getMostRecentEvent(Long gardenId, Long gardenZoneId, GardenZoneTrackerEventType eventType) {
        Garden garden = gardenService.findGardenEntityById(gardenId).get();//.orElseThrow(()
        //-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Garden not found"));
        GardenZone gardenZone = gardenZoneRepository.findByGardenZoneIdAndGarden(gardenZoneId, garden).get();//.orElseThrow(()
        //->new ResponseStatusException(HttpStatus.NOT_FOUND));
        return gardenZone.getMostRecentEvent(eventType).map(TrackerEventDTO::new);
    }

    //HELPERS
    private GardenZoneGetResponseDTO entityToGetResponseDTO(GardenZone gardenZone) {
        return new GardenZoneGetResponseDTO(
                gardenZone.getGardenZoneId(),
                gardenZone.getGardenZoneName(),
                new GardenZoneTrackerDTO(gardenZone.getTracker()),
                gardenZone.getPlants().stream().map(
                                p -> new PlantSummaryDTO(
                                        p.getPlantId(),
                                        p.getPlantName()
                                ))
                        .toList()
        );
    }

    protected Optional<GardenZone> findGardenZoneEntityById(Long gardenZoneId, Long gardenId) {
        Garden garden = gardenService.findGardenEntityById(gardenId).get();//.orElseThrow(()
        //-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Garden not found"));
        return gardenZoneRepository.findByGardenZoneIdAndGarden(gardenZoneId, garden);
    }
}
