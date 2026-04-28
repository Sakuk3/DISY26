package at.uastw.disys26bwi.controller;

import at.uastw.disys26bwi.dto.WeatherDto;
import at.uastw.disys26bwi.repository.WeatherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@RestController
@RequestMapping("/weather")
public class WeatherController {

  private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);

  private final WeatherRepository weatherRepository;

  WeatherController(WeatherRepository weatherRepository) {
    this.weatherRepository = weatherRepository;
    logger.info("Seeding initial weather data");
    this.weatherRepository.seed();
    logger.info("Initial book weather seeded");
  }

  //  curl -s http://localhost:8080/weather?city=vienna | jq
  @GetMapping
  public List<WeatherDto> getWeather(@RequestParam(required = false) String city) {
    logger.info("Fetching all weather");
    if (city != null) {
      return this.weatherRepository.query(city);
    } else {
      return this.weatherRepository.queryAll();
    }
  }
  
   //  curl -s http://localhost:8080/weather/current?city=vienna | jq
  @GetMapping("/current")
  public WeatherDto getCurrentWeather(@RequestParam String city) {
    logger.info("Fetching current weather for city={}", city);
    LocalDate today = LocalDate.now();

    return this.weatherRepository.query(city).stream()
      .filter(weatherDto -> weatherDto.date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isEqual(today))
      .findFirst()
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

  //  curl -s http://localhost:8080/weather/2 | jq
  @GetMapping("/{id}")
  public WeatherDto getWeatherById(@PathVariable int id) {
    logger.info("Fetching weather with id={}", id);
    return this.weatherRepository.findById(id)
      .orElseThrow(() -> {
        logger.warn("Weather with id={} not found", id);
        return new ResponseStatusException(HttpStatus.NOT_FOUND);
      });
  }


  //  curl -X POST "http://localhost:8080/weather" \
  //    -H "Content-Type: application/json" \
  //    -d '{"id":36,"date":"2026-04-08T00:00:00Z","temperature":14.5,"participationMM":1.2,"city":"Vienna"}'
  @PostMapping
  public WeatherDto addWeather(@RequestBody WeatherDto weather) {
    logger.info("Adding observation with id={}", weather.id());
    return this.weatherRepository.save(weather);
  }
}
