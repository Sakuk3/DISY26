package at.uastw.disys26bwi.controller;

import at.uastw.disys26bwi.common.dto.CurrentEnergyDto;
import at.uastw.disys26bwi.common.dto.HistoricEnergyDto;
import at.uastw.disys26bwi.repository.EnergyRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/energy")
public class EnergyController {

  private static final Logger logger = LoggerFactory.getLogger(EnergyController.class);
  private final EnergyRepository energyRepository;

  EnergyController(EnergyRepository energyRepository) {
    this.energyRepository = energyRepository;
    logger.info("EnergyController initialized");
  }

  @GetMapping("/current")
  public CurrentEnergyDto getCurrentEnergy() {
    logger.debug("Fetching current energy data");
    return this.energyRepository.getCurrentEnergy();
  }

  @GetMapping("/historic")
  public HistoricEnergyDto getHistoricEnergy(
      @RequestParam("start") LocalDateTime start,
      @RequestParam("end") LocalDateTime  end
  ) {
    logger.debug("Fetching historic energy data from {} to {}", start, end);
    if (start.isAfter(end)) {
      logger.warn("Invalid time range: start {} is after end {}", start, end);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start time must be before end time");
    } else if (start.isEqual(end)) {
      logger.warn("Invalid time range: start {} is equal to end {}", start, end);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start time must be before end time");
    } else if (end.isAfter(LocalDateTime.now())) {
      logger.warn("Invalid time range: end {} is in the future", end);
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "End time cannot be in the future");
    } else {
      return this.energyRepository.getHistoricEnergy(start, end);
    }
  }
}



