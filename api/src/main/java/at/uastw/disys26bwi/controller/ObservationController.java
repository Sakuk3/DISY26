package at.uastw.disys26bwi.controller;

import at.uastw.disys26bwi.dto.ObservationDto;
import at.uastw.disys26bwi.repository.ObservationRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/observations")
public class ObservationController {

  private static final Logger logger = LoggerFactory.getLogger(ObservationController.class);

  private final ObservationRepository observationRepository;

  ObservationController(ObservationRepository observationRepository) {
    this.observationRepository = observationRepository;
    logger.info("Seeding initial observation data");
    this.observationRepository.seed();
    logger.info("Initial book observation  seeded");
  }

  //  curl -s http://localhost:8080/observations | jq
  @GetMapping
  public List<ObservationDto> getBooks(@RequestParam(required = false) LocalDate from, @RequestParam(required = false) LocalDate to) {
    logger.info("Fetching all observatiosn");
    return this.observationRepository.queryAll();
  }

  //  curl -s http://localhost:8080/observations/2 | jq
  @GetMapping("/{id}")
  public ObservationDto getBookById(@PathVariable int id) {
    logger.info("Fetching Observation  with id={}", id);
    return this.observationRepository.findById(id)
      .orElseThrow(() -> {
        logger.warn("Observation with id={} not found", id);
        return new ResponseStatusException(HttpStatus.NOT_FOUND);
      });
  }


  //  curl -X POST "http://localhost:8080/observations " \
  //    -H "Content-Type: application/json" \
  //    -d '{"id":5,"title":"The Hobbit","genre":"Fantasy"}'
  @PostMapping
  public ObservationDto addBook(@RequestBody ObservationDto observation) {
    logger.info("Adding observation with id={}", observation.id());
    return this.observationRepository.save(observation);
  }

  //  curl -X DELETE "http://localhost:8080/observations/5"
  @DeleteMapping("/{id}")
  public void deleteBook(@PathVariable int id) {
    logger.info("Deleting observation with id={}", id);
    if (!this.observationRepository.delete(id)) {
      logger.warn("Observation with id={} not found for deletion", id);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
  }

  //  curl -X PUT "http://localhost:8080/observations" \
  //    -H "Content-Type: application/json" \
  //    -d '{"id":5,"title":"The Hobbit: An Unexpected Journey","genre":"Fantasy"}'
  @PutMapping("/{id}")
  public ObservationDto updateBook(@PathVariable int id, @RequestBody ObservationDto observationDto) {
    logger.info("Updating observation with id={}", id);
    return this.observationRepository.update(id, observationDto);
  }

}
