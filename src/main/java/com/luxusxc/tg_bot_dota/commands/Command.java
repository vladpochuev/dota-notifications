package com.luxusxc.tg_bot_dota.commands;

import com.luxusxc.tg_bot_dota.service.TelegramBot;
import lombok.AllArgsConstructor;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.function.Function;

@AllArgsConstructor
public abstract class Command implements Function<Update, Boolean> {
    protected TelegramBot bot;
}
