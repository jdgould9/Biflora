package net.jdgould.spring_garden.dto.tracker.trackable;

import net.jdgould.spring_garden.model.tracker.TrackableType;

public record TrackableHierarchyDTO(Long id,
                                    String name,
                                    TrackableType type,
                                    Long parentId,
                                    String parentName,
                                    TrackableType parentType,
                                    Long grandParentId,
                                    String grandParentName,
                                    TrackableType grandParentType) {
}
