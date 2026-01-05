package net.jdgould.spring_garden.controller;

import net.jdgould.spring_garden.exception.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLIntegrityConstraintViolationException;

@CrossOrigin

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

    @ExceptionHandler(TrackableNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleTrackableNotFound(TrackableNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(TrackerPolicyNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleTrackerPolicyNotFound(TrackerPolicyNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(TrackerAssignmentNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleTrackerAssignmentNotFound(TrackerAssignmentNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(TrackerEventNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleTrackerEventNotFound(TrackerEventNotFoundException e) {
        return e.getMessage();
    }

    @ExceptionHandler(TrackerAssignmentAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleTrackerAssignmentAlreadyExists(TrackerAssignmentAlreadyExistsException e){
        return e.getMessage();
    }

    @ExceptionHandler(InvalidTrackerAssignmentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleInvalidTrackerAssignment(InvalidTrackerAssignmentException e){
        return e.getMessage();
    }

}
