//GardenSection.java
package net.jdgould.spring_garden.model.gardenzone;

import jakarta.persistence.*;
import net.jdgould.spring_garden.model.plant.Plant;
import net.jdgould.spring_garden.model.tracker.Trackable;
import net.jdgould.spring_garden.model.tracker.TrackableType;
import net.jdgould.spring_garden.model.tracker.TrackerEvent;
import net.jdgould.spring_garden.model.garden.Garden;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "garden_zone")
public class GardenZone extends Trackable {
    @ManyToOne
    @JoinColumn(name = "garden_id", nullable = false)
    private Garden garden;

    @OneToMany(mappedBy = "gardenZone", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Plant> plants = new ArrayList<>();

    protected GardenZone() {
    }

    public GardenZone(Garden garden, String gardenZoneName) {
        super(gardenZoneName);
        this.garden = garden;
    }

    public List<Plant> getPlants(){
        return this.plants;
    }

    public void addPlant(Plant plant){
        plants.add(plant);
        plant.setGardenZone(this);
    }

    public void removePlant(Plant plant){
        plants.remove(plant);
        plant.setGardenZone(null);
    }

    public void setGarden(Garden garden){
        this.garden=garden;
    }

    @Override
    public TrackableType getTrackableType() {
        return TrackableType.GARDENZONE;
    }

    public Garden getGarden() {
        return garden;
    }
}
