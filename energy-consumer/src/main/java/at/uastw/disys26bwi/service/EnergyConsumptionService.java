package at.uastw.disys26bwi.service;

import at.uastw.disys26bwi.mqSpec.constants.Association;
import at.uastw.disys26bwi.mqSpec.constants.NodeType;
import at.uastw.disys26bwi.mqSpec.constants.QueueNames;
import at.uastw.disys26bwi.mqSpec.dto.EnergyNodeMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class EnergyConsumptionService {
    private static final Logger logger = LoggerFactory.getLogger(EnergyConsumptionService.class);
    private static final long INTERVAL = 5000; // 5 seconds

    private static final double[] HOURLY_CONSUMPTION_FACTORS = {
        0.1, 0.1, 0.1, 0.1, 0.1, 0.2,     // 00:00-05:00: Night, very low
        0.6, 0.9, 1.0, 0.6, 0.3, 0.3,     // 06:00-11:00: Morning peak + lunch dip
        0.5, 0.3, 0.3, 0.3, 0.4, 0.7,     // 12:00-17:00: Afternoon low + evening start
        0.9, 1.0, 0.8, 0.8, 0.6, 0.2      // 18:00-23:00: Evening peak + night start
    };

    private static final NodeType NODE_TYPE = NodeType.CONSUMER;
    private static final Association ASSOCIATION = Association.COMMUNITY;

    // Base consumption per 5-second interval
    // Typical community: 1.0-1.5 kWh/hour = 0.0008 - 0.0025 kWh per 5-second interval (base)
    private static final double MIN_KWH_PER_5SEC = 0.0014;
    private static final double MAX_KWH_PER_5SEC = 0.0021;

    private final RabbitTemplate rabbit;

    public EnergyConsumptionService(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    @Scheduled(fixedRate = INTERVAL)
    public void sendEnergyConsumptionData() {
        int currentHour = LocalDateTime.now().getHour();
        double factor = HOURLY_CONSUMPTION_FACTORS[currentHour];

        // Random variation within realistic range, adjusted by hourly factor
        double randomVariation = ThreadLocalRandom.current().nextDouble(MIN_KWH_PER_5SEC, MAX_KWH_PER_5SEC);
        double kwhValue = randomVariation * factor;

        final BigDecimal kwh = BigDecimal.valueOf(kwhValue);
        final EnergyNodeMessageDto data = new EnergyNodeMessageDto(NODE_TYPE, ASSOCIATION, kwh, Instant.now().toString());

        logger.info("Sending consumption: {} kWh at hour {} (factor: {})",
            String.format("%.5f", kwhValue), currentHour, String.format("%.2f", factor));
        this.rabbit.convertAndSend(QueueNames.ENERGY_EVENTS_QUEUE, data);
    }
}