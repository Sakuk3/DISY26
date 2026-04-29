package at.uastw.disys26bwi.repository;

import at.uastw.disys26bwi.common.dto.CurrentEnergyDto;
import at.uastw.disys26bwi.common.dto.HistoricEnergyDto;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class InMemoryEnergyRepository implements EnergyRepository {
  private static final Logger logger = LoggerFactory.getLogger(InMemoryEnergyRepository.class);
  private final Random random;

  InMemoryEnergyRepository() {
    this.random = new Random();
  }

  InMemoryEnergyRepository(Random random) {
    this.random = random;
  }

  public CurrentEnergyDto getCurrentEnergy() {
   logger.debug("Generating current energy data");
    return new CurrentEnergyDto(
      LocalDateTime.now(),
      this.round(random.nextDouble()),
      this.round(random.nextDouble())
    );
  }

  public HistoricEnergyDto getHistoricEnergy(LocalDateTime start, LocalDateTime  end) {
    logger.debug("Generating historic energy data from {} to {}", start, end);
    double communityUsed = this.round(1000 + random.nextDouble() * 9000);
    double communityProduced = this.round(random.nextDouble() * 2 * communityUsed);
    double gridUsed = this.round(Math.max(0, communityUsed  - communityProduced));

    return new HistoricEnergyDto(
      start,
      end,
      communityProduced,
      communityUsed,
      gridUsed
    );
  }


  private double round(double value) {
    double scale = Math.pow(10, 2);
    return Math.round(value * scale) / scale;
  }
}
