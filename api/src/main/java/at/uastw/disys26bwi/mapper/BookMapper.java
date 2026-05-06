package at.uastw.disys26bwi.mapper;

import at.uastw.disys26bwi.apiSpec.dto.BookDto;
import at.uastw.disys26bwi.repository.BookEntity;
import org.springframework.stereotype.Service;

@Service
public class BookMapper extends AbstractMapper<BookEntity, BookDto> {

  @Override
  public BookDto map(BookEntity source) {
    if (source == null) {
      return null;
    }
    return new BookDto(source.getId(), source.getTitle(), source.getGenre());
  }

  @Override
  public BookEntity mapReverse(BookDto target) {
    if (target == null) {
      return null;
    }
    BookEntity entity = new BookEntity();
    if (target.id() != null) {
      entity.setId(target.id());
    }
    entity.setTitle(target.title());
    entity.setGenre(target.genre());
    return entity;
  }
}
