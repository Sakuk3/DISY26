package at.uastw.disys26bwi.common.dto;

import java.util.Date;

public record HistoricEnergyDto(Date startDate, Date endDate, double communityProduced, double communityUsed, double gridUsed) {}
