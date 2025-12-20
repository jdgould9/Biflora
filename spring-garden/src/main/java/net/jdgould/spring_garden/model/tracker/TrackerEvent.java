package net.jdgould.spring_garden.model.tracker;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Embeddable
public class TrackerEvent {

    @Column(nullable = false)
    private LocalDateTime time;

    @Column(nullable = false)
    private String details;

    protected TrackerEvent() {}

    public TrackerEvent(LocalDateTime time, String details) {
        this.time = time;
        this.details = details;
    }

    public LocalDateTime getTime() { return time; }
    public String getDetails() { return details; }

    public static Optional<TrackerEvent> getMostRecentEvent(List<TrackerEvent> history) {
        if (history == null || history.isEmpty()) return Optional.empty();
        return Optional.of(history.getLast());
    }
}