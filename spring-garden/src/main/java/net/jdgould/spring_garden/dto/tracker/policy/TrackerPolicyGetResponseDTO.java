package net.jdgould.spring_garden.dto.tracker.policy;

import net.jdgould.spring_garden.dto.tracker.event.TrackerAssignmentGetResponseDTO;
import net.jdgould.spring_garden.model.tracker.TrackableType;

import java.time.LocalDateTime;
import java.util.List;

public record TrackerPolicyGetResponseDTO(Long trackerPolicyId,
                                          TrackableType targetType,
                                          String name,
                                          String description,
                                          Integer intervalHours,
                                          LocalDateTime creationTime,
                                          List<TrackerAssignmentGetResponseDTO> assignments) {
}
