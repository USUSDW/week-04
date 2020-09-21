package com.github.ususdw.week04.data;

public class Article {
    public String id;
    public String name;
    public String author;
    public String preview;
    public String source;

    @Override
    public String toString() {
        return "Article{" +
                "id='" + id + '\'' +
                ", title='" + name + '\'' +
                ", author='" + author + '\'' +
                ", preview='" + preview + '\'' +
                ", source='" + source + '\'' +
                '}';
    }

    public Article(String id, String name, String author, String preview, String source) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.preview = preview;
        this.source = source;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
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
}
