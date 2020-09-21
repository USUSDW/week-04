package com.github.ususdw.examples;

import java.util.Iterator;

public class LinkedList<T> {
    private LinkedNode<T> head;
    private LinkedNode<T> tail;

    public LinkedList() {}

    public void add(T data) {
        if (head == null) {
            head = new LinkedNode<>(data);
            tail = head;
        }
        else {
            LinkedNode<T> tmp = tail;
            tail = new LinkedNode<>(data);
            tmp.next = tail;
        }
    }

    public Iterator<T> iterator() {
        return new LinkedListIterator<>(this);
    }

    private static class LinkedNode<E> {
        private E data;
        private LinkedNode<E> next;

        private LinkedNode(E data) {
            this.data = data;
            this.next = null;
        }
    }

    private static class LinkedListIterator<E> implements Iterator<E> {
        private LinkedNode<E> current;

        public LinkedListIterator(LinkedList<E> list) {
            this.current = list.head;
        }

        @Override
        public boolean hasNext() {
            return current == null;
        }

        @Override
        public E next() {
            LinkedNode<E> tmp = current;
            current = current.next;
            return tmp.data;
        }
    }

}
