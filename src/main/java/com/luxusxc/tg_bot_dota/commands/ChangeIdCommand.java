package com.luxusxc.tg_bot_dota.commands;

import com.luxusxc.tg_bot_dota.model.UserEntity;
import com.luxusxc.tg_bot_dota.model.UserRepository;
import com.luxusxc.tg_bot_dota.service.TelegramBot;
import com.luxusxc.tg_bot_dota.status.StatusType;
import org.telegram.telegrambots.meta.api.objects.Update;

public class ChangeIdCommand extends Command {
    private static final String MESSAGE = "Send your OpenDota account ID.";

    public ChangeIdCommand(TelegramBot bot) {
        super(bot);
    }

    @Override
    public Boolean apply(Update update) {
        long chatId = update.getMessage().getChatId();

        UserRepository repository = bot.getUserRepository();
        UserEntity user = repository.findById(chatId).orElseThrow();
        user.setUserStatus(StatusType.DOTA_ID);
        repository.save(user);

        bot.sendMessage(chatId, MESSAGE);

        return true;
    }
}
