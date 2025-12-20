package net.jdgould.spring_garden.dto.tracker;

import net.jdgould.spring_garden.model.plant.PlantTracker;
import net.jdgould.spring_garden.model.plant.PlantTrackerEventType;

import java.time.LocalDateTime;
import java.util.Optional;

public record PlantTrackerDTO(LocalDateTime creationDate,
                              Optional<TrackerEventDTO> wateringHistory,
                              Optional<TrackerEventDTO> fertilizationHistory,
                              Optional<TrackerEventDTO> pruningHistory,
                              Optional<TrackerEventDTO> pestTreatmentHistory) {

    public PlantTrackerDTO(PlantTracker plantTracker) {
        this(plantTracker.getCreationDate(),
                plantTracker.getMostRecentEvent(PlantTrackerEventType.WATER).map(TrackerEventDTO::new),
                plantTracker.getMostRecentEvent(PlantTrackerEventType.FERTILIZE).map(TrackerEventDTO::new),
                plantTracker.getMostRecentEvent(PlantTrackerEventType.PRUNE).map(TrackerEventDTO::new),
                plantTracker.getMostRecentEvent(PlantTrackerEventType.PEST_TREATMENT).map(TrackerEventDTO::new)
        );
    }
}
