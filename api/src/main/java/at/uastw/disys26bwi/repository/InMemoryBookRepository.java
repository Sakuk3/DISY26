package at.uastw.disys26bwi.repository;

import at.uastw.disys26bwi.dto.BookDto;
import org.springframework.stereotype.Component;

@Component
public class InMemoryBookRepository extends InMemoryRepository<BookDto, Integer> implements BookRepository {
  @Override
  protected Integer getId(BookDto entity) {
    return entity.id();
  }

  public void seed() {
    save(new BookDto(1, "The Lord of the Rings", "Fantasy"));
    save(new BookDto(2, "The Hobbit", "Fantasy"));
    save(new BookDto(3, "The Catcher in the Rye", "Fiction"));
    save(new BookDto(4, "To Kill a Mockingbird", "Fiction"));
  }
}
