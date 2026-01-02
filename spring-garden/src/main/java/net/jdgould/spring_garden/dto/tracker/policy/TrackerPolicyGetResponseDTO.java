package net.jdgould.spring_garden.dto.tracker.policy;

import java.time.LocalDateTime;

public record TrackerPolicyGetResponseDTO(Long trackerPolicyId,
                                          String name,
                                          String description,
                                          Integer intervalHours,
                                          LocalDateTime creationTime) {
}
