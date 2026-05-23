package at.uastw.disys26bwi;

import at.uastw.disys26bwi.mqSpec.constants.QueueNames;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Disys26bwiApplication {
  @Bean
  public Queue inputQueue() {
    return new Queue(QueueNames.ENERGY_CONSUMPTION_QUEUE, true);
  }

  public static void main(String[] args) {
    SpringApplication.run(Disys26bwiApplication.class, args);
  }
}
