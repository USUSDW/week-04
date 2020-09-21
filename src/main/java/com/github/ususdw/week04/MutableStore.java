package com.github.ususdw.week04;

public interface MutableStore<T> extends ImmutableStore<T> {
    void add(T item);
    default void duplicate(ImmutableStore<T> other) {
        for (var item : other.getAll()) {
            add(item);
        }
    }
}
