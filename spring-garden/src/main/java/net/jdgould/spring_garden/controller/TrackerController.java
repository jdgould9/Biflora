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
import net.jdgould.spring_garden.service.TrackerPolicyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/trackers")
public class TrackerController {
    private TrackerPolicyService trackerPolicyService;

    public TrackerController(TrackerPolicyService trackerPolicyService){
        this.trackerPolicyService = trackerPolicyService;
    }

    /// TRACKER POLICY
    //Create a new tracking policy
    @PostMapping("")
    public ResponseEntity<TrackerPolicyCreationResponseDTO> createTrackerPolicy(@RequestBody TrackerPolicyCreationRequestDTO request){
        TrackerPolicyCreationResponseDTO response = trackerPolicyService.addTrackerPolicy(request);
        URI location = URI.create("/api/trackers/" + response.trackerPolicyId());
        return ResponseEntity.created(location).body(response);
    }

    //Get all tracking policies
    @GetMapping("")
    public List<TrackerPolicyGetResponseDTO> getAllTrackerPolicies(){
        return trackerPolicyService.findAllTrackerPolicies();
    }

    //Get tracking policy
    @GetMapping("/{trackerPolicyId}")
    public TrackerPolicyGetResponseDTO getTrackerPolicyById(@PathVariable("trackerPolicyId") Long trackerPolicyId){
        return trackerPolicyService.findTrackerPolicyById(trackerPolicyId);
    }

    //Delete tracking policy
    @DeleteMapping("/{trackerPolicyId}")
    public ResponseEntity<Void> deleteTrackerPolicyById(@PathVariable("trackerPolicyId") Long trackerPolicyId){
        trackerPolicyService.deleteTrackerPolicyById(trackerPolicyId);
        return ResponseEntity.noContent().build();
    }

    //Update tracking policy
    @PatchMapping("/{trackerPolicyId}")
    public ResponseEntity<TrackerPolicyGetResponseDTO> updateTrackerPolicyById(@PathVariable("trackerPolicyId") Long trackerPolicyId, @RequestBody TrackerPolicyUpdateRequestDTO request){
        TrackerPolicyGetResponseDTO response = trackerPolicyService.updateTrackerPolicyById(trackerPolicyId, request);
        return ResponseEntity.ok(response);
    }

    /// TRACKER ASSIGNMENT
    //Assign a policy to a trackable
    @PostMapping("/{trackerPolicyId}/assignments")
    public ResponseEntity<TrackerAssignmentCreationResponseDTO> createTrackerAssignment(@PathVariable("trackerPolicyId") Long trackerPolicyId,
                                                                                        @RequestBody TrackerAssignmentCreationRequestDTO request){
        TrackerAssignmentCreationResponseDTO response = trackerPolicyService.addTrackerAssignment(trackerPolicyId, request);
        URI location = URI.create("/api/trackers/" + trackerPolicyId + "/assignments/" + response.trackerAssignmentId());

        return ResponseEntity.created(location).body(response);
    }

    //Get all assignments
    @GetMapping("/{trackerPolicyId}/assignments")
    public List<TrackerAssignmentGetResponseDTO> getAllTrackerAssignments(@PathVariable("trackerPolicyId") Long trackerPolicyId){
        return trackerPolicyService.findAllTrackerAssignmentsInPolicy(trackerPolicyId);
    }

    //Get assignment
    @GetMapping("/{trackerPolicyId}/assignments/{trackerAssignmentId}")
    public TrackerAssignmentGetResponseDTO getTrackerAssignmentById(@PathVariable("trackerPolicyId") Long trackerPolicyId,
                                                                    @PathVariable("trackerAssignmentId") Long trackerAssignmentId){
        return trackerPolicyService.findTrackerAssignmentById(trackerAssignmentId, trackerPolicyId);
    }

    //Unassign a policy to a trackable
    @DeleteMapping("/{trackerPolicyId}/assignments/{trackerAssignmentId}")
    public ResponseEntity<Void> deleteTrackerAssignmentById(@PathVariable("trackerPolicyId") Long trackerPolicyId,
                                                            @PathVariable("trackerAssignmentId") Long trackerAssignmentId){
        trackerPolicyService.deleteTrackerAssignmentById(trackerAssignmentId, trackerPolicyId);
        return ResponseEntity.noContent().build();
    }

    /// TRACKER EVENT
    //Record a tracker event
    @PostMapping("/{trackerPolicyId}/assignments/{trackerAssignmentId}/events")
    public TrackerEventCreationResponseDTO createTrackerEvent(@PathVariable("trackerPolicyId") Long trackerPolicyId,
                                                              @PathVariable("trackerAssignmentId") Long trackerAssignmentId,
                                                              @RequestBody TrackerEventCreationRequestDTO request){
        TrackerEventCreationResponseDTO response = trackerPolicyService.addTrackerEvent(trackerAssignmentId, trackerPolicyId, request);
        URI location = URI.create("/api/trackers/" + trackerPolicyId + "/assignments/" + trackerAssignmentId + "/events/" + response.trackerEventId());
        return ResponseEntity.created(location).body(response);
    }

    //Get all events
    @GetMapping("/{trackerPolicyId}/assignments/{trackerAssignmentId}/events")
    public List<TrackerEventGetResponseDTO> getAllTrackerEvents(@PathVariable("trackerPolicyId") Long trackerPolicyId,
                                                                @PathVariable("trackerAssignmentId") Long trackerAssignmentId){
        return trackerPolicyService.findallTrackerEventsInAssignment(trackerAssignmentId, trackerPolicyId);
    }

    //Get event
    @GetMapping("/{trackerPolicyId}/assignments/{trackerAssignmentId}/events/{trackerEventId}")
    public TrackerEventGetResponseDTO getTrackerEventById(@PathVariable("trackerPolicyId") Long trackerPolicyId,
                                                          @PathVariable("trackerAssignmentId") Long trackerAssignmentId,
                                                          @PathVariable("trackerEventId") Long trackerEventId){
        return trackerPolicyService.findTrackerEventById(trackerEventId, trackerAssignmentId, trackerPolicyId);
    }

    //Get most recent event for a tracker assignment
    @GetMapping("/{trackerPolicyId}/assignments/{trackerAssignmentId}/events/latest")
    public TrackerEventGetResponseDTO getMostRecentTrackerEvent(@PathVariable("trackerPolicyId") Long trackerPolicyId,
                                                          @PathVariable("trackerAssignmentId") Long trackerAssignmentId){
        return trackerPolicyService.findMostRecentTrackerEvent(trackerPolicyId, trackerAssignmentId);
    }

    //Delete tracker event from assignment
    @DeleteMapping("/{trackerPolicyId}/assignments/{trackerAssignmentId}/events/{trackerEventId}")
    public ResponseEntity<Void> deleteTrackerEventById(@PathVariable("trackerPolicyId") Long trackerPolicyId,
                                                       @PathVariable("trackerAssignmentId") Long trackerAssignmentId,
                                                       @PathVariable("trackerEventId") Long trackerEventId){
        trackerPolicyService.deleteTrackerEventById(trackerEventId, trackerAssignmentId, trackerPolicyId);
        return ResponseEntity.noContent().build();
    }


}
