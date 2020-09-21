package com.github.ususdw.week04;

import com.github.ususdw.week04.data.Author;
import com.github.ususdw.week04.data.Author;
import com.github.ususdw.week04.util.FileUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class RemoteJsonAuthorStore implements ImmutableStore<Author> {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private static final Type listType = new TypeToken<ArrayList<Author>>(){}.getType();

    private final String url;

    public RemoteJsonAuthorStore(String url) {
        this.url = url;
    }

    public List<Author> getAll() {
        try (var stream = new URL(url).openStream()) {
            var reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
            var text = FileUtils.readAll(reader);
            return gson.fromJson(text, listType);
        } catch (Exception ex) {
            return null;
        }
    }
}
