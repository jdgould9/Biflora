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
    //  Implement meaningful HTTP status for controller response:
    //  E.g. Post mapping should return CREATED, not 200 OK, CREATED is more meaningful

    //TODO:
    //  Implement exception handling w/ global exception handler, custom exceptions
    //  Services should not throw HTTPStatus exceptions: Only controller should.
    //  Services should throw domain exceptions when needed (E.g. GardenNotFoundException)
    //  Controller should handle it as HTTP response
    //  Controller catches using @ControllerAdvice for exception handling to keep controller method clean
    //  Controller shouldn't be manually throwing status exceptions: While this is OK, it can be better





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
