package ru.geekbrains.dsa.lesson4;

public class DoubleLinkedList<T> {

    private class Node<T> {

    }

    private class DoubleListIterator implements ListIterator<T> {

        @Override
        public void reset() {

        }

        @Override
        public boolean atEnd() {
            return false;
        }

        @Override
        public boolean atBegin() {
            return false;
        }

        @Override
        public T deleteCurrent() {
            return null;
        }

        @Override
        public void insertAfter(Cat c) {

        }

        @Override
        public void insertBefore(Cat c) {

        }

        @Override
        public T getCurrent() {
            return null;
        }

        @Override
        public boolean hasNext() {
            return false;
        }

        @Override
        public T next() {
            return null;
        }
    }

    private Node<T> head;
    private Node<T> tail;
    private ListIterator<T> iterator;

    public DoubleLinkedList() {
        head = null;
        tail = null;
        iterator = new DoubleListIterator();
        iterator.reset();
    }
}
