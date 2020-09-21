package com.github.ususdw.week04.data;

import com.github.ususdw.week04.LocalJsonAuthorStore;
import com.google.gson.annotations.SerializedName;

public class Article {

    private String id;
    private String name;
    @SerializedName("author")
    private String authorName;
    private String preview;
    private String source;

    private LocalJsonAuthorStore authorStore;

    @Override
    public String toString() {
        return "Article{" +
                "id='" + id + '\'' +
                ", title='" + name + '\'' +
                ", author='" + authorName + '\'' +
                ", preview='" + preview + '\'' +
                ", source='" + source + '\'' +
                '}';
    }

    public Article(String id, String name, String authorName, String preview, String source,
        LocalJsonAuthorStore authorStore) {
        this.id = id;
        this.name = name;
        this.authorName = authorName;
        this.preview = preview;
        this.source = source;
        this.authorStore = authorStore;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Author getAuthor() {
        return authorStore.getAuthors().stream().filter((it) -> it.getName().equalsIgnoreCase(authorName)).findAny().get();
    }

    public void setAuthor(String author) {
        this.authorName = author;
    }

    public String getPreview() {
        return preview;
    }

    public void setPreview(String preview) {
        this.preview = preview;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setAuthorStore(LocalJsonAuthorStore authorStore) {
        this.authorStore = authorStore;
    }
}
