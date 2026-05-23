package at.uastw.disys26bwi.service;

import at.uastw.disys26bwi.mqSpec.constants.QueueNames;
import at.uastw.disys26bwi.mqSpec.dto.UsageUpdateMessageDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class EnergyUpdateService {
  private static final Logger logger = LoggerFactory.getLogger(EnergyUpdateService.class);

  @RabbitListener(queues = QueueNames.USAGE_UPDATE_QUEUE)
  public void receiveMessage(UsageUpdateMessageDto message) {
    logger.info("Received usage update: {}", message);
    // TODO: Implement logic to update energy usage based on the received message
  }
}
