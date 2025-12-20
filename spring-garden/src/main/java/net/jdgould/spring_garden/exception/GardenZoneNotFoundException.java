package net.jdgould.spring_garden.exception;

public class GardenZoneNotFoundException extends RuntimeException {
    public GardenZoneNotFoundException(String message) {
        super(message);
    }
}
