package com.example.ui;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

public class UserRequests {
    public static String getUser() throws IOException {
        String user = null;
        JSONObject u = null;
        try {
            OkHttpClient client = new OkHttpClient();
            String token = Token.getToken();
            Request req = new Request.Builder()
                    .url("http://localhost:3000/user")
                    .header("Authorization", token)
                    .get()
                    .build();
            Call call = client.newCall(req);
            Response res = call.execute();

            String userString = res.body().string();

            u = new JSONObject(userString);
            user = u.getString("username");
        }catch (Exception e){
            e.printStackTrace();
        }
        return user+u.getInt("userid");
        }

}
