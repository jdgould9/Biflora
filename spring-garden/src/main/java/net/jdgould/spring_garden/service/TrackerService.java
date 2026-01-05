package net.jdgould.spring_garden.service;

import net.jdgould.spring_garden.dto.tracker.assignment.TrackerEventCreationRequestDTO;
import net.jdgould.spring_garden.dto.tracker.assignment.TrackerEventCreationResponseDTO;
import net.jdgould.spring_garden.dto.tracker.assignment.TrackerEventGetResponseDTO;
import net.jdgould.spring_garden.dto.tracker.event.TrackerAssignmentCreationRequestDTO;
import net.jdgould.spring_garden.dto.tracker.event.TrackerAssignmentCreationResponseDTO;
import net.jdgould.spring_garden.dto.tracker.event.TrackerAssignmentGetResponseDTO;
import net.jdgould.spring_garden.dto.tracker.policy.TrackerPolicyCreationRequestDTO;
import net.jdgould.spring_garden.dto.tracker.policy.TrackerPolicyCreationResponseDTO;
import net.jdgould.spring_garden.dto.tracker.policy.TrackerPolicyGetResponseDTO;
import net.jdgould.spring_garden.dto.tracker.policy.TrackerPolicyUpdateRequestDTO;
import net.jdgould.spring_garden.dto.tracker.trackable.TrackableGetResponseDTO;
import net.jdgould.spring_garden.exception.*;
import net.jdgould.spring_garden.model.tracker.Trackable;
import net.jdgould.spring_garden.model.tracker.TrackerAssignment;
import net.jdgould.spring_garden.model.tracker.TrackerEvent;
import net.jdgould.spring_garden.model.tracker.TrackerPolicy;
import net.jdgould.spring_garden.repository.TrackableRepository;
import net.jdgould.spring_garden.repository.TrackerAssignmentRepository;
import net.jdgould.spring_garden.repository.TrackerEventRepository;
import net.jdgould.spring_garden.repository.TrackerPolicyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

//TODO:
//Let parent set relationship
//Right now relationship is set in both places for a loto enitites:
//E.g.:
/*
new TrackerEvent(trackerAssignment, ...)
trackerAssignment.addTrackerEvent(newTrackerEvent);
 */

//Let trackerAssignment handle it:
/*
TrackerEvent newTrackerEvent =
        new TrackerEvent(request.eventDetails());

trackerAssignment.addTrackerEvent(newTrackerEvent);
trackerEventRepository.save(newTrackerEvent);
 */

//TODO:
//Avoid loading (parent) entities when not needed?

@Service
public class TrackerService {
    private final TrackerPolicyRepository trackerPolicyRepository;
    private final TrackerAssignmentRepository trackerAssignmentRepository;
    private final TrackerEventRepository trackerEventRepository;
    private final TrackableRepository trackableRepository;

    public TrackerService(TrackerPolicyRepository trackerPolicyRepository,
                          TrackerAssignmentRepository trackerAssignmentRepository,
                          TrackerEventRepository trackerEventRepository,
                          TrackableRepository trackableRepository) {
        this.trackerPolicyRepository = trackerPolicyRepository;
        this.trackerAssignmentRepository = trackerAssignmentRepository;
        this.trackerEventRepository = trackerEventRepository;
        this.trackableRepository = trackableRepository;

    }

    ///TRACKABLE
    public List<TrackableGetResponseDTO>  findAllTrackables() {
        return trackableRepository.findAll().stream()
                .map(this::trackableEntityToGetResponseDTO)
                .toList();
    }

    public TrackableGetResponseDTO findTrackableById(Long trackableId) {
        Trackable trackable = findTrackableEntityById(trackableId);
        return trackableEntityToGetResponseDTO(trackable);
    }


    ///TRACKER POLICY
    public TrackerPolicyCreationResponseDTO addTrackerPolicy(TrackerPolicyCreationRequestDTO request) {
        TrackerPolicy savedTrackerPolicy = trackerPolicyRepository.save(new TrackerPolicy(request.trackerName(), request.targetType(), request.trackerDescription(), request.intervalHours()));
        return new TrackerPolicyCreationResponseDTO(savedTrackerPolicy.getTrackerPolicyId());
    }

    public List<TrackerPolicyGetResponseDTO> findAllTrackerPolicies() {
        return trackerPolicyRepository.findAll().stream()
                .map(this::trackerPolicyEntityToGetResponseDTO)
                .toList();
    }

    public TrackerPolicyGetResponseDTO findTrackerPolicyById(Long trackerPolicyId) {
        TrackerPolicy trackerPolicy = findTrackerPolicyEntityById(trackerPolicyId);
        return trackerPolicyEntityToGetResponseDTO(trackerPolicy);
    }

