package net.jdgould.spring_garden.repository;

import net.jdgould.spring_garden.dto.tracker.event.TrackerAssignmentGetResponseDTO;
import net.jdgould.spring_garden.model.tracker.TrackerAssignment;
import net.jdgould.spring_garden.model.tracker.TrackerPolicy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TrackerAssignmentRepository extends JpaRepository<TrackerAssignment, Long> {
    List<TrackerAssignment> findAllByTrackerPolicy(TrackerPolicy trackerPolicy);
    Optional<TrackerAssignment> findTrackerAssignmentByTrackerAssignmentIdAndTrackerPolicy(Long trackerAssignmentId, TrackerPolicy trackerPolicy);
}
