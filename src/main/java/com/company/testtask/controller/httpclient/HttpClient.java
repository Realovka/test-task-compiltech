package com.company.testtask.controller.httpclient;

import lombok.extern.slf4j.Slf4j;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.io.IOException;

@Slf4j
public class HttpClient {
    public void createUser() {
        OkHttpClient client = new OkHttpClient();
        String json = new StringBuilder()
                .append("{")
                .append("\"login\":\"user\",")
                .append("\"password\":\"password\",")
                .append("\"fullName\":\"user\"")
                .append("}").toString();
        RequestBody body = RequestBody.create(json,
                MediaType.parse("application/json; charset=utf-8"));
        Request request = new Request.Builder()
                .url("http://localhost:8081/signup")
                .post(body)
                .build();
        try (Response response = client.newCall(request).execute()) {
            String serverAnswer = response.body().string();
        } catch (IOException e) {
            log.error("Exception in createUser() method: {}", e);
        }
    }
}
