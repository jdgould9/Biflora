package net.jdgould.spring_garden.dto.tracker.policy;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import net.jdgould.spring_garden.model.tracker.TrackableType;

public record TrackerPolicyCreationRequestDTO(
        @Size(min = 1, message = "Name must contain at least 1 character.")
        String trackerName,
        @Size(min = 1, message = "Description must contain at least 1 character.")
        String trackerDescription,

        TrackableType targetType,
        @Positive
        Integer intervalHours) {
}
