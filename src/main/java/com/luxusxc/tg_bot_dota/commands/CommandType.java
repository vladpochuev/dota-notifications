package com.luxusxc.tg_bot_dota.commands;

import java.util.HashMap;
import java.util.Map;

public enum CommandType {
    START("/start", "Start using the bot"),
    CHANGE_ID("/changeid", "Change Open Dota ID");

    public final String body;
    public final String description;
    private static final Map<String, CommandType> BODY_TO_COMMAND;

    static {
        BODY_TO_COMMAND = new HashMap<>();
        for (CommandType command : CommandType.values()) {
            BODY_TO_COMMAND.put(command.body, command);
        }
    }

    CommandType(String body, String description) {
        this.body = body;
        this.description = description;
    }

    public static CommandType getCommand(String body) {
        return BODY_TO_COMMAND.get(body);
    }
}
