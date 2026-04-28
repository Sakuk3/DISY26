package at.uastw.disys26bwi.common.dto;

import java.util.Date;

public record CurrentEnergyDto(Date date, double communityDepleted, double gridPortion) {}

