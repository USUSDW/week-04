package com.github.ususdw.week04;

import com.github.ususdw.week04.data.Author;

import java.util.LinkedList;
import java.util.List;

public class MemoryAuthorStore implements MutableStore<Author> {
    private List<Author> authors = new LinkedList<>();

    @Override
    public void add(Author item) {
        authors.add(item);
    }

    @Override
    public List<Author> getAll() {
        return List.copyOf(authors);
    }
}
