package net.jdgould.spring_garden.controller;

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
import net.jdgould.spring_garden.service.TrackerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/trackers")
public class TrackerController {
    private TrackerService trackerService;

    public TrackerController(TrackerService trackerService) {
        this.trackerService = trackerService;
    }

    /// TRACKER POLICY
    //Create a new tracking policy
    @PostMapping("")
    public ResponseEntity<TrackerPolicyCreationResponseDTO> createTrackerPolicy(@RequestBody TrackerPolicyCreationRequestDTO request) {
        TrackerPolicyCreationResponseDTO response = trackerService.addTrackerPolicy(request);
        URI location = URI.create("/api/trackers/" + response.trackerPolicyId());
        return ResponseEntity.created(location).body(response);
    }

    //Get all tracking policies
    @GetMapping("")
    public List<TrackerPolicyGetResponseDTO> getAllTrackerPolicies() {
        return trackerService.findAllTrackerPolicies();
    }

    //Get tracking policy
    @GetMapping("/{trackerPolicyId}")
    public TrackerPolicyGetResponseDTO getTrackerPolicyById(@PathVariable("trackerPolicyId") Long trackerPolicyId) {
        return trackerService.findTrackerPolicyById(trackerPolicyId);
    }

    //Delete tracking policy
    @DeleteMapping("/{trackerPolicyId}")
    public ResponseEntity<Void> deleteTrackerPolicyById(@PathVariable("trackerPolicyId") Long trackerPolicyId) {
        trackerService.deleteTrackerPolicyById(trackerPolicyId);
        return ResponseEntity.noContent().build();
    }

    //Update tracking policy
    @PatchMapping("/{trackerPolicyId}")
    public ResponseEntity<TrackerPolicyGetResponseDTO> updateTrackerPolicyById(@PathVariable("trackerPolicyId") Long trackerPolicyId, @RequestBody TrackerPolicyUpdateRequestDTO request) {
        TrackerPolicyGetResponseDTO response = trackerService.updateTrackerPolicyById(trackerPolicyId, request);
        return ResponseEntity.ok(response);
    }

    /// TRACKER ASSIGNMENT
    //Assign a policy to a trackable
    @PostMapping("/{trackerPolicyId}/assignments")
    public ResponseEntity<TrackerAssignmentCreationResponseDTO> createTrackerAssignment(@PathVariable("trackerPolicyId") Long trackerPolicyId,
                                                                                        @RequestBody TrackerAssignmentCreationRequestDTO request) {
        TrackerAssignmentCreationResponseDTO response = trackerService.addTrackerAssignment(trackerPolicyId, request);
        URI location = URI.create("/api/trackers/" + trackerPolicyId + "/assignments/" + response.trackerAssignmentId());

        return ResponseEntity.created(location).body(response);
    }

    //Get all assignments
    @GetMapping("/{trackerPolicyId}/assignments")
    public List<TrackerAssignmentGetResponseDTO> getAllTrackerAssignments(@PathVariable("trackerPolicyId") Long trackerPolicyId) {
        return trackerService.findAllTrackerAssignmentsInPolicy(trackerPolicyId);
    }

    //Get assignment
    @GetMapping("/{trackerPolicyId}/assignments/{trackerAssignmentId}")
    public TrackerAssignmentGetResponseDTO getTrackerAssignmentById(@PathVariable("trackerPolicyId") Long trackerPolicyId,
                                                                    @PathVariable("trackerAssignmentId") Long trackerAssignmentId) {
        return trackerService.findTrackerAssignmentById(trackerAssignmentId, trackerPolicyId);
    }

    //Unassign a policy to a trackable
    @DeleteMapping("/{trackerPolicyId}/assignments/{trackerAssignmentId}")
    public ResponseEntity<Void> deleteTrackerAssignmentById(@PathVariable("trackerPolicyId") Long trackerPolicyId,
                                                            @PathVariable("trackerAssignmentId") Long trackerAssignmentId) {
        trackerService.deleteTrackerAssignmentById(trackerAssignmentId, trackerPolicyId);
        return ResponseEntity.noContent().build();
    }

    /// TRACKER EVENT
    //Record a tracker event
    @PostMapping("/{trackerPolicyId}/assignments/{trackerAssignmentId}/events")
    public ResponseEntity<TrackerEventCreationResponseDTO> createTrackerEvent(@PathVariable("trackerPolicyId") Long trackerPolicyId,
                                                                              @PathVariable("trackerAssignmentId") Long trackerAssignmentId,
                                                                              @RequestBody TrackerEventCreationRequestDTO request) {
        TrackerEventCreationResponseDTO response = trackerService.addTrackerEvent(trackerAssignmentId, trackerPolicyId, request);
        URI location = URI.create("/api/trackers/" + trackerPolicyId + "/assignments/" + trackerAssignmentId + "/events/" + response.trackerEventId());
        return ResponseEntity.created(location).body(response);
    }

    //Get all events
    @GetMapping("/{trackerPolicyId}/assignments/{trackerAssignmentId}/events")
    public List<TrackerEventGetResponseDTO> getAllTrackerEvents(@PathVariable("trackerPolicyId") Long trackerPolicyId,
                                                                @PathVariable("trackerAssignmentId") Long trackerAssignmentId) {
        return trackerService.findAllTrackerEventsInAssignment(trackerAssignmentId, trackerPolicyId);
    }

    //Get event
    @GetMapping("/{trackerPolicyId}/assignments/{trackerAssignmentId}/events/{trackerEventId}")
    public TrackerEventGetResponseDTO getTrackerEventById(@PathVariable("trackerPolicyId") Long trackerPolicyId,
                                                          @PathVariable("trackerAssignmentId") Long trackerAssignmentId,
                                                          @PathVariable("trackerEventId") Long trackerEventId) {
        return trackerService.findTrackerEventById(trackerEventId, trackerAssignmentId, trackerPolicyId);
    }

    //Get most recent event for a tracker assignment
    @GetMapping("/{trackerPolicyId}/assignments/{trackerAssignmentId}/events/latest")
    public ResponseEntity<TrackerEventGetResponseDTO> getMostRecentTrackerEvent(@PathVariable("trackerPolicyId") Long trackerPolicyId,
                                                                                @PathVariable("trackerAssignmentId") Long trackerAssignmentId) {
        return trackerService.findMostRecentTrackerEvent(trackerPolicyId, trackerAssignmentId)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NO_CONTENT));
    }

    //Delete tracker event from assignment
    @DeleteMapping("/{trackerPolicyId}/assignments/{trackerAssignmentId}/events/{trackerEventId}")
    public ResponseEntity<Void> deleteTrackerEventById(@PathVariable("trackerPolicyId") Long trackerPolicyId,
                                                       @PathVariable("trackerAssignmentId") Long trackerAssignmentId,
                                                       @PathVariable("trackerEventId") Long trackerEventId) {
        trackerService.deleteTrackerEventById(trackerEventId, trackerAssignmentId, trackerPolicyId);
        return ResponseEntity.noContent().build();
    }
}
