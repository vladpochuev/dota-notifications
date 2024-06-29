package com.luxusxc.tg_bot_dota.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luxusxc.tg_bot_dota.model.Match;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpenDotaAccount implements DotaAccount {
    private final GetRequest request;

    public OpenDotaAccount(GetRequest request) {
        this.request = request;
    }

    @Override
    public String getNickname(String id) {
        String url = "https://api.opendota.com/api/players/";
        String jsonString = request.get(url + id);
        JSONObject json = new JSONObject(jsonString);

        String nickname = null;
        if (!json.has("error")) {
            nickname = json.getJSONObject("profile").getString("personaname");
        }

        return nickname;
    }

    @Override
    public Match getLastMatch(String id) {
        String urlFormat = "https://api.opendota.com/api/players/%s/recentMatches";
        String jsonString = request.get(urlFormat.formatted(id));

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            List<Match> matches = objectMapper.readValue(jsonString, new TypeReference<>() {});
            Match lastMatch = matches.get(0);
            lastMatch.setIsWin(isMatchWin(lastMatch.getId(), id));
            return lastMatch;
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isMatchWin(long matchId, String userId) {
        String urlFormat = "https://api.opendota.com/api/players/%s/matches?limit=1&win=1";
        String jsonString = request.get(urlFormat.formatted(userId));
        JSONObject json = new JSONArray(jsonString).getJSONObject(0);

        return json.getLong("match_id") == matchId;
    }
}
