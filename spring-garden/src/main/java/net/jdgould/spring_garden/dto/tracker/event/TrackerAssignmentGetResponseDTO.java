package net.jdgould.spring_garden.dto.tracker.event;

import java.time.LocalDateTime;

public record TrackerAssignmentGetResponseDTO(Long trackerAssignmentId,
                                              //TrackerEvent mostRecentEvent
                                              Long trackableAssignedToId,
                                              LocalDateTime startDate) {
}
