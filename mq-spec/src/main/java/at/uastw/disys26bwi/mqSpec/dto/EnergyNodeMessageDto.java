package at.uastw.disys26bwi.mqSpec.dto;

import at.uastw.disys26bwi.mqSpec.constants.Association;
import at.uastw.disys26bwi.mqSpec.constants.NodeType;

import java.io.Serializable;
import java.math.BigDecimal;

public record EnergyNodeMessageDto(
  NodeType type,
  Association association,
  BigDecimal kwh,
  String datetime
) implements Serializable {
}
