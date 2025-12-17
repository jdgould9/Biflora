//GardenSectionTracker.java
//Represents a single garden zone's tracker
package net.jdgould.spring_garden.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    public void recordWeeding(LocalDateTime time, String details) {
        weedingHistory.add(new TrackerEvent(time, details));
    }

    public void recordWeeding(String details) {
        weedingHistory.add(new TrackerEvent(LocalDateTime.now(), details));
    }

    public void recordSoilPhTesting(LocalDateTime time, String details) {
        soilPhHistory.add(new TrackerEvent(time, details));
    }

    public void recordSoilPhTesting(String details) {
        soilPhHistory.add(new TrackerEvent(LocalDateTime.now(), details));
    }

    public void recordSoilMoistureTesting(LocalDateTime time, String details) {
        soilMoistureHistory.add(new TrackerEvent(time, details));
    }

    public void recordSoilMoistureTesting(String details) {
        soilMoistureHistory.add(new TrackerEvent(LocalDateTime.now(), details));
    }

    //GETTERS
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public List<TrackerEvent> getWeedingHistory() {
        return weedingHistory;
    }

    public List<TrackerEvent> getSoilPhHistory() {
        return soilPhHistory;
    }

    public List<TrackerEvent> getSoilMoistureHistory() {
        return soilMoistureHistory;
    }

}
