package com.luxusxc.tg_bot_dota.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.luxusxc.tg_bot_dota.model.HeroStats;
import com.luxusxc.tg_bot_dota.model.Match;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OpenDotaAccount implements DotaAccount {
    private final GetRequest request;
    private final ObjectMapper mapper;

    public OpenDotaAccount(GetRequest request) {
        this.request = request;
        mapper = new ObjectMapper();
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
    public Match getLastMatch(String id, boolean fullMatchInfo) {
        String urlFormat = "https://api.opendota.com/api/players/%s/recentMatches";
        String jsonString = request.get(urlFormat.formatted(id));

        try {
            List<Match> matches = mapper.readValue(jsonString, new TypeReference<>() {});
            Match lastMatch = matches.get(0);
            if (fullMatchInfo) {
                boolean isWin = isMatchWin(lastMatch.getId(), id);
                lastMatch.setIsWin(isWin);
            }
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

    @Override
    public HeroStats getHeroStats(String userId, int heroId) {
        String urlFormat = "https://api.opendota.com/api/players/%s/heroes?hero_id=%d";
        String jsonString = request.get(urlFormat.formatted(userId, heroId));
        try {
            List<HeroStats> stats = mapper.readValue(jsonString, new TypeReference<>() {});
            return stats.get(0);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
