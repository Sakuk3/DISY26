package at.uastw.disys26bwi.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

public class EnergyQueryValidator {
  /**
   * Validates the time range for energy queries
   *
   * @param start the start time
   * @param end   the end time
   * @throws ResponseStatusException if validation fails
   */
  public static void validateTimeRange(LocalDateTime start, LocalDateTime end) {
    if (start.isAfter(end) || start.isEqual(end)) {
      throw new ResponseStatusException(
        HttpStatus.BAD_REQUEST,
        "Start time must be before end time"
      );
    }

    if (end.isAfter(LocalDateTime.now())) {
      throw new ResponseStatusException(
        HttpStatus.BAD_REQUEST,
        "End time cannot be in the future"
      );
    }
  }
}

