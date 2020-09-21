package com.github.ususdw.week04;

import com.github.ususdw.week04.data.Article;
import com.github.ususdw.week04.data.Author;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class CommandHandler {
    private final Scanner scanner = new Scanner(System.in);
    private final HashMap<String, Article> articles = new HashMap<>();
    private final HashMap<String, Author> authors = new HashMap<>();
    private final MutableStore<Article> articleDestination;
    private final MutableStore<Author> authorDestination = new MemoryAuthorStore();

    public CommandHandler(MutableStore<Article> articleDestination, List<ImmutableStore<Article>> articles, List<ImmutableStore<Author>> authors) {
        this.articleDestination = articleDestination;
        for (var source : authors) {
            authorDestination.duplicate(source);
            for (var author : source.getAll()) {
                this.authors.put(author.getName(), author);
            }
        }
        for (var source : articles) {
            articleDestination.duplicate(source);
            for (var article : source.getAll()) {
                article.setAuthorStore(authorDestination);
                this.articles.put(article.getId(), article);
            }
        }
    }

    public void handleCommand(String string) {
        if (string.equalsIgnoreCase("l")) {
            listArticles();
        }
        else if (string.equalsIgnoreCase("a")) {
            addArticle();
        }
        else if (string.equalsIgnoreCase(("v"))) {
            displayArticle();
        }
        else if (string.equalsIgnoreCase("h")) {
            displayHelp();
        }
        else {
            System.out.println("Unknown command '" + string + ".'");
            displayHelp();
        }
    }

    public void listArticles() {
        System.out.println("Favorite Article Listing:");
        for (var article : articles.values()) {
            System.out.println(" - " + article.getName() + " (" + article.getId() + ") by " + article.getAuthor().getName());
        }
    }

    public void displayArticle() {
        System.out.print("Article ID: ");
        String articleID = scanner.nextLine();
        if (!articles.containsKey(articleID)) {
            System.out.println("Invalid Article ID.");
        }
        var article = articles.get(articleID);
        var author = article.getAuthor();
        System.out.println(article.getName());
        System.out.println("by " + author.getName());
        System.out.println("-----------------------------");
        System.out.println(article.getPreview());
        System.out.println("-----------------------------");
        System.out.println("View more at: " + article.getSource());
    }

    public void addArticle() {
        System.out.println("Adding an Article. Leave fields blank to cancel.");
        System.out.print("ID: ");
        String id = scanner.nextLine();
        if (id.isEmpty()) {
            System.out.println("Canceled.");
            return;
        }
        System.out.print("Title: ");
        String title = scanner.nextLine();
        if (title.isEmpty()) {
            System.out.println("Canceled.");
            return;
        }
        System.out.print("Author: ");
        String authorName = scanner.nextLine();
        if (authorName.isEmpty()) {
            System.out.println("Canceled.");
            return;
        }
        System.out.print("Preview: ");
        String preview = scanner.nextLine();
        if (preview.isEmpty()) {
            System.out.println("Canceled.");
            return;
        }
        System.out.print("Source: ");
        String source = scanner.nextLine();
        if (source.isEmpty()) {
            System.out.println("Canceled.");
            return;
        }
        Article article = new Article(id, title, authorName, preview, source, authorDestination);
        articleDestination.add(article);
    }

    private void viewAuthor() {
        System.out.print("Author Name: ");
        String name = scanner.nextLine();
        if (!authors.containsKey(name)) {
            System.out.println("Unknown author.");
            return;
        }
        var author = authors.get(name);
        System.out.println(author.getName());
        System.out.println(author.getBio());
        System.out.println("Find them on:");
        for (var entry : author.getSocial().entrySet()) {
            System.out.println(" - " + entry.getKey() + " as " + entry.getValue());
        }
    }

    private void displayHelp() {
        System.out.println(" - a - (Add) Add article");
        System.out.println(" - l - (List) List articles");
        System.out.println(" - v - (View) View articles");
        System.out.println(" - w - (Who) View Author");
        System.out.println(" - h - (Help) View this message");
        System.out.println(" - q - (Quit) Exit the program.");
    }
}
