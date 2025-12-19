package net.jdgould.spring_garden.service;

import net.jdgould.spring_garden.dto.garden.GardenCreationRequestDTO;
import net.jdgould.spring_garden.dto.garden.GardenCreationResponseDTO;
import net.jdgould.spring_garden.dto.garden.GardenGetResponseDTO;
import net.jdgould.spring_garden.dto.gardenzone.GardenZoneGetResponseDTO;
import net.jdgould.spring_garden.dto.gardenzone.GardenZoneSummaryDTO;
import net.jdgould.spring_garden.model.Garden;
import net.jdgould.spring_garden.repository.GardenRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GardenService {
    private final GardenRepository gardenRepository;

    public GardenService(GardenRepository gardenRepository) {
        this.gardenRepository = gardenRepository;
    }

    public List<GardenGetResponseDTO> findAllGardens() {
        List<Garden> gardens = gardenRepository.findAll();
        return gardens.stream()
                .map(this::entityToGetResponseDTO)
                .toList();
    }

    public Optional<GardenGetResponseDTO> findGardenById(Long gardenId) {
        return gardenRepository.findById(gardenId)
                .map(this::entityToGetResponseDTO);
    }

    public GardenCreationResponseDTO addGarden(GardenCreationRequestDTO gardenCreationRequestDTO) {
        Garden savedGarden = gardenRepository.save(new Garden(gardenCreationRequestDTO.gardenName()));
        return new GardenCreationResponseDTO(savedGarden.getGardenId());
    }

    /// HELPERS
    private GardenGetResponseDTO entityToGetResponseDTO(Garden garden) {
        return new GardenGetResponseDTO(
                garden.getGardenId(),
                garden.getGardenName(),
                garden.getGardenZones().stream()
                        .map(
                                gz -> new GardenZoneSummaryDTO
                                        (
                                                gz.getGardenZoneId(),
                                                gz.getGardenZoneName()
                                        ))
                        .toList()
        );
    }

    protected Optional<Garden> findGardenEntityById(Long gardenId) {
        return gardenRepository.findById(gardenId);
    }

}
