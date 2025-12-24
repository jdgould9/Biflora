package net.jdgould.spring_garden.dto.oldtracker;

import net.jdgould.spring_garden.model.gardenzone.GardenZoneTrackerEventType;

public record GardenZoneTrackerEventCreationRequestDTO (GardenZoneTrackerEventType gardenZoneTrackerEventType, String details){
}
