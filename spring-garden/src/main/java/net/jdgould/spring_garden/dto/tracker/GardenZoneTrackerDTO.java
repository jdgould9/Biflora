package net.jdgould.spring_garden.dto.tracker;

import net.jdgould.spring_garden.model.gardenzone.GardenZoneTracker;
import net.jdgould.spring_garden.model.gardenzone.GardenZoneTrackerEventType;


import java.time.LocalDateTime;
import java.util.Optional;

public record GardenZoneTrackerDTO(LocalDateTime creationDate,
                                   Optional<TrackerEventDTO> weedingHistory,
                                   Optional<TrackerEventDTO> soilPhHistory,
                                   Optional<TrackerEventDTO> soilMoistureHistory) {

    public GardenZoneTrackerDTO(GardenZoneTracker gardenZoneTracker) {
        this(gardenZoneTracker.getCreationDate(),
                gardenZoneTracker.getMostRecentEvent(GardenZoneTrackerEventType.WEED).map(TrackerEventDTO::new),
                gardenZoneTracker.getMostRecentEvent(GardenZoneTrackerEventType.TEST_SOIL_PH).map(TrackerEventDTO::new),
                gardenZoneTracker.getMostRecentEvent(GardenZoneTrackerEventType.TEST_SOIL_MOISTURE).map(TrackerEventDTO::new)
        );
    }
}
