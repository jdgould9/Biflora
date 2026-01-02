package net.jdgould.spring_garden.dto.tracker.policy;


public record TrackerPolicyUpdateRequestDTO(
        String name,
        String description,
        Integer intervalHours) {
}


