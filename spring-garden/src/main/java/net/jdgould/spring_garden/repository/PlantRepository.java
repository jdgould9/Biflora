package net.jdgould.spring_garden.repository;

import net.jdgould.spring_garden.model.gardenzone.GardenZone;
import net.jdgould.spring_garden.model.plant.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlantRepository extends JpaRepository<Plant, Long> {
    List<Plant> findAllByGardenZone(GardenZone gardenZone);

    Optional<Plant> findByPlantIdAndGardenZone(Long plantId, GardenZone gardenZone);
}
