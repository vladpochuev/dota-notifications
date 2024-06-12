package com.luxusxc.tg_bot_dota.status;

import com.luxusxc.tg_bot_dota.commands.Command;
import com.luxusxc.tg_bot_dota.commands.SetDotaIdCommand;
import com.luxusxc.tg_bot_dota.service.TelegramBot;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StatusFactory {
    private final TelegramBot bot;

    public Command getStatus(StatusType status) {
        switch (status) {
            case DOTA_ID -> {
                return new SetDotaIdCommand(bot);
            }
        }
        return null;
    }
}
