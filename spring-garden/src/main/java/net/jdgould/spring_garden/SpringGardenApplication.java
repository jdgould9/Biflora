package net.jdgould.spring_garden;

import net.jdgould.spring_garden.model.garden.Garden;
import net.jdgould.spring_garden.model.gardenzone.GardenZone;
import net.jdgould.spring_garden.model.plant.Plant;
import net.jdgould.spring_garden.repository.GardenRepository;
import net.jdgould.spring_garden.repository.PlantRepository;
import net.jdgould.spring_garden.service.GardenService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringGardenApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringGardenApplication.class, args);
    }
    //TODO:
    //  Implement testing
    //TODO:
    //  Data validation w/ jakarta.validation
    //TODO:
    //  Normalize parameter order in methods across controllers + services
    //TODO:
    //  PROPERLY configure CORS

    /// Features (most to least important)
    //TODO:
    //  Frontend
    //TODO:
    //  Add more useful flavor and functionality to model entities, DTOs
    //TODO:
    //  See all events per assignment
    //TODO:
    //  See overdue trackers
    //TODO:
    //  Role-based user access (admin, gardener, viewer)
}
