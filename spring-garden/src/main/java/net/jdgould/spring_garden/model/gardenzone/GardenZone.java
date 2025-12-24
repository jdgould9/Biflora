//GardenSection.java
//Represents a zone of a garden containing plant(s)
package net.jdgould.spring_garden.model.gardenzone;

import jakarta.persistence.*;
import net.jdgould.spring_garden.model.plant.Plant;
import net.jdgould.spring_garden.model.tracker.TrackerEvent;
import net.jdgould.spring_garden.model.garden.Garden;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
public class GardenZone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gardenZoneId;
    @Column(nullable = false)
    private String gardenZoneName;

    @OneToMany(mappedBy = "gardenZone", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Plant> plants = new ArrayList<>();

    @ManyToOne
    @JoinColumn(nullable = false)
    private Garden garden;

//    @Embedded
//    private GardenZoneTracker gardenZoneTracker;

    protected GardenZone() {
    }

    public GardenZone(Garden garden, String gardenZoneName) {
        this.garden = garden;
        this.gardenZoneName = gardenZoneName;
//        this.gardenZoneTracker = GardenZoneTracker.create();
    }

//    //EVENT HANDLERS (DELEGATION)
//    public TrackerEvent recordEvent(GardenZoneTrackerEventType eventType, String details){
//        return switch(eventType){
//            case WEED -> gardenZoneTracker.recordWeeding(details);
//            case TEST_SOIL_PH -> gardenZoneTracker.recordSoilPhTesting(details);
//            case TEST_SOIL_MOISTURE -> gardenZoneTracker.recordSoilMoistureTesting(details);
//        };
//    }

//    public List<TrackerEvent> getEventHistory(GardenZoneTrackerEventType eventType){
//        return gardenZoneTracker.getEventHistory(eventType);
//    }
//
//    public Optional<TrackerEvent> getMostRecentEvent(GardenZoneTrackerEventType eventType){
//        return gardenZoneTracker.getMostRecentEvent(eventType);
//    }

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

//    //TRACKER GETTERS
//    public GardenZoneTracker getTracker() {
//        return this.gardenZoneTracker;
//    }
}
