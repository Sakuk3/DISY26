package at.uastw.disys26bwi.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<BookEntity, Integer> {
  List<BookEntity> findBookEntitiesByTitleContainingIgnoreCase(String title, Sort sort);
}
