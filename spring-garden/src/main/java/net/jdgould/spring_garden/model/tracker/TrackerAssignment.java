//TrackerAssignment.java
package net.jdgould.spring_garden.model.tracker;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tracker_assignment")
public class TrackerAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tracker_assignment_id")
    private Long trackerAssignmentId;

    @OneToMany(mappedBy = "trackerAssignment", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrackerEvent> trackerEvents = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "tracker_id", nullable = false)
    private TrackerPolicy trackerPolicy;

    @ManyToOne
    @JoinColumn(name = "trackable_id", nullable = false)
    private Trackable assignedTo;

    @Column(name = "assignment_start_time", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime startDate;

    protected TrackerAssignment() {
    }

    public TrackerAssignment(TrackerPolicy trackerPolicy, Trackable assignedTo) {
        this.trackerPolicy = trackerPolicy;
        this.assignedTo = assignedTo;
    }

    public void setTrackerPolicy(TrackerPolicy trackerPolicy){
        this.trackerPolicy=trackerPolicy;
    }

    public Long getTrackerAssignmentId() {
        return trackerAssignmentId;
    }

    public Long getAssignedToId() {
        return assignedTo.getId();
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }
}
