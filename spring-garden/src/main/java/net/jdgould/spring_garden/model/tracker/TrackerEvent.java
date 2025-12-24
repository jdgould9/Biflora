package net.jdgould.spring_garden.model.tracker;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
//TrackerEvent is individual tracker assignment events
@Entity
public class TrackerEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trackerEventId;

    @ManyToOne
    private TrackerAssignment trackerAssignment;

    @Column(nullable = false)
    private LocalDateTime actionTime;

    private String details;

    protected TrackerEvent() {}

    public TrackerEvent(LocalDateTime actionTime, String details) {
        this.actionTime = actionTime;
        this.details = details;
    }

    public LocalDateTime getActionTime() { return actionTime; }
    public String getDetails() { return details; }

    public static Optional<TrackerEvent> getMostRecentEvent(List<TrackerEvent> history) {
        if (history == null || history.isEmpty()) return Optional.empty();
        return Optional.of(history.getLast());
    }
}