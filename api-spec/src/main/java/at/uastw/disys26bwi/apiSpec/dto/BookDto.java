package at.uastw.disys26bwi.apiSpec.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BookDto(
  @JsonProperty(access = JsonProperty.Access.READ_ONLY) Integer id,
  String title,
  String genre) {
}