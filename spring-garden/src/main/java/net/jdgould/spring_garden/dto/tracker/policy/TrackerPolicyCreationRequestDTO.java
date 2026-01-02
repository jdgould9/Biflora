package net.jdgould.spring_garden.dto.tracker.policy;

import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record TrackerPolicyCreationRequestDTO(
        @Size(min = 1, message = "Name must contain at least 1 character.")
        String name,
        @Size(min = 1, message = "Description must contain at least 1 character.")
        String description,
        @Positive
        Integer intervalHours) {
}
