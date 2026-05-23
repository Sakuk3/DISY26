package at.uastw.disys26bwi.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class WeatherController {
  private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

  public double getSunlightIntensity() {
    // TODO: Implement actual weather API call to get real sunlight intensity
    // Simulate sunlight intensity between 0 and 1000 W/m²
    double sunlightIntensity = Math.random() * 1000;
    logger.info("Current sunlight intensity: {} W/m²", sunlightIntensity);
    return sunlightIntensity;
  }
}
