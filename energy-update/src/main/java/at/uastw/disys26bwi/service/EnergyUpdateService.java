package at.uastw.disys26bwi.service;

import at.uastw.disys26bwi.entity.AssociationEntity;
import at.uastw.disys26bwi.entity.HourlyPercentagesEntity;
import at.uastw.disys26bwi.mqSpec.constants.Association;
import at.uastw.disys26bwi.mqSpec.constants.QueueNames;
import at.uastw.disys26bwi.mqSpec.dto.UsageUpdateMessageDto;
import at.uastw.disys26bwi.repository.AssociationRepository;
import at.uastw.disys26bwi.repository.HourlyPercentagesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
public class EnergyUpdateService {
  private static final Logger logger = LoggerFactory.getLogger(EnergyUpdateService.class);

  private final HourlyPercentagesRepository hourlyPercentagesRepository;
  private final AssociationRepository associationRepository;

  EnergyUpdateService(HourlyPercentagesRepository hourlyPercentagesRepository, AssociationRepository associationRepository) {
    this.hourlyPercentagesRepository = hourlyPercentagesRepository;
    this.associationRepository = associationRepository;
  }

  @RabbitListener(queues = QueueNames.USAGE_UPDATE_QUEUE)
  public void receiveMessage(UsageUpdateMessageDto message) {
    logger.info("Received usage update: {}", message);

    final OffsetDateTime hourBucket = Instant.parse(message.hourBucket())
      .atOffset(ZoneOffset.UTC)
      .withMinute(0)
      .withSecond(0)
      .withNano(0);

    final BigDecimal communityDepletedPct = message.communityProducedKwh().equals(message.communityUsedKwh())
      ? new BigDecimal("100")
      : message.communityUsedKwh().divide(message.communityProducedKwh(), RoundingMode.HALF_UP).multiply(new BigDecimal("100"));
    final BigDecimal gridPortionPct = message.gridUsedKwh().divide(message.gridUsedKwh().add(message.communityUsedKwh()), RoundingMode.HALF_UP).multiply(new BigDecimal("100"));
    final HourlyPercentagesEntity entity = new HourlyPercentagesEntity();
    entity.setAssociationId(this.getOrCreateAssociation(message.association()).getId());
    entity.setHourBucket(hourBucket);
    entity.setSelfConsumptionPct(communityDepletedPct);
    entity.setGridDependencyPct(gridPortionPct);
    entity.setUpdatedAt(OffsetDateTime.now());
    this.hourlyPercentagesRepository.save(entity);
  }

  private AssociationEntity getOrCreateAssociation(Association association) {
    AssociationEntity entity = associationRepository.findByCode(association.name());
    if (entity == null) {
      entity = new AssociationEntity();
      entity.setCode(association.name());
      associationRepository.save(entity);
    }
    return entity;
  }
}
