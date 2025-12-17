package net.jdgould.spring_garden.service;

import net.jdgould.spring_garden.model.Garden;
import net.jdgould.spring_garden.model.GardenZone;
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

    public List<Garden> findAllGardens(){
        return gardenRepository.findAll();
    }

    public Optional<Garden> findGardenById(Long gardenId){
        return gardenRepository.findById(gardenId);
    }

    public Garden addGarden(Garden garden){
        return gardenRepository.save(garden);
    }
}
