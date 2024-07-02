package com.luxusxc.tg_bot_dota.service;

import com.luxusxc.tg_bot_dota.model.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class RecentMatchChecker {
    private final TelegramBot bot;
    private final MatchActualizer matchActualizer;

    @Scheduled(fixedDelay = 45000)
    private void checkMatches() {
        UserRepository repository = bot.getUserRepository();
        Iterable<UserEntity> users = repository.findAll();

        for (UserEntity user : users) {
            checkUser(user);
        }
    }

    private void checkUser(UserEntity user) {
        if (checkUserActive(user)) {
            String userId = user.getDotaId();
            DotaAccount account = bot.getDotaAccount();
            Match match = account.getLastMatch(userId, false);

            if (isUsersMatchSaved(user, match)) return;
            matchActualizer.actualize(user);
        }
    }

    private boolean checkUserActive(UserEntity user) {
        String accountId = user.getDotaId();
        return accountId != null;
    }

    private boolean isUsersMatchSaved(UserEntity user, Match match) {
        long lastSavedMatchId = user.getLastMatchId();
        long lastMatchId = match.getId();
        return lastMatchId == lastSavedMatchId;
    }
}
