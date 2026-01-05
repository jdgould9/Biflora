package net.jdgould.spring_garden.dto.tracker.policy;

import net.jdgould.spring_garden.model.tracker.TrackableType;

import java.time.LocalDateTime;

public record TrackerPolicyGetResponseDTO(Long trackerPolicyId,
                                          TrackableType targetType,
                                          String name,
                                          String description,
                                          Integer intervalHours,
                                          LocalDateTime creationTime) {
}
