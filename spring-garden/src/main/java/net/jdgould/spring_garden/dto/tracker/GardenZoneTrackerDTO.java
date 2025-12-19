package net.jdgould.spring_garden.dto.tracker;

import net.jdgould.spring_garden.model.GardenZoneTracker;
import net.jdgould.spring_garden.model.TrackerEvent;

import java.time.LocalDateTime;
import java.util.List;

public record GardenZoneTrackerDTO(LocalDateTime creationDate,
                                   List<TrackerEventDTO> weedingHistory,
                                   List<TrackerEventDTO> soilPhHistory,
                                   List<TrackerEventDTO> soilMoistureHistory) {

    public GardenZoneTrackerDTO(GardenZoneTracker gardenZoneTracker) {
        this(gardenZoneTracker.getCreationDate(),
                gardenZoneTracker.getWeedingHistory().stream().map(TrackerEventDTO::new).toList(),
                gardenZoneTracker.getSoilPhHistory().stream().map(TrackerEventDTO::new).toList(),
                gardenZoneTracker.getSoilMoistureHistory().stream().map(TrackerEventDTO::new).toList()
        );
    }
}
