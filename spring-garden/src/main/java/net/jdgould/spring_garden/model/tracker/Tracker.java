package net.jdgould.spring_garden.model.tracker;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Entity
//Tracker is a tracker policy
public class Tracker{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trackerId;
    private String name; //Watering, weeding, etc.
    private String description; //Water the plants in the rear, weed the front yard, etc.
    private Long intervalHours; //How often this action should be performed

}

//@Entity
//public class Tracker {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long trackerId;
//
//    @Column(nullable = false)
//    private String trackerType;
//
//    @ElementCollection
//    @OrderColumn
//    private List<TrackerEvent> events = new ArrayList<>();
//
//    protected Tracker() {}
//
//    public Tracker(String trackerType) {
//        this.trackerType = trackerType;
//    }
//
//    public String getTrackerType() { return trackerType; }
//    public List<TrackerEvent> getEvents() { return events; }
//
//    public void addEvent(TrackerEvent event) {
//        events.add(event);
//    }
//
//    public Optional<TrackerEvent> getMostRecentEvent() {
//        return events.isEmpty() ? Optional.empty() : Optional.of(events.get(events.size() - 1));
//    }
//}