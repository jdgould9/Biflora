//Plant.java
//Represents a single plant within a garden
package net.jdgould.spring_garden.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Entity
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long plantId;
    private String plantName;

    @ManyToOne
    private GardenZone gardenZone;

    @Embedded
    private PlantTracker plantTracker;

    protected Plant() {
    }

    public Plant(GardenZone gardenZone, String plantName) {
        this.gardenZone = gardenZone;
        this.plantName = plantName;
        this.plantTracker = PlantTracker.create();
    }

    //EVENT HANDLERS (DELEGATION)
    public void water(LocalDateTime time, String details) {
        plantTracker.recordWatering(time, details);
    }

    public void water(String details) {
        plantTracker.recordWatering(details);
    }

    public void fertilize(LocalDateTime time, String details) {
        plantTracker.recordFertilization(time, details);
    }

    public void fertilize(String details) {
        plantTracker.recordFertilization(details);
    }

    public void prune(LocalDateTime time, String details) {
        plantTracker.recordPruning(time, details);
    }

    public void prune(String details) {
        plantTracker.recordPruning(details);
    }

    public void pestTreatment(LocalDateTime time, String details) {
        plantTracker.recordPestTreatment(time, details);
    }

    public void pestTreatment(String details) {
        plantTracker.recordPestTreatment(details);
    }

    //SETTERS
    public void setPlantName(String plantName) {
        this.plantName = plantName;
    }

    //CLASS GETTERS
    public Long getPlantId() {
        return plantId;
    }

    public String getPlantName() {
        return plantName;
    }

    public GardenZone getGardenZone() {
        return gardenZone;
    }

    //TRACKER GETTERS
    public LocalDateTime getCreationDate() {
        return plantTracker.getCreationDate();
    }

    public List<TrackerEvent> getWateringHistory() {
        return plantTracker.getWateringHistory();
    }

    public Optional<TrackerEvent> getLastWateringEvent() {
        return TrackerEvent.getMostRecentEvent(plantTracker.getWateringHistory());
    }

    public List<TrackerEvent> getFertilizationHistory() {
        return plantTracker.getFertilizationHistory();
    }

    public Optional<TrackerEvent> getLastFertilizationEvent() {
        return TrackerEvent.getMostRecentEvent(plantTracker.getFertilizationHistory());
    }

    public List<TrackerEvent> getPruningHistory() {
        return plantTracker.getPruningHistory();
    }

    public Optional<TrackerEvent> getLastPruningEvent() {
        return TrackerEvent.getMostRecentEvent(plantTracker.getPruningHistory());
    }

    public List<TrackerEvent> getPestTreatmentHistory() {
        return plantTracker.getPestTreatmentHistory();
    }

    public Optional<TrackerEvent> getLastPestTreatmentEvent() {
        return TrackerEvent.getMostRecentEvent(plantTracker.getPestTreatmentHistory());
    }
}
