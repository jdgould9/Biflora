package net.jdgould.spring_garden.dto.tracker;

import net.jdgould.spring_garden.model.TrackerEvent;

import java.time.LocalDateTime;

public record TrackerEventDTO(LocalDateTime time,
                              String details) {

    public TrackerEventDTO(TrackerEvent event) {
        this(event.getTime(), event.getDetails());
    }
}
