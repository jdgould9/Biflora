package net.jdgould.spring_garden.service;

import net.jdgould.spring_garden.dto.gardenzone.GardenZoneCreationRequestDTO;
import net.jdgould.spring_garden.dto.gardenzone.GardenZoneCreationResponseDTO;
import net.jdgould.spring_garden.dto.gardenzone.GardenZoneGetResponseDTO;
import net.jdgould.spring_garden.dto.gardenzone.GardenZoneUpdateRequestDTO;
import net.jdgould.spring_garden.dto.plant.PlantSummaryDTO;
import net.jdgould.spring_garden.exception.GardenZoneNotFoundException;
import net.jdgould.spring_garden.model.garden.Garden;
import net.jdgould.spring_garden.model.gardenzone.GardenZone;
import net.jdgould.spring_garden.repository.GardenZoneRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GardenZoneService {
    private final GardenZoneRepository gardenZoneRepository;
    private final GardenService gardenService;

    public GardenZoneService(GardenZoneRepository gardenZoneRepository, GardenService gardenService) {
        this.gardenZoneRepository = gardenZoneRepository;
        this.gardenService=gardenService;
    }

    public GardenZoneCreationResponseDTO addGardenZoneToGarden(Long gardenId, GardenZoneCreationRequestDTO requestDTO) {
        Garden garden = gardenService.findGardenEntityById(gardenId);
        GardenZone savedGardenZone = gardenZoneRepository.save(new GardenZone(garden, requestDTO.gardenZoneName()));
        return new GardenZoneCreationResponseDTO(savedGardenZone.getId());
    }

    public List<GardenZoneGetResponseDTO> findAllGardenZonesInGarden(Long gardenId) {
        Garden garden = gardenService.findGardenEntityById(gardenId);
        return gardenZoneRepository.findAllByGarden(garden).stream()
                .map(this::entityToGetResponseDTO).toList();
    }

    public GardenZoneGetResponseDTO findGardenZoneById(Long gardenZoneId, Long gardenId) {
        GardenZone gardenZone = findGardenZoneEntityById(gardenZoneId, gardenId);
        return entityToGetResponseDTO(gardenZone);
    }

    public void deleteGardenZoneById(Long gardenZoneId, Long gardenId){
        GardenZone gardenZone = findGardenZoneEntityById(gardenZoneId, gardenId);
        gardenZoneRepository.delete(gardenZone);
    }

    public GardenZoneGetResponseDTO updateGardenZoneById(Long gardenZoneId, Long gardenId, GardenZoneUpdateRequestDTO request){
        GardenZone gardenZone = findGardenZoneEntityById(gardenZoneId, gardenId);
        gardenZone.setName(request.gardenZoneName());
        gardenZoneRepository.save(gardenZone);
        return entityToGetResponseDTO(gardenZone);
    }

    //HELPERS
    private GardenZoneGetResponseDTO entityToGetResponseDTO(GardenZone gardenZone) {
        return new GardenZoneGetResponseDTO(
                gardenZone.getId(),
                gardenZone.getName(),
                gardenZone.getPlants().stream().map(
                                p -> new PlantSummaryDTO(
                                        p.getId(),
                                        p.getName()
                                ))
                        .toList()
        );
    }

    protected GardenZone findGardenZoneEntityById(Long gardenZoneId, Long gardenId) {
        Garden garden = gardenService.findGardenEntityById(gardenId);
        return gardenZoneRepository.findGardenZoneByIdAndGarden(gardenZoneId, garden)
                .orElseThrow(() -> new GardenZoneNotFoundException("Garden zone not found with id: " + gardenZoneId));
    }
}
