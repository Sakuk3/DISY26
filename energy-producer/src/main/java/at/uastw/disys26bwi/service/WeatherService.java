package at.uastw.disys26bwi.service;

import at.uastw.disys26bwi.dto.OpenMeteoResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

import at.uastw.disys26bwi.exception.WeatherServiceException;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class WeatherService {
    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    @Value("${weatherapi.latitude}")
    private String latitude;

    @Value("${weatherapi.longitude}")
    private String longitude;

    @Value("${weatherapi.timezone}")
    private String timezone;

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public WeatherService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    private String buildUrl() {
        return "https://api.open-meteo.com/v1/forecast?latitude=" + latitude
                + "&longitude=" + longitude
                + "&minutely_15=direct_radiation&forecast_days=1&timezone=" + timezone;
    }


    public double getSunlightIntensity() {
        try {
            OpenMeteoResponse response = getWeatherResponse();

            List<String> times = response.minutely15().time();
            List<Double> radiations = response.minutely15().directRadiation();

            // Aktuelle Zeit auf 15 Minuten abrunden
            LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
            int roundedMinutes = (now.getMinute() / 15) * 15;
            LocalDateTime roundedTime = now.withMinute(roundedMinutes).withSecond(0);
            String targetTime = roundedTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm"));

            // Index im time-Array finden der zur aktuellen Zeit passt
            for (int i = 0; i < times.size(); i++) {
                if (times.get(i).equals(targetTime)) {
                    double radiation = radiations.get(i);
                    logger.info("Direct radiation: {} W/m²", radiation);
                    return radiation;
                }
            }

            logger.warn("No radiation data found for time: {}", targetTime);
            return 0.0;

        } catch (Exception e) {
            logger.warn("Weather API failed, falling back to 0", e);
            return 0.0;
        }
    }

    private OpenMeteoResponse getWeatherResponse() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(buildUrl()))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(
                    request,
                    HttpResponse.BodyHandlers.ofString()
            );

            if (response.statusCode() < 200 || response.statusCode() >= 300) {
                throw new WeatherServiceException(
                        "API request failed with status " + response.statusCode()
                                + ": " + response.body()
                );
            }

            return objectMapper.readValue(response.body(), OpenMeteoResponse.class);

        } catch (IOException e) {
            throw new WeatherServiceException("Could not connect to External Weather API", e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new WeatherServiceException("Weather API request was interrupted", e);
        }
    }
}
