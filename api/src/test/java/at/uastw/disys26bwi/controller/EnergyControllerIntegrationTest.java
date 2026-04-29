package at.uastw.disys26bwi.controller;

import at.uastw.disys26bwi.common.dto.CurrentEnergyDto;
import at.uastw.disys26bwi.common.dto.HistoricEnergyDto;
import at.uastw.disys26bwi.repository.EnergyRepository;
import org.junit.jupiter.api.Test;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EnergyControllerIntegrationTest {

  @Test
  void getCurrentEnergyReturnsExpectedResponseData() {
    CurrentEnergyDto expected = new CurrentEnergyDto(LocalDateTime.of(2026, 4, 1, 12, 0), 0.4, 0.6);
    EnergyController controller = new EnergyController(new StubEnergyRepository(expected, null));

    CurrentEnergyDto response = controller.getCurrentEnergy();

    assertEquals(expected, response);
  }

  @Test
  void getHistoricEnergyReturnsExpectedResponseData() {
    LocalDateTime start = LocalDateTime.of(2026, 4, 1, 0, 0);
    LocalDateTime end = LocalDateTime.of(2026, 4, 2, 0, 0);
    HistoricEnergyDto expected = new HistoricEnergyDto(start, end, 5000.0, 3000.0, 2000.0);
    EnergyController controller = new EnergyController(new StubEnergyRepository(null, expected));

    HistoricEnergyDto response = controller.getHistoricEnergy(start, end);

    assertEquals(expected, response);
  }

  @Test
  void getHistoricEnergyReturnsBadRequestWhenStartIsNotBeforeEnd() {
    LocalDateTime timestamp = LocalDateTime.of(2026, 4, 1, 12, 0);
    EnergyController controller = new EnergyController(new StubEnergyRepository(null, null));

    ResponseStatusException exception = assertThrows(ResponseStatusException.class,
      () -> controller.getHistoricEnergy(timestamp, timestamp));

    assertEquals("Start time must be before end time", exception.getReason());
  }

  @Test
  void getHistoricEnergyReturnsBadRequestWhenEndIsInFuture() {
    LocalDateTime start = LocalDateTime.now().minusDays(1).withNano(0);
    LocalDateTime end = LocalDateTime.now().plusDays(1).withNano(0);
    EnergyController controller = new EnergyController(new StubEnergyRepository(null, null));

    ResponseStatusException exception = assertThrows(ResponseStatusException.class,
      () -> controller.getHistoricEnergy(start, end));

    assertEquals("End time cannot be in the future", exception.getReason());
  }

  private static void assertPercentage(double value) {
    assertTrue(value >= 0.0 && value <= 1.0,
      () -> "Expected " + value + " to be a percentage between 0.0 and 1.0");
  }

  private record StubEnergyRepository(
    CurrentEnergyDto currentEnergyDto,
    HistoricEnergyDto historicEnergyDto
  ) implements EnergyRepository {

    @Override
    public CurrentEnergyDto getCurrentEnergy() {
      return currentEnergyDto;
    }

    @Override
    public HistoricEnergyDto getHistoricEnergy(LocalDateTime start, LocalDateTime end) {
      return historicEnergyDto;
    }
  }
}
