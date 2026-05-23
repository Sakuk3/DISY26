package at.uastw.disys26bwi.service;

import at.uastw.disys26bwi.controller.WeatherController;
import at.uastw.disys26bwi.mqSpec.constants.Association;
import at.uastw.disys26bwi.mqSpec.constants.NodeType;
import at.uastw.disys26bwi.mqSpec.constants.QueueNames;
import at.uastw.disys26bwi.mqSpec.dto.EnergyNodeMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class EnergyProductionService {
  private static final Logger logger = LoggerFactory.getLogger(EnergyProductionService.class);
  private static final long INTERVAL = 5000; // 5 seconds
  private static final long MIN_INITIAL_DELAY = 1000; // 1 second
  private static final long MAX_INITIAL_DELAY = 5000; // 5 seconds
  public static final long INITIAL_DELAY =
    ThreadLocalRandom.current()
      .nextLong(MIN_INITIAL_DELAY, MAX_INITIAL_DELAY);

  private static final NodeType NODE_TYPE = NodeType.PRODUCER;
  private static final Association ASSOCIATION = Association.COMMUNITY;

  private final RabbitTemplate rabbit;
  private final WeatherController weatherController;

  public EnergyProductionService(RabbitTemplate rabbit, WeatherController weatherController) {
    this.rabbit = rabbit;
    this.weatherController = weatherController;
  }

  @Scheduled(
    fixedRate = INTERVAL,
    initialDelayString = "#{T(at.uastw.disys26bwi.service.EnergyProductionService).INITIAL_DELAY}"
  )
  public void sendEnergyProductionData() {
    final double kwh = weatherController.getSunlightIntensity() * 0.001; // Simulate energy production based on sunlight intensity
    final String datetime = java.time.LocalDateTime.now().toString();
    final EnergyNodeMessageDto data = new EnergyNodeMessageDto(NODE_TYPE, ASSOCIATION, kwh, datetime);
    logger.info("Sending energy production data: {}", data);
    this.rabbit.convertAndSend(QueueNames.ENERGY_EVENTS_QUEUE, data);
  }
}