package at.uastw.disys26bwi.repository;

import at.uastw.disys26bwi.common.dto.CurrentEnergyDto;
import at.uastw.disys26bwi.common.dto.HistoricEnergyDto;

import java.util.Date;

public interface EnergyRepository {

  CurrentEnergyDto getCurrentEnergy();

  HistoricEnergyDto getHistoricEnergy(Date start, Date end);
}


