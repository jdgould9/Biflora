package net.jdgould.spring_garden.dto.oldtracker;

import net.jdgould.spring_garden.model.plant.PlantTrackerEventType;

public record PlantTrackerEventCreationRequestDTO(PlantTrackerEventType plantTrackerEventType, String details){
}
