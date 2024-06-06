package ru.game.cat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CatBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatBotApplication.class, args);
    }

}
