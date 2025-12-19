//PlantTracker.java
//Represents a single plant's tracker
package net.jdgould.spring_garden.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Embeddable
public class PlantTracker {
    @Column(nullable = false, updatable = false)
    private LocalDateTime creationDate; //time plant was added to the garden section

    @ElementCollection
    @CollectionTable(name = "watering_history")
    @OrderColumn
    private List<TrackerEvent> wateringHistory = new ArrayList<>(); //times plant was watered

    @ElementCollection
    @CollectionTable(name = "fertilization_history")
    @OrderColumn
    private List<TrackerEvent> fertilizationHistory = new ArrayList<>(); //times plant was fertilized

    @ElementCollection
    @CollectionTable(name = "pruning_history")
    @OrderColumn
    private List<TrackerEvent> pruningHistory = new ArrayList<>(); //times plant was pruned

    @ElementCollection
    @CollectionTable(name = "pest_treatment_history")
    @OrderColumn
    private List<TrackerEvent> pestTreatmentHistory = new ArrayList<>(); //times plant was treated for pests

    protected PlantTracker() {
    }

    public static PlantTracker create() {
        PlantTracker plantTracker = new PlantTracker();
        plantTracker.creationDate = LocalDateTime.now();
        return plantTracker;
    }

    //EVENT HANDLERS
    public void recordWatering(LocalDateTime time, String details) {
        wateringHistory.add(new TrackerEvent(time, details));
    }

    public void recordWatering(String details) {
        wateringHistory.add(new TrackerEvent(LocalDateTime.now(), details));
    }

    public void recordFertilization(LocalDateTime time, String details) {
        fertilizationHistory.add(new TrackerEvent(time, details));
    }

    public void recordFertilization(String details) {
        fertilizationHistory.add(new TrackerEvent(LocalDateTime.now(), details));
    }

    public void recordPruning(LocalDateTime time, String details) {
        pruningHistory.add(new TrackerEvent(time, details));
    }

    public void recordPruning(String details) {
        pruningHistory.add(new TrackerEvent(LocalDateTime.now(), details));
    }

    public void recordPestTreatment(LocalDateTime time, String details) {
        pestTreatmentHistory.add(new TrackerEvent(time, details));
    }

    public void recordPestTreatment(String details) {
        pestTreatmentHistory.add(new TrackerEvent(LocalDateTime.now(), details));
    }

    //GETTERS
    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public List<TrackerEvent> getWateringHistory() {
        return wateringHistory;
    }

    public List<TrackerEvent> getFertilizationHistory() {
        return fertilizationHistory;
    }

    public List<TrackerEvent> getPruningHistory() {
        return pruningHistory;
    }

    public List<TrackerEvent> getPestTreatmentHistory() {
        return pestTreatmentHistory;
    }
}
