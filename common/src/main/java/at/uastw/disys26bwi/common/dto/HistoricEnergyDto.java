package at.uastw.disys26bwi.common.dto;


import java.time.LocalDateTime;

public record HistoricEnergyDto(LocalDateTime startDate, LocalDateTime endDate, double communityProduced,
                                double communityUsed, double gridUsed) {
}
