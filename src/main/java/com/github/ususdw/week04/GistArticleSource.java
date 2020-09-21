package com.github.ususdw.week04;

import com.github.ususdw.week04.data.Article;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class GistArticleSource {
    private final String url;
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public GistArticleSource(String url) {
        this.url = url;
    }

    public List<Article> rip() {
        try (InputStream stream = new URL(url).openStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
            String text = readAll(reader);
            Type listType = new TypeToken<ArrayList<Article>>(){}.getType();
            return gson.fromJson(text, listType);
        } catch (Exception ex) {
            return null;
        }
    }

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
