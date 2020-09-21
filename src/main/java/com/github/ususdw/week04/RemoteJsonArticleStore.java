package com.github.ususdw.week04;

import com.github.ususdw.week04.data.Article;
import com.github.ususdw.week04.util.FileUtils;
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

public class RemoteJsonArticleStore {
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private final String url;
    private final LocalJsonAuthorStore authorStore;

    public RemoteJsonArticleStore(String url,
        LocalJsonAuthorStore authorStore) {
        this.url = url;
        this.authorStore = authorStore;
    }

    public List<Article> downloadArticles() {
        try (InputStream stream = new URL(url).openStream()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream, StandardCharsets.UTF_8));
            String text = FileUtils.readAll(reader);
            Type listType = new TypeToken<ArrayList<Article>>(){}.getType();
            List<Article> articles =  gson.fromJson(text, listType);
            for (Article it : articles) {
                it.setAuthorStore(authorStore);
            }
            return articles;
        } catch (Exception ex) {
            return null;
        }
    }
}
