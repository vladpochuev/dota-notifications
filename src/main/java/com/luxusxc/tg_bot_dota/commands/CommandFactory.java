package com.luxusxc.tg_bot_dota.commands;

import com.luxusxc.tg_bot_dota.service.TelegramBot;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CommandFactory {
    private final TelegramBot bot;

    public Command getCommand(CommandType command) {
        switch (command) {
            case START -> {
                return new StartCommand(bot);
            }
            default -> {
                return new UnknownCommand(bot);
            }
        }
    }
}
