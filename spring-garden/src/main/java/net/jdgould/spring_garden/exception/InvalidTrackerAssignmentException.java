package net.jdgould.spring_garden.exception;

public class InvalidTrackerAssignmentException extends RuntimeException {
    public InvalidTrackerAssignmentException(String message) {
        super(message);
    }
}
