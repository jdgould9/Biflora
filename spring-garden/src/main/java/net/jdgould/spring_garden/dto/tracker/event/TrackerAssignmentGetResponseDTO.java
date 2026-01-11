package net.jdgould.spring_garden.dto.tracker.event;

import net.jdgould.spring_garden.dto.tracker.assignment.TrackerEventGetResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public record TrackerAssignmentGetResponseDTO(Long trackerAssignmentId,
                                              Long trackableAssignedToId,
                                              LocalDateTime startDate,
                                              List<TrackerEventGetResponseDTO> events) {
}
