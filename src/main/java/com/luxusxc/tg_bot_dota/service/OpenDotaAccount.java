package com.luxusxc.tg_bot_dota.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class OpenDotaAccount implements DotaAccount {
    private final GetRequest request;

    public OpenDotaAccount(GetRequest request) {
        this.request = request;
    }

    @Override
    public boolean isIdValid(String id) {
        String url = "https://api.opendota.com/api/players/";
        String jsonString = request.get(url + id);
        JSONObject json = new JSONObject(jsonString);
        return !json.has("error");
    }
}
