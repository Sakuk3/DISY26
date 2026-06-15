package at.uastw.disys26bwi.controller;

import at.uastw.disys26bwi.apiSpec.dto.CurrentEnergyDto;
import at.uastw.disys26bwi.apiSpec.dto.HistoricEnergyDto;
import at.uastw.disys26bwi.repository.EnergyRepository;
import at.uastw.disys26bwi.util.EnergyQueryValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/energy")
public class EnergyController {

  private static final Logger logger = LoggerFactory.getLogger(EnergyController.class);
  private final EnergyRepository energyRepository;

  EnergyController(EnergyRepository energyRepository) {
    this.energyRepository = energyRepository;
    logger.debug("initialized");
  }

  @GetMapping("/current")
  public CurrentEnergyDto getCurrentEnergy() {
    logger.debug("Fetching current energy data");
    return this.energyRepository.getCurrentEnergy();
  }

    @GetMapping({"/historic", "/historical"})
  public HistoricEnergyDto getHistoricEnergy(
    @RequestParam("start") LocalDateTime start,
    @RequestParam("end") LocalDateTime end
  ) {
    logger.debug("Fetching historic energy data from {} to {}", start, end);
    EnergyQueryValidator.validateTimeRange(start, end);
    return this.energyRepository.getHistoricEnergy(start, end);
  }
}



