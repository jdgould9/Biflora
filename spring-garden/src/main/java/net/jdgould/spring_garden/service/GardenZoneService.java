package net.jdgould.spring_garden.service;

import net.jdgould.spring_garden.dto.gardenzone.GardenZoneCreationRequestDTO;
import net.jdgould.spring_garden.dto.gardenzone.GardenZoneCreationResponseDTO;
import net.jdgould.spring_garden.dto.gardenzone.GardenZoneGetResponseDTO;
import net.jdgould.spring_garden.dto.plant.PlantSummaryDTO;
import net.jdgould.spring_garden.dto.tracker.GardenZoneTrackerDTO;
import net.jdgould.spring_garden.model.Garden;
import net.jdgould.spring_garden.model.GardenZone;
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

    public GardenZoneCreationResponseDTO addGardenZoneToGarden(Long gardenId, GardenZoneCreationRequestDTO gardenZoneCreationRequestDTO) {
        Garden garden = gardenService.findGardenEntityById(gardenId).get();//.orElseThrow(()
        //-> new ResponseStatusException(HttpStatus.NOT_FOUND, "Garden not found"));
        GardenZone savedGardenZone = gardenZoneRepository.save(new GardenZone(garden, gardenZoneCreationRequestDTO.gardenZoneName()));
        return new GardenZoneCreationResponseDTO(savedGardenZone.getGardenZoneId());
    }

    /// HELPERS
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
