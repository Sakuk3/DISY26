package at.uastw.disys26bwi;

import at.uastw.disys26bwi.mqSpec.QueueNames;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Disys26bwiApplication {
  @Bean
  public Queue inputQueue() {
    return new Queue(QueueNames.INPUT_QUEUE , false);
  }

  @Bean
  public Queue textReverseQueue() {
    return new Queue(QueueNames.TEXT_REVERSE_QUEUE, false);
  }

  @Bean
  public Queue deleteEverySecondCharacterQueue() {
    return new Queue(QueueNames.DELETE_EVERY_SECOND_CHARACTER_QUEUE, false);
  }

  @Bean
  public Queue outputQueue() {
    return new Queue(QueueNames.OUTPUT_QUEUE, false);
  }

  public static void main(String[] args) {
    SpringApplication.run(Disys26bwiApplication.class, args);
  }
}
