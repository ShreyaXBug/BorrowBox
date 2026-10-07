package com.siestech.booksharing.Utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.siestech.booksharing.Model.Book;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class LocalBookStore {
    private static final String PREFS = "borrowbox_local_books";
    private static final String KEY = "books";

    public static void save(Context context, Book book) {
        try {
            SharedPreferences sp = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
            JSONArray array = new JSONArray(sp.getString(KEY, "[]"));
            JSONObject o = new JSONObject();
            o.put("id", book.getId());
            o.put("title", book.getTitle());
            o.put("author", book.getAuthor());
            o.put("category", book.getCategory());
            o.put("description", book.getDescription());
            o.put("ownerId", book.getOwnerId());
            o.put("ownerName", book.getOwnerName());
            o.put("ownerEmail", book.getOwnerEmail());
            o.put("condition", book.getCondition());
            o.put("imageName", book.getImageName());
            o.put("available", book.isAvailable());
            o.put("createdAt", book.getCreatedAt());
            array.put(o);
            sp.edit().putString(KEY, array.toString()).apply();
        } catch (Exception ignored) { }
    }

    public static ArrayList<Book> load(Context context) {
        ArrayList<Book> result = new ArrayList<>();
        try {
            SharedPreferences sp = context.getSharedPreferences(PREFS, Context.MODE_PRIVATE);
            JSONArray array = new JSONArray(sp.getString(KEY, "[]"));
            for (int i = 0; i < array.length(); i++) {
                JSONObject o = array.getJSONObject(i);
                result.add(new Book(
                        o.optString("id"),
                        o.optString("title"),
                        o.optString("author"),
                        o.optString("category"),
                        o.optString("description"),
                        o.optString("ownerId"),
                        o.optString("ownerName"),
                        o.optString("ownerEmail"),
                        o.optString("condition"),
                        o.optString("imageName", "book1"),
                        o.optBoolean("available", true),
                        o.optLong("createdAt")
                ));
            }
        } catch (Exception ignored) { }
        return result;
    }
}
