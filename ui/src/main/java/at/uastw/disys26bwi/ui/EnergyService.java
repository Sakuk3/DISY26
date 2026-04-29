package at.uastw.disys26bwi.ui;

import at.uastw.disys26bwi.common.dto.CurrentEnergyDto;
import at.uastw.disys26bwi.common.dto.HistoricEnergyDto;

import java.time.LocalDateTime;
import java.util.Random;

public class EnergyService {
  private final Random random = new Random();

  public CurrentEnergyDto getCurrentEnergy() {
    return new CurrentEnergyDto(
      LocalDateTime.now(),
      random.nextDouble(),
      random.nextDouble()
    );
  }

  public HistoricEnergyDto getHistoricEnergy(LocalDateTime start, LocalDateTime end) {
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
