package at.uastw.disys26bwi.mqSpec.dto;

import at.uastw.disys26bwi.mqSpec.constants.Association;

import java.io.Serializable;

public record UsageUpdateMessageDto(
  Association association,
  String datetime
) implements Serializable {
}
