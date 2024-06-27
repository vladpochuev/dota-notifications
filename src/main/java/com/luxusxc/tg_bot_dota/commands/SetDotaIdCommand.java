package com.luxusxc.tg_bot_dota.commands;

import com.luxusxc.tg_bot_dota.model.UserEntity;
import com.luxusxc.tg_bot_dota.model.UserRepository;
import com.luxusxc.tg_bot_dota.service.DotaAccount;
import com.luxusxc.tg_bot_dota.service.TelegramBot;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service
public class SetDotaIdCommand extends Command {
    private static final String VALID_ARGUMENT = "Valid.";
    private static final String WRONG_ARGUMENT = "Invalid ID. Send your OpenDota account ID to start using of the bot.";
    public SetDotaIdCommand(TelegramBot bot) {
        super(bot);
    }

    @Override
    public Boolean apply(Update update) {
        long chatId = update.getMessage().getChatId();
        String accountId = update.getMessage().getText();
        DotaAccount account = bot.getDotaAccount();
        boolean isValid = account.isIdValid(accountId);

        if (isValid) {
            UserRepository repository = bot.getUserRepository();
            UserEntity entity = repository.findById(chatId).orElseThrow();
            entity.setDotaId(accountId);
            entity.setUserStatus(null);
            repository.save(entity);
            bot.sendMessage(chatId, VALID_ARGUMENT);
        } else {
            bot.sendMessage(chatId, WRONG_ARGUMENT);
        }
        return true;
    }
}
