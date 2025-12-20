package net.jdgould.spring_garden.repository;

import net.jdgould.spring_garden.model.garden.Garden;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GardenRepository extends JpaRepository<Garden, Long> { }
