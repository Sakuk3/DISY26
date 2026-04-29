package at.uastw.disys26bwi.repository;

import at.uastw.disys26bwi.common.dto.CurrentEnergyDto;
import at.uastw.disys26bwi.common.dto.HistoricEnergyDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InMemoryEnergyRepositoryTest {
  private static final long CURRENT_ENERGY_SEED = 12345L;
  private static final long HISTORIC_ENERGY_SEED = 98765L;
  private static final int HISTORIC_RANGE_COUNT = 10;

  @Test
  void getCurrentEnergyReturnsValuesInExpectedRange() {
    InMemoryEnergyRepository repository = new InMemoryEnergyRepository(new Random(CURRENT_ENERGY_SEED));

    CurrentEnergyDto dto = repository.getCurrentEnergy();

    assertNotNull(dto.date());
    assertPercentage(dto.communityDepleted());
    assertPercentage(dto.gridPortion());
  }

  @Test
  void getHistoricEnergyReturnsValuesInExpectedRange() {
    InMemoryEnergyRepository repository = new InMemoryEnergyRepository(new Random(HISTORIC_ENERGY_SEED));
    LocalDateTime base = LocalDateTime.of(2026, 4, 1, 0, 0);

    for (int i = 0; i < HISTORIC_RANGE_COUNT; i++) {
      LocalDateTime start = base.plusDays(i);
      LocalDateTime end = start.plusDays(1);

      HistoricEnergyDto dto = repository.getHistoricEnergy(start, end);

      assertHistoricEnergy(dto, start, end);
    }
  }

  private static void assertHistoricEnergy(HistoricEnergyDto dto, LocalDateTime start, LocalDateTime end) {
    assertEquals(start, dto.startDate());
    assertEquals(end, dto.endDate());
    assertTrue(dto.communityUsed() >= 0, "Community usage must be non-negative");
    assertTrue(dto.communityProduced() >= 0, "Community production must be non-negative");
    assertTrue(dto.gridUsed() >= 0, "Grid usage must be non-negative");
    assertEquals(Math.max(0, dto.communityUsed() - dto.communityProduced()), dto.gridUsed(), 0.01,
      "Grid usage must equal the community deficit");
  }

  private static void assertPercentage(double value) {
    assertTrue(value >= 0.0 && value <= 1.0,
      () -> "Expected " + value + " to be a percentage between 0.0 and 1.0");
  }
}
