package net.jdgould.spring_garden.model.tracker;

import jakarta.persistence.*;

//Tracker assignment is the link between a tracker policy and a garden/gardenzone/plant
import java.time.LocalDateTime;
@Entity
public class TrackerAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long trackerAssignmentId;
    @ManyToOne
    @Enumerated(EnumType.STRING)
    private TrackerTargetType trackerTargetType;
    private Long targetId;
    private LocalDateTime startDate;


}
