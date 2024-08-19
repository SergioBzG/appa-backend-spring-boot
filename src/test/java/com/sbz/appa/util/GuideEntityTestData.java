package com.sbz.appa.util;

import com.sbz.appa.commons.Checkpoint;
import com.sbz.appa.commons.Nation;
import com.sbz.appa.infrastructure.persistence.entity.GuideEntity;

import java.util.UUID;

public class GuideEntityTestData {

    public static GuideEntity createTestGuideEntity() {
        return GuideEntity.builder()
                .id(UUID.randomUUID())
                .currentNation(Nation.WATER)
                .currentCheckpoint(Checkpoint.SOUTHERN_WATER)
                .build();
    }
}
