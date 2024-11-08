package org.example.interfaces;

import java.util.List;

public interface Service<T> {
    boolean add();
    boolean edit(T entity);
    boolean remove(T entity);
    void print();
    List<T> list();
}
