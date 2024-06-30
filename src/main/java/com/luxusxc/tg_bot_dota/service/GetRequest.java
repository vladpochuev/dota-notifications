package com.luxusxc.tg_bot_dota.service;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class GetRequest {
    private final OkHttpClient client = new OkHttpClient();
    public String get(String url) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            log.info("Get request to the " + url);
            return response.body().string();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
