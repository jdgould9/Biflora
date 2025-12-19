package net.jdgould.spring_garden.dto.gardenzone;

import net.jdgould.spring_garden.dto.plant.PlantSummaryDTO;
import net.jdgould.spring_garden.dto.tracker.GardenZoneTrackerDTO;
import net.jdgould.spring_garden.model.GardenZoneTracker;
import net.jdgould.spring_garden.model.Plant;

import java.util.List;

public record GardenZoneGetResponseDTO(Long gardenZoneId,
                                       String gardenZoneName,
                                       GardenZoneTrackerDTO gardenZoneTracker,
                                       List<PlantSummaryDTO> plants
) {
}
