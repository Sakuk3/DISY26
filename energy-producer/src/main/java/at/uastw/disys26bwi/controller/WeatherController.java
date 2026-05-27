package at.uastw.disys26bwi.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class WeatherController {
    private static final Logger logger = LoggerFactory.getLogger(WeatherController.class);
    private static final String OPEN_METEO_URL = "https://api.open-meteo.com/v1/forecast?latitude=48.23956&longitude=16.377493&minutely_15=direct_radiation&forecast_days=1&timezone=auto";
    private static final double PANEL_SIZE_M2 = 5.0;
    private static final double EFFICIENCY = 0.20;

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public WeatherController() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public double getSunlightIntensity() {
        try {
            JsonNode response = getJsonNode();

            //time-Array und direct_radiation-Array aus der Response holen
            JsonNode timeArray = response.get("minutely_15").get("time");
            JsonNode radiationArray = response.get("minutely_15").get("direct_radiation");

            //aktuelle Zeit um 15 Minuten runden z.B. 14:17 → "2026-05-27T14:15"
            LocalDateTime now = LocalDateTime.now();
            int roundedMinutes = (now.getMinute() / 15) * 15;
            LocalDateTime roundedTime = now.withMinute(roundedMinutes).withSecond(0);
            String targetTime = roundedTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));

            //Index im time-Array finden der zur aktuellen Zeit passt
            double directRadiation = 0.0;
            for (int i = 0; i < timeArray.size(); i++) {
                if (timeArray.get(i).asText().equals(targetTime)) {
                    directRadiation = radiationArray.get(i).asDouble();
                    break;
                }
            }

            //W/m² → kWh pro Minute umrechnen
            //kWh = (W/m²) / 1000 (=kw/m²) × Panelfläche × Wirkungsgrad × (1/60) (= kwH pro Minute)
            double kwh = (directRadiation / 1000.0) * PANEL_SIZE_M2 * EFFICIENCY * (1.0 / 60.0);

            logger.info("Direct radiation: {} W/m², calculated: {} kWh/min", directRadiation, kwh);
            return kwh;

        } catch (Exception e) {
            logger.warn("Weather API failed, falling back to random value: {}", e.getMessage());
            return Math.random() * 0.005;
        }
    }

    private JsonNode getJsonNode() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(OPEN_METEO_URL))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );
            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new WeatherControllerException(
                        "API request failed with status " + response.statusCode()
                                + ": " + response.body()
                );
            }
            return objectMapper.readTree(response.body());

        } catch (IOException e) {
            throw new WeatherControllerException("Could not connect to External Weather API", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WeatherControllerException("Weather API request was interrupted", e);
        }
    }

    public static class WeatherControllerException extends RuntimeException {
        public WeatherControllerException(String message) {
            super(message);
        }

        public WeatherControllerException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
