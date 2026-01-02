package net.jdgould.spring_garden.service;

import net.jdgould.spring_garden.dto.tracker.event.TrackerAssignmentCreationRequestDTO;
import net.jdgould.spring_garden.dto.tracker.event.TrackerAssignmentCreationResponseDTO;
import net.jdgould.spring_garden.dto.tracker.event.TrackerAssignmentGetResponseDTO;
import net.jdgould.spring_garden.dto.tracker.policy.TrackerPolicyCreationRequestDTO;
import net.jdgould.spring_garden.dto.tracker.policy.TrackerPolicyCreationResponseDTO;
import net.jdgould.spring_garden.dto.tracker.policy.TrackerPolicyGetResponseDTO;
import net.jdgould.spring_garden.dto.tracker.policy.TrackerPolicyUpdateRequestDTO;
import net.jdgould.spring_garden.exception.GardenZoneNotFoundException;
import net.jdgould.spring_garden.exception.TrackableNotFoundException;
import net.jdgould.spring_garden.exception.TrackerAssignmentNotFoundException;
import net.jdgould.spring_garden.exception.TrackerPolicyNotFoundException;
import net.jdgould.spring_garden.model.garden.Garden;
import net.jdgould.spring_garden.model.gardenzone.GardenZone;
import net.jdgould.spring_garden.model.tracker.Trackable;
import net.jdgould.spring_garden.model.tracker.TrackerAssignment;
import net.jdgould.spring_garden.model.tracker.TrackerPolicy;
import net.jdgould.spring_garden.repository.TrackableRepository;
import net.jdgould.spring_garden.repository.TrackerAssignmentRepository;
import net.jdgould.spring_garden.repository.TrackerPolicyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackerPolicyService {
    private final TrackableRepository trackableRepository;
    private final TrackerPolicyRepository trackerPolicyRepository;
    private final TrackerAssignmentRepository trackerAssignmentRepository;

    public TrackerPolicyService(TrackerPolicyRepository trackerPolicyRepository,
                                TrackerAssignmentRepository trackerAssignmentRepository,
                                TrackableRepository trackableRepository) {
        this.trackableRepository = trackableRepository;
        this.trackerPolicyRepository = trackerPolicyRepository;
        this.trackerAssignmentRepository = trackerAssignmentRepository;

    }

    ///TRACKER POLICY
    public TrackerPolicyCreationResponseDTO addTrackerPolicy(TrackerPolicyCreationRequestDTO request) {
        TrackerPolicy savedTrackerPolicy = trackerPolicyRepository.save(new TrackerPolicy(request.name(), request.description(), request.intervalHours()));
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
        TrackerAssignment savedTrackerAssignment = trackerAssignmentRepository.save(new TrackerAssignment(trackerPolicy, trackable));
        trackerPolicy.addTrackerAssignment(savedTrackerAssignment);

        return new TrackerAssignmentCreationResponseDTO(savedTrackerAssignment.getTrackerAssignmentId());
    }

    public List<TrackerAssignmentGetResponseDTO> findAllTrackerAssignmentsInPolicy(Long trackerPolicyId){
        TrackerPolicy trackerPolicy = findTrackerPolicyEntityById(trackerPolicyId);
        return trackerAssignmentRepository.findAllByTrackerPolicy(trackerPolicy).stream()
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

        trackerAssignmentRepository.delete(trackerAssignment);
        trackerPolicy.removeTrackerAssignment(trackerAssignment);
    }

    /// HELPERS
    private TrackerPolicyGetResponseDTO trackerPolicyEntityToGetResponseDTO(TrackerPolicy trackerPolicy) {
        return new TrackerPolicyGetResponseDTO(
                trackerPolicy.getTrackerPolicyId(),
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
                .orElseThrow(()->new TrackableNotFoundException("Trackable entity (garden, gardenzone, plant) not found" +
                        "with id: " + trackableId));
    }


}
