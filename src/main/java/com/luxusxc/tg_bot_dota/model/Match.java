package com.luxusxc.tg_bot_dota.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Match {
    @JsonProperty("match_id")
    private long id;
    private Boolean isWin;
    @JsonProperty("kills")
    private int kills;
    @JsonProperty("deaths")
    private int deaths;
    @JsonProperty("assists")
    private int assists;
    @JsonProperty("hero_damage")
    private int heroDamage;
    @JsonProperty("tower_damage")
    private int towerDamage;
    @JsonProperty("hero_healing")
    private int heroHealing;
}
