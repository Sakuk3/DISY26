package at.uastw.disys26bwi.ui;

import at.uastw.disys26bwi.apiSpec.dto.CurrentEnergyDto;
import at.uastw.disys26bwi.apiSpec.dto.HistoricEnergyDto;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public class EnergyService {
  private static final String API_BASE_URL = "http://localhost:8080";

  private final HttpClient httpClient;
  private final ObjectMapper objectMapper;

  public EnergyService() {
    this.httpClient = HttpClient.newHttpClient();
    this.objectMapper = new ObjectMapper();
  }

  public CurrentEnergyDto getCurrentEnergy() {
    JsonNode json = getJson("/energy/current");

    return new CurrentEnergyDto(
      LocalDateTime.parse(json.get("date").asText()),
      json.get("communityDepleted").asDouble(),
      json.get("gridPortion").asDouble()
    );
  }

  public HistoricEnergyDto getHistoricEnergy(LocalDateTime start, LocalDateTime end) {
    String startParam = URLEncoder.encode(start.toString(), StandardCharsets.UTF_8);
    String endParam = URLEncoder.encode(end.toString(), StandardCharsets.UTF_8);

      JsonNode json = getJson("/energy/historical?start=" + startParam + "&end=" + endParam);

    return new HistoricEnergyDto(
      LocalDateTime.parse(json.get("startDate").asText()),
      LocalDateTime.parse(json.get("endDate").asText()),
      json.get("communityProduced").asDouble(),
      json.get("communityUsed").asDouble(),
      json.get("gridUsed").asDouble()
    );
  }

  private JsonNode getJson(String path) {
    try {
      HttpRequest request = HttpRequest.newBuilder()
        .uri(URI.create(API_BASE_URL + path))
        .header("Accept", "application/json")
        .GET()
        .build();

      HttpResponse<String> response = httpClient.send(
        request,
        HttpResponse.BodyHandlers.ofString()
      );

      if (response.statusCode() < 200 || response.statusCode() >= 300) {
        throw new EnergyServiceException(
          "API request failed with status " + response.statusCode()
            + ": " + response.body()
        );
      }

      return objectMapper.readTree(response.body());

    } catch (IOException e) {
      throw new EnergyServiceException("Could not connect to REST API", e);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new EnergyServiceException("REST API request was interrupted", e);
    }
  }
}