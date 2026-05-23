package at.uastw.disys26bwi.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import at.uastw.disys26bwi.mqSpec.QueueNames;

@Service
public class DeleteEverySecondCharService extends AbstractMessageProcessorService{
  private static final Logger logger = LoggerFactory.getLogger(DeleteEverySecondCharService.class);
  private static final String INPUT_QUEUE = QueueNames.TEXT_REVERSE_QUEUE;
  private static final String OUTPUT_QUEUE = QueueNames.DELETE_EVERY_SECOND_CHARACTER_QUEUE;

  public DeleteEverySecondCharService(RabbitTemplate rabbit) {
    super(rabbit, logger);
  }

  @RabbitListener(queues = INPUT_QUEUE)
  public void receiveMessage(String message) throws InterruptedException {
    super.receiveMessage(message);
  }

  public String processText(String text) {
    StringBuilder result = new StringBuilder();
    for (int i = 0; i < text.length(); i++) {
      if (i % 2 == 0) {
        result.append(text.charAt(i));
      }
    }
    return result.toString();
  }

  @Override
  protected String getOutputQueue() {
    return OUTPUT_QUEUE;
  }
}
