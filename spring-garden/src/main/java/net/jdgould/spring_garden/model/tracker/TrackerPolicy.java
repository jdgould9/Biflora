//TrackerPolicy.java
package net.jdgould.spring_garden.model.tracker;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.sound.midi.Track;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tracker")
public class TrackerPolicy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tracker_id")
    private Long trackerPolicyId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrackableType targetType;

    @OneToMany(mappedBy = "trackerPolicy", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TrackerAssignment> trackerAssignments = new ArrayList<>();

    @Column(name = "tracker_name", nullable = false)
    private String name; //Watering, weeding, etc.

    @Column(name = "tracker_description")
    private String description; //Water the plants in the rear, weed the front yard, etc.

    @Column(name = "interval_hours", nullable = false)
    private Integer intervalHours; //How often this action should be performed

    @Column(name = "creation_time", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime creationTime;

    protected TrackerPolicy() {
    }

    public TrackerPolicy(String name, TrackableType targetType, String description, Integer intervalHours) {
        this.name = name;
        this.targetType=targetType;
        this.description = description;
        this.intervalHours = intervalHours;
    }

    public Long getTrackerPolicyId() {
        return trackerPolicyId;
    }

    public List<TrackerAssignment> getTrackerAssignments() {
        return trackerAssignments;
    }

    public void addTrackerAssignment(TrackerAssignment trackerAssignment){
        this.trackerAssignments.add(trackerAssignment);
        trackerAssignment.setTrackerPolicy(this);
    }

    public void removeTrackerAssignment(TrackerAssignment trackerAssignment){
        this.trackerAssignments.remove(trackerAssignment);
        trackerAssignment.setTrackerPolicy(null);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getIntervalHours() {
        return intervalHours;
    }

    public void setIntervalHours(Integer intervalHours) {
        this.intervalHours = intervalHours;
    }

    public LocalDateTime getCreationTime() {
        return creationTime;
    }

    public TrackableType getTargetType() {
        return targetType;
    }
}
