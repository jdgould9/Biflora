package net.jdgould.spring_garden;

import net.jdgould.spring_garden.model.garden.Garden;
import net.jdgould.spring_garden.model.gardenzone.GardenZone;
import net.jdgould.spring_garden.model.plant.Plant;
import net.jdgould.spring_garden.repository.PlantRepository;
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
    //  Restructure trackers
    //TODO:
    //  Implement reminder/notification scheduling mechanism

    @Bean
    CommandLineRunner commandLineRunner(PlantRepository repository) {
        return args -> {
//            System.out.println("Hello.");
//
//            Garden g1 = new Garden("My garden");
//
//            GardenZone gz1 = new GardenZone(g1, "garden zone 1");
//            Plant p1 = new Plant(gz1, "rose");
//            Plant p2 = new Plant(gz1, "lily");
//
//            GardenZone gz2 = new GardenZone(g1, "garden zone 2");
//            Plant p3 = new Plant(gz2, "oak");
//            Plant p4 = new Plant(gz2, "maple");
//

//            Tree oak = new Tree("Oak");
//            Flower rose = new Flower("Sunflower");
//
//            // Save them to the database
//            repository.save(oak);
//            repository.save(rose);
//
//            // Fetch all plants and print
//            repository.findAll().forEach(plant ->
//                    System.out.println(plant.getName())
//            );
        };
    }

}
