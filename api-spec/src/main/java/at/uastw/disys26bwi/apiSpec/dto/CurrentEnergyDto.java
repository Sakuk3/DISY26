package at.uastw.disys26bwi.apiSpec.dto;

import java.time.LocalDateTime;

public record CurrentEnergyDto(LocalDateTime date, double communityDepleted, double gridPortion) {
}

