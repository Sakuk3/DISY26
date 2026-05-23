package at.uastw.disys26bwi.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import at.uastw.disys26bwi.mqSpec.QueueNames;

@Service
public class RemoveSpacesService extends AbstractMessageProcessorService {
  private static final Logger logger = LoggerFactory.getLogger(RemoveSpacesService.class);
  private static final String INPUT_QUEUE = QueueNames.DELETE_EVERY_SECOND_CHARACTER_QUEUE;
  private static final String OUTPUT_QUEUE = QueueNames.OUTPUT_QUEUE;

  public RemoveSpacesService(RabbitTemplate rabbit) {
    super(rabbit, logger);
  }

  @Override
  @RabbitListener(queues = INPUT_QUEUE)
  public void receiveMessage(String message) throws InterruptedException {
    super.receiveMessage(message);
  }

  @Override
  public String processText(String text) {
    return text.replaceAll("\\s+", "");
  }

  @Override
  protected String getOutputQueue() {
    return OUTPUT_QUEUE;
  }
}
