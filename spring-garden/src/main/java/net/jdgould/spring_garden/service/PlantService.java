package net.jdgould.spring_garden.service;

import net.jdgould.spring_garden.model.Plant;
import net.jdgould.spring_garden.repository.PlantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlantService {
    private final PlantRepository plantRepository;

    public PlantService(PlantRepository plantRepository) {
        this.plantRepository = plantRepository;
    }



    //SERVICES

//    public Optional<Plant> getPlantById(Long id) {
//        return plantRepository.findById(id);
//    }
//
//    public List<Plant> getAllPlants() {
//        return plantRepository.findAll();
//    }
//
//    public Plant addPlant(Plant plant) {
//        return plantRepository.save(plant);
//    }
}

