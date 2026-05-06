package at.uastw.disys26bwi.repository;

import at.uastw.disys26bwi.apiSpec.dto.CurrentEnergyDto;
import at.uastw.disys26bwi.apiSpec.dto.HistoricEnergyDto;

import java.time.LocalDateTime;

public interface EnergyRepository {

  CurrentEnergyDto getCurrentEnergy();

  HistoricEnergyDto getHistoricEnergy(LocalDateTime start, LocalDateTime end);
}