    public void deleteTrackerPolicyById(Long trackerPolicyId) {
        TrackerPolicy trackerPolicy = findTrackerPolicyEntityById(trackerPolicyId);
        trackerPolicyRepository.delete(trackerPolicy);
    }

    public TrackerPolicyGetResponseDTO updateTrackerPolicyById(Long trackerPolicyId, TrackerPolicyUpdateRequestDTO request) {
        TrackerPolicy trackerPolicy = findTrackerPolicyEntityById(trackerPolicyId);
        if (request.name() != null && !request.name().isBlank()) {
            trackerPolicy.setName(request.name());
        }

        if (request.description() != null && !request.description().isBlank()) {
            trackerPolicy.setDescription(request.description());
        }

        if (request.intervalHours() != null && request.intervalHours() > 0) {
            trackerPolicy.setIntervalHours(request.intervalHours());
        }
        trackerPolicyRepository.save(trackerPolicy);
        return trackerPolicyEntityToGetResponseDTO(trackerPolicy);
    }

    ///TRACKER ASSIGNMENTS
    public TrackerAssignmentCreationResponseDTO addTrackerAssignment(Long trackerPolicyId, TrackerAssignmentCreationRequestDTO request){
        TrackerPolicy trackerPolicy = findTrackerPolicyEntityById(trackerPolicyId);
        Trackable trackable = findTrackableEntityById(request.trackableId());

        //Check to make sure trackerPolicy's targetType and trackable's trackableType are compatible
        if(trackerPolicy.getTargetType()!=trackable.getTrackableType()){
            throw new InvalidTrackerAssignmentException("Tracker policy is for " + trackerPolicy.getTargetType() +", trackable entity is of type " + trackable.getTrackableType());
        }

        //Check to make sure tracker assignment doesn't already exist
        if(trackerAssignmentRepository.findTrackerAssignmentByAssignedToAndTrackerPolicy(trackable, trackerPolicy).isPresent()){
            throw new TrackerAssignmentAlreadyExistsException("Trackable entity " + trackable.getId() + " is already assigned to tracker policy " + trackerPolicyId);
        }

        TrackerAssignment newTrackerAssignment = new TrackerAssignment(trackerPolicy, trackable);

        trackerPolicy.addTrackerAssignment(newTrackerAssignment);
        trackerAssignmentRepository.save(newTrackerAssignment);

        return new TrackerAssignmentCreationResponseDTO(newTrackerAssignment.getTrackerAssignmentId());
    }

    public List<TrackerAssignmentGetResponseDTO> findAllTrackerAssignmentsInPolicy(Long trackerPolicyId){
        return trackerAssignmentRepository.findAllByTrackerPolicy(findTrackerPolicyEntityById(trackerPolicyId)).stream()
                .map(this::trackerAssignmentEntityToGetResponseDTO)
                .toList();
    }

    public TrackerAssignmentGetResponseDTO findTrackerAssignmentById(Long trackerAssignmentId, Long trackerPolicyId){
        TrackerAssignment trackerAssignment = findTrackerAssignmentEntityById(trackerAssignmentId, trackerPolicyId);
        return trackerAssignmentEntityToGetResponseDTO(trackerAssignment);
    }

    public void deleteTrackerAssignmentById(Long trackerAssignmentId, Long trackerPolicyId){
        TrackerPolicy trackerPolicy = findTrackerPolicyEntityById(trackerPolicyId);
        TrackerAssignment trackerAssignment = findTrackerAssignmentEntityById(trackerAssignmentId, trackerPolicyId);
        Trackable trackable = findTrackableEntityById(trackerAssignment.getAssignedToId());

        trackable.removeTrackerAssignment(trackerAssignment);
        trackerPolicy.removeTrackerAssignment(trackerAssignment);

        trackerAssignmentRepository.delete(trackerAssignment);
    }


    /// TRACKER EVENTS
    public TrackerEventCreationResponseDTO addTrackerEvent(Long trackerAssignmentId, Long trackerPolicyId, TrackerEventCreationRequestDTO request){
        TrackerAssignment trackerAssignment = findTrackerAssignmentEntityById(trackerAssignmentId, trackerPolicyId);
        TrackerEvent newTrackerEvent = new TrackerEvent(trackerAssignment, request.eventDetails());

        trackerAssignment.addTrackerEvent(newTrackerEvent);
        trackerEventRepository.save(newTrackerEvent);

        return new TrackerEventCreationResponseDTO(newTrackerEvent.getTrackerEventId(), newTrackerEvent.getRecordedTime());
    }

