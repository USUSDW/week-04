package com.github.ususdw.week04;

import com.github.ususdw.week04.data.Article;
import com.github.ususdw.week04.util.FileUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class LocalJsonArticleStore {

    private final File file;
    private final Type listType = new TypeToken<ArrayList<Article>>(){}.getType();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private final LocalJsonAuthorStore authorStore;

    public LocalJsonArticleStore(String path,
        LocalJsonAuthorStore authorStore) {
        file = new File(path);
        this.authorStore = authorStore;
    }

    public List<Article> getArticles() {
        if (!file.exists()) {
            return List.of();
        }
        try {
            var reader = new FileReader(file);
            var text = FileUtils.readAll(reader);
            reader.close();
            List<Article> articles =  gson.fromJson(text, listType);
            for (var article : articles) {
                article.setAuthorStore(authorStore);
            }
            return articles;
        } catch (IOException e) {
            return List.of();
        }
    }

    public void addAuthor(Article article) throws IOException {
        if (!file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
        }
        else {
            var articles = getArticles();
            articles.add(article);
            String text = gson.toJson(articles);
            var writer = new FileWriter(file, StandardCharsets.UTF_8, false);
            writer.write(text);
            writer.close();
        }
    }
}
