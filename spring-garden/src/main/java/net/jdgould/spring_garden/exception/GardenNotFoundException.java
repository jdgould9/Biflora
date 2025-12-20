package net.jdgould.spring_garden.exception;

public class GardenNotFoundException extends RuntimeException {
    public GardenNotFoundException(String message) {
        super(message);
    }
}
