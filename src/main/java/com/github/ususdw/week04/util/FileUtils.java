package com.github.ususdw.week04.util;

import java.io.IOException;
import java.io.Reader;

public final class FileUtils {
    private FileUtils(){}

    public static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }
}
