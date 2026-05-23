package at.uastw.disys26bwi.messaging;

import org.slf4j.Logger;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

public abstract class AbstractMessageProcessorService {

    protected final RabbitTemplate rabbit;
    protected final Logger logger;

    protected AbstractMessageProcessorService(RabbitTemplate rabbit, Logger logger) {
        this.rabbit = rabbit;
        this.logger = logger;
    }

    public void receiveMessage(String message) throws InterruptedException {
        logger.info("Received message: {}", message);
        sleep(message);
        final String processedMessage = processText(message);
        logger.info("Processed message: {}", processedMessage);
        rabbit.convertAndSend(getOutputQueue(), processedMessage);
    }

    protected void sleep(String message) throws InterruptedException {
        logger.info("Sleeping for {}s", message.length());
        final int sleepTime = 1000 * message.length();
        Thread.sleep(sleepTime);
    }

    protected abstract String processText(String text);

    protected abstract String getOutputQueue();
}