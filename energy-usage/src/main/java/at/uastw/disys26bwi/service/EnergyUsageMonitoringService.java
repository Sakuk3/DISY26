package at.uastw.disys26bwi.service;

import at.uastw.disys26bwi.entity.AssociationEntity;
import at.uastw.disys26bwi.entity.HourlyUsageEntity;
import at.uastw.disys26bwi.entity.HourlyUsageId;
import at.uastw.disys26bwi.mqSpec.constants.Association;
import at.uastw.disys26bwi.mqSpec.constants.NodeType;
import at.uastw.disys26bwi.mqSpec.constants.QueueNames;
import at.uastw.disys26bwi.mqSpec.dto.EnergyNodeMessageDto;
import at.uastw.disys26bwi.mqSpec.dto.UsageUpdateMessageDto;
import at.uastw.disys26bwi.repository.AssociationRepository;
import at.uastw.disys26bwi.repository.HourlyUsageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Service
public class EnergyUsageMonitoringService {
    private static final Logger logger = LoggerFactory.getLogger(EnergyUsageMonitoringService.class);
    private final RabbitTemplate rabbit;
    private final AssociationRepository associationRepository;
    private final HourlyUsageRepository hourlyUsageRepository;

    public EnergyUsageMonitoringService(RabbitTemplate rabbit, AssociationRepository associationRepository, HourlyUsageRepository hourlyUsageRepository) {
        this.rabbit = rabbit;
        this.associationRepository = associationRepository;
        this.hourlyUsageRepository = hourlyUsageRepository;
    }

    @RabbitListener(queues = QueueNames.ENERGY_EVENTS_QUEUE)
    public void receiveMessage(EnergyNodeMessageDto message) {
        logger.info("Received energy event: {}", message);

        final HourlyUsageEntity hourlyUsage = this.updateHourlyUsage(message);

        final UsageUpdateMessageDto data = new UsageUpdateMessageDto(
                message.association(),
                Instant.from(DateTimeFormatter.ISO_OFFSET_DATE_TIME.parse(message.datetime())).toString(),
                hourlyUsage.getCommunityProducedKwh(),
                hourlyUsage.getCommunityUsedKwh(),
                hourlyUsage.getGridUsedKwh()
        );
        this.rabbit.convertAndSend(QueueNames.USAGE_UPDATE_QUEUE, data);
    }

    private HourlyUsageEntity updateHourlyUsage(EnergyNodeMessageDto message) {
        final HourlyUsageEntity currentHourlyUsage = getHourlyUsage(message);

        switch (message.type()) {
            case NodeType.PRODUCER ->
                    currentHourlyUsage.setCommunityProducedKwh(currentHourlyUsage.getCommunityProducedKwh().add(message.kwh()));
            case NodeType.CONSUMER -> {
                BigDecimal desiredUsedKWh = currentHourlyUsage.getCommunityUsedKwh().add(message.kwh());
                BigDecimal selfSuppliedKWh = desiredUsedKWh.min(currentHourlyUsage.getCommunityProducedKwh());
                BigDecimal gridUsedKWh = desiredUsedKWh.subtract(selfSuppliedKWh).max(BigDecimal.ZERO);
                currentHourlyUsage.setCommunityUsedKwh(selfSuppliedKWh);
                currentHourlyUsage.setGridUsedKwh(currentHourlyUsage.getGridUsedKwh().add(gridUsedKWh));
            }
        }
        currentHourlyUsage.setUpdatedAt(OffsetDateTime.now());
        return hourlyUsageRepository.save(currentHourlyUsage);
    }

    private HourlyUsageEntity getHourlyUsage(EnergyNodeMessageDto message) {
        final AssociationEntity association = getOrCreateAssociation(message.association());
        final OffsetDateTime hourBucket = Instant.parse(message.datetime())
                .atOffset(ZoneOffset.UTC)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
        final HourlyUsageId id = new HourlyUsageId();
        id.setAssociationId(association.getId());
        id.setHourBucket(hourBucket);

        return hourlyUsageRepository.findById(id).orElseGet(() -> {
            var newEntity = new HourlyUsageEntity();
            newEntity.setAssociationId(association.getId());
            newEntity.setHourBucket(hourBucket);
            newEntity.setCommunityUsedKwh(BigDecimal.ZERO);
            newEntity.setGridUsedKwh(BigDecimal.ZERO);
            newEntity.setCommunityProducedKwh(BigDecimal.ZERO);
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
