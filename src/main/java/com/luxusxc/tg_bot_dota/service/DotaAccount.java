package com.luxusxc.tg_bot_dota.service;

import com.luxusxc.tg_bot_dota.model.Match;

public interface DotaAccount {
    String getNickname(String id);
    Match getLastMatch(String id, boolean fullMatchInfo);
}