//GardenSection.java
//Represents a zone of a garden containing plant(s)
package net.jdgould.spring_garden.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class GardenZone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gardenZoneId;
    private String gardenZoneName;

    @OneToMany(mappedBy = "gardenZone", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Plant> plants = new ArrayList<>();

    @ManyToOne
    private Garden garden;

    @Embedded
    private GardenZoneTracker gardenZoneTracker;

    protected GardenZone() {
    }

    public GardenZone(Garden garden, String gardenZoneName) {
        this.garden = garden;
        this.gardenZoneName = gardenZoneName;
        this.gardenZoneTracker = GardenZoneTracker.create();
    }

    //EVENT HANDLERS (DELEGATION)
    public void weed(LocalDateTime time, String details) {
        gardenZoneTracker.recordWeeding(time, details);
    }

    public void weed(String details) {
        gardenZoneTracker.recordWeeding(details);
    }

    public void testSoilPh(LocalDateTime time, String details) {
        gardenZoneTracker.recordSoilPhTesting(time, details);
    }

    public void testSoilPh(String details) {
        gardenZoneTracker.recordSoilPhTesting(details);
    }

    public void testSoilMoisture(LocalDateTime time, String details) {
        gardenZoneTracker.recordSoilMoistureTesting(time, details);
    }

    public void testSoilMoisture(String details) {
        gardenZoneTracker.recordSoilMoistureTesting(details);
    }

    //SETTERS
    public void setGardenZoneName(String gardenZoneName) {
        this.gardenZoneName = gardenZoneName;
    }

    //CLASS GETTERS
    public Long getGardenZoneId() {
        return gardenZoneId;
    }

    public String getGardenZoneName() {
        return gardenZoneName;
    }

    public Garden getGarden() {
        return garden;
    }

    public List<Plant> getPlants() {
        return plants;
    }
    
    //TRACKER GETTERS
    public LocalDateTime getCreationDate() {
        return gardenZoneTracker.getCreationDate();
    }

    public List<TrackerEvent> getWeedingHistory() {
        return gardenZoneTracker.getWeedingHistory();
    }

    public Optional<TrackerEvent> getLastWeedingEvent() {
        return TrackerEvent.getMostRecentEvent(gardenZoneTracker.getWeedingHistory());
    }

    public List<TrackerEvent> getSoilPhHistory() {
        return gardenZoneTracker.getSoilPhHistory();
    }

    public Optional<TrackerEvent> getLastSoilPhTestingEvent() {
        return TrackerEvent.getMostRecentEvent(gardenZoneTracker.getSoilPhHistory());
    }

    public List<TrackerEvent> getSoilMoistureHistory() {
        return gardenZoneTracker.getSoilMoistureHistory();
    }

    public Optional<TrackerEvent> getLastSoilMoistureTestingEvent() {
        return TrackerEvent.getMostRecentEvent(gardenZoneTracker.getSoilMoistureHistory());
    }
}
