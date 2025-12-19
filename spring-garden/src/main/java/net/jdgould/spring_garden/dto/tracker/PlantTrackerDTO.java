package net.jdgould.spring_garden.dto.tracker;

import net.jdgould.spring_garden.model.PlantTracker;
import net.jdgould.spring_garden.model.TrackerEvent;

import java.time.LocalDateTime;
import java.util.List;

public record PlantTrackerDTO(LocalDateTime creationDate,
                              List<TrackerEventDTO> wateringHistory,
                              List<TrackerEventDTO> fertilizationHistory,
                              List<TrackerEventDTO> pruningHistory,
                              List<TrackerEventDTO> pestTreatmentHistory) {

    public PlantTrackerDTO(PlantTracker plantTracker) {
        this(plantTracker.getCreationDate(),
                plantTracker.getWateringHistory().stream().map(TrackerEventDTO::new).toList(),
                plantTracker.getFertilizationHistory().stream().map(TrackerEventDTO::new).toList(),
                plantTracker.getPruningHistory().stream().map(TrackerEventDTO::new).toList(),
                plantTracker.getPestTreatmentHistory().stream().map(TrackerEventDTO::new).toList()
        );
    }
}
