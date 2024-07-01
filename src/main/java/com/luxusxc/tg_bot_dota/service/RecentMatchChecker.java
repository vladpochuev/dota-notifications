package com.luxusxc.tg_bot_dota.service;

import com.luxusxc.tg_bot_dota.model.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
@Slf4j
public class RecentMatchChecker {
    private final TelegramBot bot;

    public RecentMatchChecker(TelegramBot bot) {
        this.bot = bot;
    }

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

            match = account.getLastMatch(userId, true);
            refreshUsersData(user, match);
            sendMatchStatistics(user, match);
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
        String messageFormat = """
                %s
                
                Hero                    %s
                K/D/A                   %d/%d/%d
                Hero damage             %d
                Tower damage            %d
                Hero healing            %d
                
                Statistics for day      %d/%d (%s)
                %s winrate              %s
                """;

        String winrate = getHeroWinrate(user.getDotaId(), match);
        Hero hero = Hero.getById(match.getHeroId());
        String message = messageFormat.formatted(
                match.getIsWin() ? "Win" : "Lose", hero.getName(),
                match.getKills(), match.getDeaths(), match.getAssists(),
                match.getHeroDamage(), match.getTowerDamage(), match.getHeroHealing(),
                user.getWinPerDay(), user.getLosePerDay(), getWLDifference(user),
                hero.getName(), winrate);
        bot.sendMessage(user.getChatId(), message);
        log.info("User %s (dota_id=%s) was informed about recent match.".formatted(user.getFirstName(), user.getDotaId()));
    }

    private String getHeroWinrate(String userId, Match match) {
        DotaAccount account = bot.getDotaAccount();
        HeroStats hero = account.getHeroStats(userId, match.getHeroId());
        DecimalFormat df = new DecimalFormat("#.#");

        double winrate = (double) hero.getWin() / hero.getGames() * 100;
        String winrateStr = df.format(winrate);
        double winrateDiff = (double)((hero.getGames() * (match.getIsWin() ? 1 : 0) - hero.getWin()) * 100) / (hero.getGames() * (hero.getGames() - 1));
        String winrateDiffStr = (match.getIsWin() ? "+" : "") + df.format(winrateDiff);

        return "%s%% (%s%%)".formatted(winrateStr, winrateDiffStr);
    }

    private String getWLDifference(UserEntity user) {
        StringBuilder builder = new StringBuilder();
        int win = user.getWinPerDay();
        int lose = user.getLosePerDay();
        builder.append(win > lose ? "+" : "");
        builder.append(win - lose);

        return builder.toString();
    }
}
