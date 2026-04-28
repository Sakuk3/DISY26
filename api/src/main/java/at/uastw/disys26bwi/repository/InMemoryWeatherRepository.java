package at.uastw.disys26bwi.repository;

import at.uastw.disys26bwi.dto.WeatherDto;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Component
public class InMemoryWeatherRepository extends InMemoryRepository<WeatherDto, Integer> implements WeatherRepository {
  @Override
  protected Integer getId(WeatherDto entity) {
    return entity.id();
  }

  public List<WeatherDto> query(String city) {
    return this.queryAll().stream()
      .filter(weatherDto -> weatherDto.city().toLowerCase().equals(city.toLowerCase())).toList();
  }

  public void seed() {
    String[] cities = {"Vienna", "Graz", "Linz", "Salzburg", "Innsbruck"};
    LocalDate today = LocalDate.now();
    int id = 1;

    for (int dayOffset = 0; dayOffset < 7; dayOffset++) {
      LocalDate day = today.minusDays(dayOffset);
      Date date = Date.from(day.atStartOfDay(ZoneId.systemDefault()).toInstant());

      for (int cityIndex = 0; cityIndex < cities.length; cityIndex++) {
        float temperature = 10.0f + cityIndex * 1.5f - dayOffset * 0.8f;
        float participationMM = Math.max(0.0f, (cityIndex + dayOffset) % 5 * 1.2f);
        save(new WeatherDto(id++, date, temperature, participationMM, cities[cityIndex]));
      }
    }
  }
}
