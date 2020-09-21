package com.github.ususdw.week04;

import com.github.ususdw.week04.data.Article;
import com.github.ususdw.week04.data.Author;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        var articleDest = new LocalJsonArticleStore("src/main/resources/articles.json", null);
        CommandHandler handler = new CommandHandler(articleDest,
                List.of(articleDest,
                        new RemoteJsonArticleStore("https://gist.githubusercontent.com/hhenrichsen/c63287e1780258e270c13e806c4608b5/raw/94747096ee347805ed90e5d9d7aa19bcc6583ecd/data.json", null)),
                List.of(new LocalJsonAuthorStore("src/main/resources/authors.json"),
                        new RemoteJsonAuthorStore("https://gist.githubusercontent.com/hhenrichsen/c63287e1780258e270c13e806c4608b5/raw/3e0290937dcf6aaa178a2bf3fee1685506921579/authors.json")));
        Scanner scanner = new Scanner(System.in);
        String input;
        do {
            System.out.print("?> ");
            input = scanner.nextLine();
            if (!input.equals("q")) {
                handler.handleCommand(input);
            }
        } while (!input.equalsIgnoreCase("q"));
    }
}
