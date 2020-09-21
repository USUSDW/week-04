package com.github.ususdw.week04;

import com.github.ususdw.week04.data.Article;
import com.github.ususdw.week04.data.Author;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        CommandHandler handler = new CommandHandler();
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
