package com.luxusxc.tg_bot_dota.service;

import com.luxusxc.tg_bot_dota.model.UserEntity;
import com.luxusxc.tg_bot_dota.model.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class StatPerDayScheduler {
    private final TelegramBot bot;

    public StatPerDayScheduler(TelegramBot bot) {
        this.bot = bot;
    }

    @Scheduled(cron = "0 0 0 * * *")
    private void clearStatPerDay() {
        UserRepository repo = bot.getUserRepository();
        Iterable<UserEntity> users = repo.findAll();
        for (UserEntity user : users) {
            user.setWinPerDay(0);
            user.setLosePerDay(0);
            repo.save(user);
        }
    }
}
