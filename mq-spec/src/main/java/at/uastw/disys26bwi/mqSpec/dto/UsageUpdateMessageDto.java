package at.uastw.disys26bwi.mqSpec.dto;

import at.uastw.disys26bwi.mqSpec.constants.Association;

import java.io.Serializable;
import java.math.BigDecimal;

public record UsageUpdateMessageDto(
  Association association,
  String hourBucket,
  BigDecimal communityProducedKwh,
  BigDecimal communityUsedKwh,
  BigDecimal gridUsedKwh

) implements Serializable {
}
