package at.uastw.disys26bwi.service;

import at.uastw.disys26bwi.mqSpec.constants.Association;
import at.uastw.disys26bwi.mqSpec.constants.QueueNames;
import at.uastw.disys26bwi.mqSpec.dto.EnergyNodeMessageDto;
import at.uastw.disys26bwi.mqSpec.dto.UsageUpdateMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class EnergyUsageMonitoringService {
  private static final Logger logger = LoggerFactory.getLogger(EnergyUsageMonitoringService.class);
  private final RabbitTemplate rabbit;

  public EnergyUsageMonitoringService(RabbitTemplate rabbit) {
    this.rabbit = rabbit;
  }

  @RabbitListener(queues = QueueNames.ENERGY_EVENTS_QUEUE)
  public void receiveMessage(EnergyNodeMessageDto message) {
    logger.info("Received energy event: {}", message);
    // TODO: Implement logic to monitor energy usage based on the received message
    final String datetime = java.time.LocalDateTime.now().toString();
    final UsageUpdateMessageDto data = new UsageUpdateMessageDto(Association.COMMUNITY, datetime);
    this.rabbit.convertAndSend(QueueNames.USAGE_UPDATE_QUEUE, data);
  }
}
