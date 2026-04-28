package at.uastw.disys26bwi.controller;

import at.uastw.disys26bwi.dto.BookDto;
import at.uastw.disys26bwi.repository.BookRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

  private static final Logger logger = LoggerFactory.getLogger(BookController.class);
  private static final Comparator<BookDto> TITLE_COMPARATOR = Comparator.comparing(
    BookDto::title,
    Comparator.nullsLast(String.CASE_INSENSITIVE_ORDER)
  );

  private final BookRepository bookRepository;

  BookController(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
    logger.info("Seeding initial book data");
    this.bookRepository.seed();
    logger.info("Initial book data seeded");
  }

  //  curl -s http://localhost:8080/books | jq
  @GetMapping
  public List<BookDto> getBooks(
    @RequestParam(required = false) String title,
    @RequestParam(required = false) BookOrder order
  ) {
    logger.info("Fetching all books");
    List<BookDto> allBooks = this.bookRepository.queryAll();
    Comparator<BookDto> titleComparator = order == BookOrder.DESC
      ? TITLE_COMPARATOR.reversed()
      : TITLE_COMPARATOR;

    return allBooks.stream()
      .filter(b -> title == null || b.title().toLowerCase().contains(title.toLowerCase()))
      .sorted(titleComparator)
      .toList();
  }

  private String normalize(String value) {
    return value == null ? "" : value.trim();
  }

  //  curl -s http://localhost:8080/books/2 | jq
  @GetMapping("/{id}")
  public BookDto getBookById(@PathVariable int id) {
    logger.info("Fetching book with id={}", id);
    return this.bookRepository.findById(id)
      .orElseThrow(() -> {
        logger.warn("Book with id={} not found", id);
        return new ResponseStatusException(HttpStatus.NOT_FOUND);
      });
  }


  //  curl -X POST "http://localhost:8080/books" \
  //    -H "Content-Type: application/json" \
  //    -d '{"id":5,"title":"The Hobbit","genre":"Fantasy"}'
  @PostMapping
  public BookDto addBook(@RequestBody BookDto book) {
    logger.info("Adding book with id={}", book.id());
    return this.bookRepository.save(book);
  }

  //  curl -X DELETE "http://localhost:8080/books?id=5"
  @DeleteMapping("/{id}")
  public void deleteBook(@PathVariable int id) {
    logger.info("Deleting book with id={}", id);
    if (!this.bookRepository.delete(id)) {
      logger.warn("Book with id={} not found for deletion", id);
      throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
  }

  //  curl -X PUT "http://localhost:8080/books" \
  //    -H "Content-Type: application/json" \
  //    -d '{"id":5,"title":"The Hobbit: An Unexpected Journey","genre":"Fantasy"}'
  @PutMapping("/{id}")
  public BookDto updateBook(@PathVariable int id, @RequestBody BookDto book) {
    logger.info("Updating book with id={}", id);
    return this.bookRepository.update(id, book);
  }
}
