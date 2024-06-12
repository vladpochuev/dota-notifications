package com.luxusxc.tg_bot_dota.model;

import com.luxusxc.tg_bot_dota.status.StatusType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.sql.Timestamp;

@Entity(name = "user_data_table")
@Data
public class UserEntity {
    @Id
    private Long chatId;
    private String firstName;
    private String lastName;
    private String userName;
    private String languageCode;
    private Timestamp registeredAt;
    private String dotaId;
    private StatusType userStatus;
}
