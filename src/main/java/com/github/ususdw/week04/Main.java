package com.github.ususdw.week04;

public class Main {
    public static void main(String[] args) {
        GistArticleSource src = new GistArticleSource("https://gist.githubusercontent.com/hhenrichsen/c63287e1780258e270c13e806c4608b5/raw/94747096ee347805ed90e5d9d7aa19bcc6583ecd/data.json");
        System.out.println(src.rip());
    }
}
