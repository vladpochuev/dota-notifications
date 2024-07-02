package com.luxusxc.tg_bot_dota.service;

import com.luxusxc.tg_bot_dota.model.Match;
import com.luxusxc.tg_bot_dota.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class MatchActualizer {
    private final TelegramBot bot;
    private final MatchMessageFormatter messageFormatter;

    public void actualize(UserEntity user) {
        DotaAccount account = bot.getDotaAccount();
        Match match = account.getLastMatch(user.getDotaId(), true);
        refreshUsersData(user, match);
        sendMatchStatistics(user, match);
    }

    private void refreshUsersData(UserEntity user, Match match) {
        user.setLastMatchId(match.getId());
        if (match.getIsWin()) {
            user.setWinPerDay(user.getWinPerDay() + 1);
        } else {
            user.setLosePerDay(user.getLosePerDay() + 1);
        }

        bot.getUserRepository().save(user);
    }

    private void sendMatchStatistics(UserEntity user, Match match) {
        String formattedMessage = messageFormatter.getMessage(user, match);
        bot.sendMessage(user.getChatId(), formattedMessage, "HTML");
        log.info("User %s (dota_id=%s) was informed about recent match.".formatted(user.getFirstName(), user.getDotaId()));
    }
}
