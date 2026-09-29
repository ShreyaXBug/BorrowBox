package com.example.borrowbox.database;

import com.example.borrowbox.BuildConfig;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class SupabaseClient {

    private static final OkHttpClient client = new OkHttpClient();

    public static void getBooks(final SupabaseCallback callback) {

        String url = BuildConfig.SUPABASE_URL
                + "/rest/v1/borrowbox_books?select=*";

        Request request = new Request.Builder()
                .url(url)
                .get()
                .addHeader("apikey", BuildConfig.SUPABASE_KEY)
                .addHeader(
                        "Authorization",
                        "Bearer " + BuildConfig.SUPABASE_KEY
                )
                .build();

        new Thread(() -> {

            try (Response response = client.newCall(request).execute()) {

                if (response.body() == null) {
                    callback.onError("Empty response from Supabase");
                    return;
                }

                String responseBody = response.body().string();

                if (response.isSuccessful()) {
                    callback.onSuccess(responseBody);
                } else {
                    callback.onError(
                            "Supabase error "
                                    + response.code()
                                    + ": "
                                    + responseBody
                    );
                }

            } catch (IOException e) {
                callback.onError(
                        "Connection error: " + e.getMessage()
                );
            }

        }).start();
    }

    public interface SupabaseCallback {
        void onSuccess(String response);
        void onError(String error);
    }
}