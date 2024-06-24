package com.sbz.appa.core.mapper.impl;

import com.sbz.appa.application.dto.GuideDto;
import com.sbz.appa.commons.Checkpoint;
import com.sbz.appa.commons.Nation;
import com.sbz.appa.core.mapper.Mapper;
import com.sbz.appa.infrastructure.persistence.entity.GuideEntity;
import org.springframework.stereotype.Component;

@Component
public class GuideEntityToGuideDto implements Mapper<GuideEntity, GuideDto> {
    @Override
    public GuideDto mapToDto(GuideEntity guideEntity) {
        return GuideDto.builder()
                .id(guideEntity.getId())
                .currentNation(guideEntity.getCurrentNation().name())
                .currentCheckpoint(guideEntity.getCurrentCheckpoint().name())
                .build();
    }

    @Override
    public GuideEntity mapFromDto(GuideDto guideDto) {
        return GuideEntity.builder()
                .id(guideDto.getId())
                .currentNation(Nation.valueOf(guideDto.getCurrentNation()))
                .currentCheckpoint(Checkpoint.valueOf(guideDto.getCurrentCheckpoint()))
                .build();
    }
}
