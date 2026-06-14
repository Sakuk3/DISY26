package at.uastw.disys26bwi.service;

import at.uastw.disys26bwi.entity.AssociationEntity;
import at.uastw.disys26bwi.entity.HourlyPercentagesEntity;
import at.uastw.disys26bwi.entity.HourlyPercentagesId;
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

        //Wie viel Prozent des Community-Pools wurden aufgebraucht
        final BigDecimal communityDepletedPct = message.communityProducedKwh().compareTo(BigDecimal.ZERO) == 0
                ? BigDecimal.ZERO
                : message.communityUsedKwh().compareTo(message.communityProducedKwh()) == 0
                ? new BigDecimal("100")
                : message.communityUsedKwh().divide(message.communityProducedKwh(), RoundingMode.HALF_UP).multiply(new BigDecimal("100"));


        final BigDecimal total = message.gridUsedKwh().add(message.communityUsedKwh());

        //Wie viel Prozent des Gesamtverbrauchs wurden vom Grid bereitgestellt
        final BigDecimal gridPortionPct = total.compareTo(BigDecimal.ZERO) == 0
                ? BigDecimal.ZERO
                : message.gridUsedKwh().divide(total, RoundingMode.HALF_UP).multiply(new BigDecimal("100"));

        final HourlyPercentagesEntity entity = getHourlyPercentages(message);
        entity.setSelfConsumptionPct(communityDepletedPct);
        entity.setGridDependencyPct(gridPortionPct);
        entity.setUpdatedAt(OffsetDateTime.now());
        this.hourlyPercentagesRepository.save(entity);
    }

    private HourlyPercentagesEntity getHourlyPercentages(UsageUpdateMessageDto message) {
        final AssociationEntity association = getOrCreateAssociation(message.association());
        final OffsetDateTime hourBucket = Instant.parse(message.hourBucket())
                .atOffset(ZoneOffset.UTC)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        final HourlyPercentagesId id = new HourlyPercentagesId();
        id.setAssociationId(association.getId());
        id.setHourBucket(hourBucket);

        return hourlyPercentagesRepository.findById(id).orElseGet(() -> {
            var newEntity = new HourlyPercentagesEntity();
            newEntity.setAssociationId(association.getId());
            newEntity.setHourBucket(hourBucket);
            newEntity.setSelfConsumptionPct(BigDecimal.ZERO);
            newEntity.setGridDependencyPct(BigDecimal.ZERO);
            return newEntity;
        });
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
