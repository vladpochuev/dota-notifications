package com.luxusxc.tg_bot_dota.commands;

public enum CommandType {
    START("/start", "Start using the bot");

    public final String body;
    public final String description;

    CommandType(String body, String description) {
        this.body = body;
        this.description = description;
    }
}
