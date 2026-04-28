package at.uastw.disys26bwi.repository;

import at.uastw.disys26bwi.dto.WeatherDto;

import java.util.List;

public interface WeatherRepository extends Repository<WeatherDto, Integer> {

  List<WeatherDto> query(String city);
}
