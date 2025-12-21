package net.jdgould.spring_garden.controller;

import net.jdgould.spring_garden.exception.GardenNotFoundException;
import net.jdgould.spring_garden.exception.GardenZoneNotFoundException;
import net.jdgould.spring_garden.exception.PlantNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(GardenNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleGardenNotFound(GardenNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(GardenZoneNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleGardenZoneNotFound(GardenZoneNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(PlantNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handlePlantNotFound(PlantNotFoundException e) {
        return e.getMessage();
    }
}
