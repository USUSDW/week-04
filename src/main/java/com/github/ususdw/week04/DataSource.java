package com.github.ususdw.week04;

import java.util.List;

public interface DataSource<T> {
    public List<T> getAll();
}
