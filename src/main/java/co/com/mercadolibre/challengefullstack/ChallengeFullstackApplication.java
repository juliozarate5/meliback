package co.com.mercadolibre.challengefullstack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan({"co.com.mercadolibre.challengefullstack"})
@EnableCaching
@EnableScheduling
public class ChallengeFullstackApplication {

  public static void main(String[] args) {
    SpringApplication.run(ChallengeFullstackApplication.class, args);
  }
}
