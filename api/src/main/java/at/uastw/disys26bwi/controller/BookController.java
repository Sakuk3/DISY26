package at.uastw.disys26bwi.controller;

import at.uastw.disys26bwi.apiSpec.dto.BookDto;
import at.uastw.disys26bwi.mapper.BookMapper;
import at.uastw.disys26bwi.repository.BookEntity;
import at.uastw.disys26bwi.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

  private static final Logger logger = LoggerFactory.getLogger(BookController.class);
  private static final Sort DEFAULT_SORT = Sort.by(Sort.Direction.ASC, "id");
  private final BookRepository bookRepository;
  private final BookMapper bookMapper = new BookMapper();

  BookController(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
    logger.debug("initialized");
  }

  @GetMapping("/")
  public List<BookDto> getAllBooks() {
    logger.debug("get all books");
    return this.bookMapper.mapList(this.bookRepository.findAll(DEFAULT_SORT));
  }

  @GetMapping(value = "/", params = "title")
  public List<BookDto> getAllBooks(@RequestParam String title) {
    logger.debug("get all books by title {}", title);
    if (title.isBlank()) {
      return this.bookMapper.mapList(this.bookRepository.findAll(DEFAULT_SORT));
    }
    return this.bookMapper.mapList(this.bookRepository.findBookEntitiesByTitleContainingIgnoreCase(title, DEFAULT_SORT));
  }

  @GetMapping("/{id}")
  public BookDto getBookById(@PathVariable int id) {
    logger.debug("get book by id {}", id);
    BookEntity book = this.bookRepository.findById(id)
      .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found"));
    return this.bookMapper.map(book);
  }

  @PostMapping("/")
  public BookDto createBook(@RequestBody BookDto book) {
    logger.debug("create book");
    BookEntity bookToCreate = this.bookMapper.mapReverse(book);
    bookToCreate.setId(0);
    return this.bookMapper.map(this.bookRepository.save(bookToCreate));
  }

  @PutMapping("/{id}")
  public BookDto updateBook(@PathVariable int id, @RequestBody BookDto book) {
    logger.debug("update book {}", id);
    Optional<BookEntity> existing = this.bookRepository.findById(id);
    if (existing.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
    }
    BookEntity bookToUpdate = this.bookMapper.mapReverse(book);
    bookToUpdate.setId(id);
    return this.bookMapper.map(this.bookRepository.save(bookToUpdate));
  }

  @DeleteMapping("/{id}")
  public void deleteBook(@PathVariable int id) {
    logger.debug("delete book {}", id);
    if (!this.bookRepository.existsById(id)) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Book not found");
    }
    this.bookRepository.deleteById(id);
  }
}
