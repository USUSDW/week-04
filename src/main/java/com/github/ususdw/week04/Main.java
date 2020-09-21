package com.github.ususdw.week04;

import com.github.ususdw.week04.data.Article;
import com.github.ususdw.week04.data.Author;
import java.util.HashMap;

public class Main {
    private HashMap<String, Article> articles;
    private HashMap<String, Author> authors;

    public static void main(String[] args) {
        var authorStore = new LocalJsonAuthorStore("src/main/resources/authors.json");
        var remoteArticleStore = new RemoteJsonArticleStore("https://gist.githubusercontent.com/hhenrichsen/c63287e1780258e270c13e806c4608b5/raw/94747096ee347805ed90e5d9d7aa19bcc6583ecd/data.json", authorStore);
        var articleStore = new LocalJsonArticleStore("src/main/resources/articles.json", authorStore);
        System.out.println(remoteArticleStore.downloadArticles());
        System.out.println(articleStore.getArticles().get(0).getAuthor());
    }
}
