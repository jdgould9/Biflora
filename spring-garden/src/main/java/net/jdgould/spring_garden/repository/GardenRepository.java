package net.jdgould.spring_garden.repository;

import net.jdgould.spring_garden.model.Garden;
import net.jdgould.spring_garden.model.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GardenRepository extends JpaRepository<Garden, Long> { }
