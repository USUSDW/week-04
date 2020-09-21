package com.github.ususdw.week04;

import com.github.ususdw.week04.data.Author;
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

public class LocalJsonAuthorStore implements MutableStore<Author> {
    private final File file;
    private final Type listType = new TypeToken<ArrayList<Author>>(){}.getType();
    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public LocalJsonAuthorStore(String path) {
        file = new File(path);
    }

    public List<Author> getAll() {
        if (!file.exists()) {
            return List.of();
        }
        try {
            var reader = new FileReader(file);
            var text = FileUtils.readAll(reader);
            reader.close();
            return gson.fromJson(text, listType);
        } catch (IOException e) {
            return List.of();
        }
    }

    public void add(Author author) {
        try {

            if (!file.exists()) {
                //noinspection ResultOfMethodCallIgnored
                file.createNewFile();
            }
            else {
                var authors = getAll();
                authors.add(author);
                String text = gson.toJson(authors);
                var writer = new FileWriter(file, StandardCharsets.UTF_8, false);
                writer.write(text);
                writer.close();
            }
        }
        catch (Exception ex) {
            System.err.println("Failed to save author:");
            ex.printStackTrace();
        }
    }
}
