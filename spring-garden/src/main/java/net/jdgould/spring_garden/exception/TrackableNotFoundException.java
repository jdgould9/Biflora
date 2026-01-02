package net.jdgould.spring_garden.exception;

public class TrackableNotFoundException extends RuntimeException {
    public TrackableNotFoundException(String message) {
        super(message);
    }
}
