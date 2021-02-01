package ru.geekbrains.dsa.lesson4;

import java.util.Iterator;

public interface ListIterator<T> extends Iterator<T> {
    void reset();
    boolean atEnd();
    boolean atBegin();
    T deleteCurrent();
    void insertAfter(Cat c);
    void insertBefore(Cat c);
    T getCurrent();
}
