package at.uastw.disys26bwi.controller;

import at.uastw.disys26bwi.common.dto.CurrentEnergyDto;
import at.uastw.disys26bwi.common.dto.HistoricEnergyDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;

@RestController
@RequestMapping("/energy")
public class EnergyController {

  private static final Logger logger = LoggerFactory.getLogger(EnergyController.class);

  @GetMapping("/current")
  public CurrentEnergyDto getCurrentEnergy() {
    logger.info("Fetching current energy data");
    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Not implemented");
  }

  @GetMapping("/historic")
  public HistoricEnergyDto getHistoricEnergy(
      @RequestParam Date start,
      @RequestParam Date end
  ) {
    logger.info("Fetching historic energy data from {} to {}", start, end);
    throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "Not implemented");
  }
}



