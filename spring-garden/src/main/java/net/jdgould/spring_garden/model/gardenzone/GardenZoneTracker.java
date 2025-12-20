//GardenSectionTracker.java
//Represents a single garden zone's tracker
package net.jdgould.spring_garden.model.gardenzone;

import jakarta.persistence.*;
import net.jdgould.spring_garden.model.tracker.TrackerEvent;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Embeddable
public class GardenZoneTracker {
    @Column(nullable = false, updatable = false)
    private LocalDateTime creationDate; //time garden section was added to the garden

    @ElementCollection
    @CollectionTable(name = "weeded_history")
    @OrderColumn
    private List<TrackerEvent> weedingHistory = new ArrayList<>(); //times garden zone was weeded

    @ElementCollection
    @CollectionTable(name = "soil_ph_history")
    @OrderColumn
    private List<TrackerEvent> soilPhHistory = new ArrayList<>(); //times garden zone pH level was tested

    @ElementCollection
    @CollectionTable(name = "soil_moisture_history")
    @OrderColumn
    private List<TrackerEvent> soilMoistureHistory = new ArrayList<>(); //times garden zone moisture level was tested

    protected GardenZoneTracker() {
    }

    public static GardenZoneTracker create() {
        GardenZoneTracker gardenZoneTracker = new GardenZoneTracker();
        gardenZoneTracker.creationDate = LocalDateTime.now();
        return gardenZoneTracker;
    }

    //EVENT HANDLERS
    protected TrackerEvent recordWeeding(String details) {
        TrackerEvent event = new TrackerEvent(LocalDateTime.now(), details);
        weedingHistory.add(event);
        return event;
    }

    protected TrackerEvent recordSoilPhTesting(String details) {
        TrackerEvent event = new TrackerEvent(LocalDateTime.now(), details);
        soilPhHistory.add(event);
        return event;
    }

    protected TrackerEvent recordSoilMoistureTesting(String details) {
        TrackerEvent event = new TrackerEvent(LocalDateTime.now(), details);
        soilMoistureHistory.add(event);
        return event;
    }

    //GETTERS
    public List<TrackerEvent> getEventHistory(GardenZoneTrackerEventType eventType){
        return switch(eventType){
            case WEED -> weedingHistory;
            case TEST_SOIL_PH -> soilPhHistory;
            case TEST_SOIL_MOISTURE -> soilMoistureHistory;
        };
    }

    public Optional<TrackerEvent> getMostRecentEvent(GardenZoneTrackerEventType eventType){
        List<TrackerEvent> events = getEventHistory(eventType);
        return events.isEmpty() ?  Optional.empty() : Optional.of(events.getLast());
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }
}
