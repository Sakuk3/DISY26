package at.uastw.disys26bwi.dto;

import java.util.Date;

public record WeatherDto(int id, Date date, float temperature, float participationMM, String city) {
}
