package net.jdgould.spring_garden.service;

import net.jdgould.spring_garden.dto.garden.GardenCreationRequestDTO;
import net.jdgould.spring_garden.dto.garden.GardenCreationResponseDTO;
import net.jdgould.spring_garden.dto.garden.GardenGetResponseDTO;
import net.jdgould.spring_garden.dto.garden.GardenUpdateRequestDTO;
import net.jdgould.spring_garden.dto.gardenzone.GardenZoneSummaryDTO;
import net.jdgould.spring_garden.exception.GardenNotFoundException;
import net.jdgould.spring_garden.model.garden.Garden;
import net.jdgould.spring_garden.repository.GardenRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class GardenService {
    private final GardenRepository gardenRepository;

    public GardenService(GardenRepository gardenRepository) {
        this.gardenRepository = gardenRepository;
    }

    public GardenCreationResponseDTO addGarden(GardenCreationRequestDTO request) {
        Garden savedGarden = gardenRepository.save(new Garden(request.gardenName()));
        return new GardenCreationResponseDTO(savedGarden.getId());
    }

    public List<GardenGetResponseDTO> findAllGardens() {
        return gardenRepository.findAll().stream()
                .map(this::entityToGetResponseDTO)
                .toList();
    }

    public GardenGetResponseDTO findGardenById(Long gardenId) {
        Garden garden = findGardenEntityById(gardenId);
        return entityToGetResponseDTO(garden);
    }

    public void deleteGardenById(Long gardenId){
        Garden garden = findGardenEntityById(gardenId);
        gardenRepository.delete(garden);
    }

    public GardenGetResponseDTO updateGardenById(Long gardenId, GardenUpdateRequestDTO request){
        Garden garden = findGardenEntityById(gardenId);
        garden.setName(request.gardenName());
        gardenRepository.save(garden);
        return entityToGetResponseDTO(garden);
    }

    //HELPERS
    private GardenGetResponseDTO entityToGetResponseDTO(Garden garden) {
        return new GardenGetResponseDTO(
                garden.getId(),
                garden.getName()
//                garden.getGardenZones().stream()
//                        .map(
//                                gz -> new GardenZoneSummaryDTO(
//                                        gz.getId(),
//                                        gz.getName()
//                                )
//                        )
//                        .toList()
        );
    }

    protected Garden findGardenEntityById(Long gardenId) {
        return gardenRepository.findById(gardenId)
                .orElseThrow(() -> new GardenNotFoundException("Garden not found with id: " + gardenId));
    }
}
