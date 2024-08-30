package com.sbz.appa.util;

import com.sbz.appa.application.dto.GuideDto;

import java.util.UUID;

public class GuideDtoTestData {

    public static GuideDto createTestGuideDto() {
        return GuideDto.builder()
                .id(UUID.randomUUID())
                .currentNation("WATER")
                .currentCheckpoint("SOUTHERN_WATER")
                .build();
    }
}
