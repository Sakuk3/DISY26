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
    private static final long MIN_INITIAL_DELAY = 1000; // 1 second
    private static final long MAX_INITIAL_DELAY = 5000; // 5 seconds

    //Starts with 00:00 - ends with 23:00 //Peak in the morning and afternoon hours - small peak for lunch
    private static final double[] HOURLY_CONSUMPTION_FACTORS = {0.1, 0.1, 0.1, 0.1, 0.1, 0.1, 0.6, 0.9, 1.0, 0.6, 0.3, 0.3, 0.5, 0.3, 0.3, 0.3, 0.4, 0.7, 0.9, 1.0, 0.8, 0.8, 0.6, 0.2};

    private static final NodeType NODE_TYPE = NodeType.CONSUMER;
    private static final Association ASSOCIATION = Association.COMMUNITY;

    private final RabbitTemplate rabbit;

    public EnergyConsumptionService(RabbitTemplate rabbit) {
        this.rabbit = rabbit;
    }

    @Scheduled(fixedRate = INTERVAL)
    public void sendEnergyConsumptionData() {
        double factor = HOURLY_CONSUMPTION_FACTORS[LocalDateTime.now().getHour()];
        double kwhValue = ThreadLocalRandom.current().nextDouble(0.01, 0.05) * factor;

        final BigDecimal kwh = BigDecimal.valueOf(kwhValue);
        final EnergyNodeMessageDto data = new EnergyNodeMessageDto(NODE_TYPE, ASSOCIATION, kwh, Instant.now().toString());
        logger.info("Sending energy consumption data: {} kWh (factor: {})", kwh, factor);
        this.rabbit.convertAndSend(QueueNames.ENERGY_EVENTS_QUEUE, data);
    }
}