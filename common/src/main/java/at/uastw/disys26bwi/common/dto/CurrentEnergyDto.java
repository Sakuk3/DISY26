package at.uastw.disys26bwi.common.dto;

import java.time.LocalDateTime;

public record CurrentEnergyDto(LocalDateTime date, double communityDepleted, double gridPortion) {
}

