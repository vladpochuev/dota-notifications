package com.luxusxc.tg_bot_dota.service;

import com.luxusxc.tg_bot_dota.commands.*;
import com.luxusxc.tg_bot_dota.config.BotConfig;
import com.luxusxc.tg_bot_dota.model.UserEntity;
import com.luxusxc.tg_bot_dota.model.UserRepository;
import com.luxusxc.tg_bot_dota.status.StatusFactory;
import com.luxusxc.tg_bot_dota.status.StatusType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Slf4j
@Service
public class TelegramBot extends TelegramLongPollingBot {
    private final BotConfig config;
    private final UserRepository userRepository;
    private final DotaAccount dotaAccount;

    public TelegramBot(BotConfig config, UserRepository userRepository, DotaAccount dotaAccount) {
        super(config.getToken());
        this.config = config;
        this.userRepository = userRepository;
        this.dotaAccount = dotaAccount;
        addListOfCommands();
    }

    private void addListOfCommands() {
        List<BotCommand> listOfCommands = new ArrayList<>();
        for (CommandType command : CommandType.values()) {
            listOfCommands.add(new BotCommand(command.body, command.description));
        }

        try {
            execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot`s command list " + e.getMessage());
        }
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            UserEntity user = userRepository.findById(chatId).orElse(null);
            if (user != null && user.getUserStatus() != null) {
                handleStatus(user.getUserStatus(), update);
            } else {
                handleCommand(update);
            }
        }
    }

    private void handleStatus(StatusType status, Update update) {
        StatusFactory factory = new StatusFactory(this);
        Command command = factory.getStatus(status);
        command.apply(update);
    }

    private void handleCommand(Update update) {
        String messageText = update.getMessage().getText();
        Command command = defineCommand(messageText);
        command.apply(update);
    }

    private Command defineCommand(String messageText) {
        CommandFactory commandFactory = new CommandFactory(this);
        for (CommandType value : CommandType.values()) {
            String commandBody = Utils.getCommandBody(messageText);
            if (commandBody.equals(value.body)) {
                return commandFactory.getCommand(value);
            }
        }
        return new UnknownCommand(this);
    }

    public void sendMessage(long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        sendMessage(message);
    }

    public void sendMessage(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }
}
