package at.uastw.disys26bwi.ui;

import at.uastw.disys26bwi.common.dto.CurrentEnergyDto;
import at.uastw.disys26bwi.common.dto.HistoricEnergyDto;

import java.util.Date;
import java.util.Random;

public class EnergyService {
  private final Random random = new Random();
  
  public CurrentEnergyDto getCurrentEnergy() {
    return new CurrentEnergyDto(
        new Date(),
        random.nextDouble(),
        random.nextDouble()
    );
  }
  
  public HistoricEnergyDto getHistoricEnergy(Date start, Date end) {
    double communityProduced = 1000 + random.nextDouble() * 9000;
    double gridUsed = random.nextDouble() * communityProduced;
    double communityUsed = Math.max(0, communityProduced - gridUsed);
    
    return new HistoricEnergyDto(
        start,
        end,
        communityProduced,
        communityUsed,
        gridUsed
    );
  }
}
