package org.example.disysfx;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class BookService {
  private static final String URL = "http://localhost:8080/books";
  private final ObjectMapper objectMapper = new ObjectMapper();

  public List<BookDto> getBooks(String title, String order) {
    try (HttpClient client = HttpClient.newBuilder().build()) {
      String titleEncoded = URLEncoder.encode(title, StandardCharsets.UTF_8);
      String requestUrl = URL + "?title=" + titleEncoded + "&order=" + order;
      HttpRequest getRequest = HttpRequest.newBuilder()
        .uri(URI.create(requestUrl))
        .GET()
        .build();

      HttpResponse<String> response = client.send(getRequest,
        HttpResponse.BodyHandlers.ofString());
      return  objectMapper.readValue(response.body(), new TypeReference<List<BookDto>>() {});
    } catch (IOException | InterruptedException e) {
      System.out.println("Something went wrong: " + e.getMessage());
    }
    return null;
  }
}
