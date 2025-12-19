package net.jdgould.spring_garden.dto.plant;

import net.jdgould.spring_garden.dto.tracker.PlantTrackerDTO;

public record PlantGetResponseDTO(Long plantId,
                                  String plantName,
                                  PlantTrackerDTO plantTracker) {
}
