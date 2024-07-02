package com.luxusxc.tg_bot_dota.service;

import com.luxusxc.tg_bot_dota.model.Hero;
import com.luxusxc.tg_bot_dota.model.HeroStats;
import com.luxusxc.tg_bot_dota.model.Match;
import com.luxusxc.tg_bot_dota.model.UserEntity;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@AllArgsConstructor
@Service
public class MatchMessageFormatter {
    private static final String messageFormat = """
            <code>%s
                            
            Hero	%s
            K/D/A	%s
            Hero damage	%d
            Tower damage	%d
            Hero healing	%d
                            
            Statistics for day	%s
            %s winrate	%s
            </code>
            """;
    private final TelegramBot bot;

    public String getMessage(UserEntity user, Match match) {
        String kda = "%d/%d/%d".formatted(match.getKills(), match.getDeaths(), match.getAssists());
        String dayStat = "%d/%d (%s)".formatted(user.getWinPerDay(), user.getLosePerDay(), getWLDifference(user));
        String winrate = getHeroWinrate(user.getDotaId(), match);
        Hero hero = Hero.getById(match.getHeroId());

        String message = messageFormat.formatted(
                match.getIsWin() ? "Win" : "Lose", hero.getName(),
                kda, match.getHeroDamage(), match.getTowerDamage(), match.getHeroHealing(),
                dayStat, hero.getName(), winrate);
        return formatMessage(message);
    }

    private String formatMessage(String message) {
        int max = 40;
        StringBuilder builder = new StringBuilder();
        String[] strings = message.split("\n");
        for (String string : strings) {
            String[] keyValue = string.split("\\t");
            if (keyValue.length < 2) {
                builder.append(string).append("\n");
            } else {
                int diff = max - keyValue[0].length() - keyValue[1].length();
                builder.append(keyValue[0]).append(" ".repeat(diff)).append(keyValue[1]).append("\n");
            }
        }
        return builder.toString();
    }

    private String getHeroWinrate(String userId, Match match) {
        DotaAccount account = bot.getDotaAccount();
        HeroStats hero = account.getHeroStats(userId, match.getHeroId());
        DecimalFormat df = new DecimalFormat("#.#");

        double winrate = (double) hero.getWin() / hero.getGames() * 100;
        String winrateStr = df.format(winrate);
        double winrateDiff = (double) ((hero.getGames() * (match.getIsWin() ? 1 : 0) - hero.getWin()) * 100) / (hero.getGames() * (hero.getGames() - 1));
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
