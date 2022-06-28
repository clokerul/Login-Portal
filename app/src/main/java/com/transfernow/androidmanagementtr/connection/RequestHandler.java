package com.transfernow.androidmanagementtr.connection;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.transfernow.androidmanagementtr.model.LoginCredentials;
import com.transfernow.androidmanagementtr.model.User;

import java.io.IOException;
import java.net.URL;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.BufferedSink;

public class RequestHandler {
    public static String host = "192.168.0.246:3001";
    public static final MediaType MEDIA_TYPE_MARKDOWN
            = MediaType.parse("text/x-markdown; charset=utf-8");

    private final OkHttpClient client = new OkHttpClient();

    public boolean try_login(LoginCredentials credentials) {
        try {
            return login_post(host, credentials).equals(credentials.getPassword());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private String login_post(String host, LoginCredentials credentials) throws Exception {

        String url_sz = "http://" + host + "/user?username=" + credentials.getUsername();
        URL url = new URL(url_sz);

        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

            Gson gson = new Gson();
            User[] user = gson.fromJson(response.body().string(), User[].class);
            String response_sz = user[0].getPassword();
            return response_sz;
        }
    }
}
