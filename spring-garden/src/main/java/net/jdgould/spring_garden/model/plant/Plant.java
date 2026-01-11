//Plant.java
package net.jdgould.spring_garden.model.plant;

import jakarta.persistence.*;
import net.jdgould.spring_garden.model.garden.Garden;
import net.jdgould.spring_garden.model.tracker.Trackable;
import net.jdgould.spring_garden.model.tracker.TrackableType;
import net.jdgould.spring_garden.model.tracker.TrackerEvent;
import net.jdgould.spring_garden.model.gardenzone.GardenZone;

import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "plant")
public class Plant extends Trackable {
    @ManyToOne
    @JoinColumn(name = "garden_zone_id", nullable = false)
    private GardenZone gardenZone;

    protected Plant() {
    }

    public Plant(GardenZone gardenZone, String plantName) {
        super(plantName);
        this.gardenZone = gardenZone;
    }

    public void setGardenZone(GardenZone gardenZone){
        this.gardenZone=gardenZone;
    }

    @Override
    public TrackableType getTrackableType() {
        return TrackableType.PLANT;
    }

    public GardenZone getGardenZone() {
        return gardenZone;
    }
}
