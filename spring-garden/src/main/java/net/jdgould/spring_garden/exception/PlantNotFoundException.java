package net.jdgould.spring_garden.exception;

public class PlantNotFoundException extends RuntimeException {
    public PlantNotFoundException(String message) {
        super(message);
    }
}
