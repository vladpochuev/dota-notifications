package com.luxusxc.tg_bot_dota.commands;

import com.luxusxc.tg_bot_dota.model.Match;
import com.luxusxc.tg_bot_dota.model.UserEntity;
import com.luxusxc.tg_bot_dota.model.UserRepository;
import com.luxusxc.tg_bot_dota.service.DotaAccount;
import com.luxusxc.tg_bot_dota.service.TelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
@Slf4j
public class SetDotaIdCommand extends Command {
    private static final String VALID_ARGUMENT_FORMAT = "You are logged as a %s.";
    private static final String WRONG_ARGUMENT = "Invalid ID. Send your OpenDota account ID to start using of the bot.";
    public SetDotaIdCommand(TelegramBot bot) {
        super(bot);
    }

    @Override
    public Boolean apply(Update update) {
        long chatId = update.getMessage().getChatId();
        String accountId = update.getMessage().getText();
        DotaAccount account = bot.getDotaAccount();
        String dotaNickname = account.getNickname(accountId);

        if (dotaNickname != null) {
            UserRepository repository = bot.getUserRepository();
            UserEntity user = repository.findById(chatId).orElseThrow();
            Match lastMatch = account.getLastMatch(accountId, false);
            user.setDotaId(accountId);
            user.setUserStatus(null);
            user.setLastMatchId(lastMatch.getId());
            repository.save(user);
            bot.sendMessage(chatId, VALID_ARGUMENT_FORMAT.formatted(dotaNickname));
            log.info("dota_id %s was set to %s".formatted(user.getFirstName(), user.getDotaId()));
        } else {
            bot.sendMessage(chatId, WRONG_ARGUMENT);
        }
        return true;
    }
}
