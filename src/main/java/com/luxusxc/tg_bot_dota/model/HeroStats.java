package com.luxusxc.tg_bot_dota.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class HeroStats {
    @JsonProperty("hero_id")
    private int heroId;
    @JsonProperty("games")
    private int games;
    @JsonProperty("win")
    private int win;
}
