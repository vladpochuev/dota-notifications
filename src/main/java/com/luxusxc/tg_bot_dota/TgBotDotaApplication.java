package com.luxusxc.tg_bot_dota;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class TgBotDotaApplication {
    public static void main(String[] args) {
        SpringApplication.run(TgBotDotaApplication.class, args);
    }
}
