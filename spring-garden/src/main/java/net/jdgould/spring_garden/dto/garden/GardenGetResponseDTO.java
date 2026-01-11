package net.jdgould.spring_garden.dto.garden;

import net.jdgould.spring_garden.dto.gardenzone.GardenZoneGetResponseDTO;
import net.jdgould.spring_garden.dto.gardenzone.GardenZoneSummaryDTO;

import java.util.List;

public record GardenGetResponseDTO(Long gardenId,
                                   String gardenName,
                                   List<GardenZoneGetResponseDTO> gardenZones) {
}
