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
import java.util.concurrent.ThreadLocalRandom;

@Service
public class EnergyConsumptionService {
  private static final Logger logger = LoggerFactory.getLogger(EnergyConsumptionService.class);
  private static final long INTERVAL = 5000; // 5 seconds
  private static final long MIN_INITIAL_DELAY = 1000; // 1 second
  private static final long MAX_INITIAL_DELAY = 5000; // 5 seconds
  public static final long INITIAL_DELAY =
    ThreadLocalRandom.current()
      .nextLong(MIN_INITIAL_DELAY, MAX_INITIAL_DELAY);

  private static final NodeType NODE_TYPE = NodeType.CONSUMER;
  private static final Association ASSOCIATION = Association.COMMUNITY;

  private final RabbitTemplate rabbit;

  public EnergyConsumptionService(RabbitTemplate rabbit) {
    this.rabbit = rabbit;
  }

  @Scheduled(
    fixedRate = INTERVAL,
    initialDelayString = "#{T(at.uastw.disys26bwi.service.EnergyConsumptionService).INITIAL_DELAY}"
  )
  public void sendEnergyProductionData() {
    // TODO: should be based on the time of day
    final BigDecimal kwh = BigDecimal.valueOf(ThreadLocalRandom.current().nextDouble(0.1, 5.0));
    final EnergyNodeMessageDto data = new EnergyNodeMessageDto(NODE_TYPE, ASSOCIATION, kwh, Instant.now().toString());
    logger.info("Sending energy consumption data: {}", data);
    this.rabbit.convertAndSend(QueueNames.ENERGY_EVENTS_QUEUE, data);
  }
}