    public List<TrackerEventGetResponseDTO> findAllTrackerEventsInAssignment(Long trackerAssignmentId, Long trackerPolicyId) {
        return trackerEventRepository.findAllByTrackerAssignment(findTrackerAssignmentEntityById(trackerAssignmentId, trackerPolicyId)).stream()
                .map(this::trackerEventEntityToGetResponseDTO)
                .toList();
    }

    public TrackerEventGetResponseDTO findTrackerEventById(Long trackerEventId, Long trackerAssignmentId, Long trackerPolicyId) {
        return trackerEventEntityToGetResponseDTO(findTrackerEventEntityById(trackerEventId, trackerAssignmentId, trackerPolicyId));
    }

    public Optional<TrackerEventGetResponseDTO> findMostRecentTrackerEvent(Long trackerPolicyId, Long trackerAssignmentId) {
        TrackerAssignment trackerAssignment = findTrackerAssignmentEntityById(trackerAssignmentId, trackerPolicyId);
        return trackerEventRepository
                .findTopByTrackerAssignmentOrderByRecordedTimeDesc(trackerAssignment)
                .map(this::trackerEventEntityToGetResponseDTO);
    }

    public void deleteTrackerEventById(Long trackerEventId, Long trackerAssignmentId, Long trackerPolicyId) {
        TrackerAssignment trackerAssignment = findTrackerAssignmentEntityById(trackerAssignmentId, trackerPolicyId);
        TrackerEvent trackerEvent = findTrackerEventEntityById(trackerEventId, trackerAssignmentId, trackerPolicyId);

        trackerAssignment.removeTrackerEvent(trackerEvent);
        trackerEventRepository.delete(trackerEvent);
    }

    /// HELPERS
    private TrackableGetResponseDTO trackableEntityToGetResponseDTO(Trackable trackable){
        return new TrackableGetResponseDTO(
                trackable.getId(),
                trackable.getName(),
                trackable.getTrackableType()
        );
    }

    private TrackerPolicyGetResponseDTO trackerPolicyEntityToGetResponseDTO(TrackerPolicy trackerPolicy) {
        return new TrackerPolicyGetResponseDTO(
                trackerPolicy.getTrackerPolicyId(),
                trackerPolicy.getTargetType(),
                trackerPolicy.getName(),
                trackerPolicy.getDescription(),
                trackerPolicy.getIntervalHours(),
                trackerPolicy.getCreationTime()
        );
    }

    private TrackerAssignmentGetResponseDTO trackerAssignmentEntityToGetResponseDTO(TrackerAssignment trackerAssignment){
        return new TrackerAssignmentGetResponseDTO(
                trackerAssignment.getTrackerAssignmentId(),
                trackerAssignment.getAssignedToId(),
                trackerAssignment.getStartDate()
        );
    }

    private TrackerEventGetResponseDTO trackerEventEntityToGetResponseDTO(TrackerEvent trackerEvent){
        return new TrackerEventGetResponseDTO(
                trackerEvent.getTrackerEventId(),
                trackerEvent.getRecordedTime(),
                trackerEvent.getDetails()
        );
    }

    protected TrackerEvent findTrackerEventEntityById(Long trackerEventId, Long trackerAssignmentId, Long trackerPolicyId){
        TrackerAssignment trackerAssignment = findTrackerAssignmentEntityById(trackerAssignmentId, trackerPolicyId);
        return trackerEventRepository.findTrackerEventByTrackerEventIdAndTrackerAssignment(trackerEventId, trackerAssignment)
                .orElseThrow(()->new TrackerEventNotFoundException("Tracker event not found with id: " + trackerEventId));
    }

    protected TrackerPolicy findTrackerPolicyEntityById(Long trackerPolicyId) {
        return trackerPolicyRepository.findById(trackerPolicyId)
                .orElseThrow(() -> new TrackerPolicyNotFoundException("Tracker policy not found with id: " + trackerPolicyId));
    }

    protected TrackerAssignment findTrackerAssignmentEntityById(Long trackerAssignmentId, Long trackerPolicyId){
        TrackerPolicy trackerPolicy = findTrackerPolicyEntityById(trackerPolicyId);
        return trackerAssignmentRepository.findTrackerAssignmentByTrackerAssignmentIdAndTrackerPolicy(trackerAssignmentId, trackerPolicy)
                .orElseThrow(()->new TrackerAssignmentNotFoundException("Tracker assignment not found with id: " + trackerAssignmentId));
    }

    protected Trackable findTrackableEntityById(Long trackableId){
        return trackableRepository.findById(trackableId)
                .orElseThrow(()->new TrackableNotFoundException("Trackable entity (garden, gardenzone, plant) not found with id: " + trackableId));
    }
}
