package com.luxusxc.tg_bot_dota.commands;

import com.luxusxc.tg_bot_dota.service.TelegramBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class UnknownCommand extends Command {
    private static final String MESSAGE = "Sorry, command was not recognized.";
    public UnknownCommand(TelegramBot bot) {
        super(bot);
    }

    @Override
    public Boolean apply(Update update) {
        long chatId = update.getMessage().getChatId();
        bot.sendMessage(chatId, MESSAGE);
        return true;
    }
}
