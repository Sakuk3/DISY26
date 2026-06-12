package at.uastw.disys26bwi.service;

import at.uastw.disys26bwi.controller.WeatherController;
import at.uastw.disys26bwi.mqSpec.constants.Association;
import at.uastw.disys26bwi.mqSpec.constants.NodeType;
import at.uastw.disys26bwi.mqSpec.constants.QueueNames;
import at.uastw.disys26bwi.mqSpec.dto.EnergyNodeMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;

@Service
public class EnergyProductionService {
    private static final Logger logger = LoggerFactory.getLogger(EnergyProductionService.class);

    @Value("${weatherapi.panel-size-m2}")
    private double panelSizeM2;

    @Value("${weatherapi.efficiency}")
    private double efficiency;

    private static final long INTERVAL = 5000; // 5 seconds
    private static final long MIN_INITIAL_DELAY = 1000; // 1 second
    private static final long MAX_INITIAL_DELAY = 5000; // 5 seconds

    private static final NodeType NODE_TYPE = NodeType.PRODUCER;
    private static final Association ASSOCIATION = Association.COMMUNITY;

    private final RabbitTemplate rabbit;
    private final WeatherController weatherController;

    public EnergyProductionService(RabbitTemplate rabbit, WeatherController weatherController) {
        this.rabbit = rabbit;
        this.weatherController = weatherController;
    }

    @Scheduled(fixedRate = INTERVAL)
    public void sendEnergyProductionData() {
        double radiationWm2 = weatherController.getSunlightIntensity();
        double kwh = (radiationWm2 / 1000.0) * panelSizeM2 * efficiency * (INTERVAL / 1000.0 / 3600.0);

        final EnergyNodeMessageDto data = new EnergyNodeMessageDto(
                NODE_TYPE, ASSOCIATION, BigDecimal.valueOf(kwh), Instant.now().toString()
        );
        logger.info("Sending energy production data: {} kWh (radiation: {} W/m²)", kwh, radiationWm2);
        this.rabbit.convertAndSend(QueueNames.ENERGY_EVENTS_QUEUE, data);
    }
}