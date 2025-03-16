package com.splguyjr.toyproject.miniboj.persistence;

import java.util.List;

public interface Storage<T> {
    void save(List<T> list);
    List<T> load();
}
