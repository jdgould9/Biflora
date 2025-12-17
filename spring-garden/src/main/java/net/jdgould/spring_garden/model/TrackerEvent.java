package net.jdgould.spring_garden.model;

import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Embeddable
//public record TrackerEvent<T>(LocalDateTime time, T details) {
public record TrackerEvent(LocalDateTime time, String details){
    public static Optional<TrackerEvent> getMostRecentEvent(List<TrackerEvent> history){
        return history.isEmpty() ? Optional.empty() : Optional.of(history.getLast());
    }
}
