package at.uastw.disys26bwi.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import at.uastw.disys26bwi.mqSpec.QueueNames;

@Service
public class TextReverseService extends AbstractMessageProcessorService {
  private static final Logger logger = LoggerFactory.getLogger(TextReverseService .class);
  private static final String INPUT_QUEUE = QueueNames.INPUT_QUEUE;
  private static final String OUTPUT_QUEUE = QueueNames.TEXT_REVERSE_QUEUE;

  public TextReverseService(RabbitTemplate rabbit) {
    super(rabbit, logger);
  }

  @Override
  @RabbitListener(queues = INPUT_QUEUE)
  public void receiveMessage(String message) throws InterruptedException {
    super.receiveMessage(message);
  }

  @Override
  protected String processText(String text) {
    return new StringBuilder(text).reverse().toString();
  }

  @Override
  protected String getOutputQueue() {
    return OUTPUT_QUEUE;
  }
}