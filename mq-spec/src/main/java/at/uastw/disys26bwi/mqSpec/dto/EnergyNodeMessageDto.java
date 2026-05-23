package at.uastw.disys26bwi.mqSpec.dto;

import at.uastw.disys26bwi.mqSpec.constants.Association;
import at.uastw.disys26bwi.mqSpec.constants.NodeType;

import java.io.Serializable;

public record EnergyNodeMessageDto(
  NodeType type,
  Association association,
  double kwh,
  String datetime
) implements Serializable {
}
