package net.jdgould.spring_garden.repository;

import net.jdgould.spring_garden.model.Plant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlantRepository extends JpaRepository<Plant, Long> {

}
