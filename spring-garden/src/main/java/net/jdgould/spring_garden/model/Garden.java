//Garden.java
//Represents a user's garden, containing garden zone(s), which contain plant(s)
package net.jdgould.spring_garden.model;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Garden {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long gardenId;
    private String gardenName;

    @OneToMany(mappedBy = "garden", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GardenZone> gardenZones = new ArrayList<>();

    protected Garden() {
    }

    public Garden(String gardenName) {
        this.gardenName = gardenName;
    }

    //SETTERS
    public void setGardenName(){
        this.gardenName=gardenName;
    }

    //CLASS GETTERS
    public Long getGardenId(){
        return this.gardenId;
    }
    public String getGardenName(){
        return this.gardenName;
    }
}
