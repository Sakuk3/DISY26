package at.uastw.disys26bwi.repository;

import at.uastw.disys26bwi.dto.BookDto;
import at.uastw.disys26bwi.dto.ObservationDto;
import org.springframework.stereotype.Component;

@Component
public class InMemoryObservationRepository extends InMemoryRepository<ObservationDto, Integer> implements ObservationRepository {
  @Override
  protected Integer getId(ObservationDto entity) {
    return entity.id();
  }

  public void seed() {
  }
}
