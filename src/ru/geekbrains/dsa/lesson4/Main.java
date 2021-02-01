package ru.geekbrains.dsa.lesson4;

import java.util.Objects;

public class Main {
    private static class SingleLinkedList {
        private static class Node {
            Cat c;
            Node next;

            public Node(Cat c) {
                this.c = c;
            }

            @Override
            public String toString() {
                return String.format("Node(c=%s)", c);
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Node node = (Node) o;
                return Objects.equals(c, node.c) &&
                        Objects.equals(next, node.next);
            }
        }
        protected Node head;
        private ListIterator iterator;

        public SingleLinkedList() {
            this.head = null;
            this.iterator = new ListIterator(this);
        }

        public boolean isEmpty() {
            return head == null;
        }

        public void push(Cat c) {
            Node n = new Node(c);
            n.next = head;
            head = n;
        }

        public Cat pop() {
            if (isEmpty()) return null;
            Cat temp = head.c;
            head = head.next;
            return temp;
        }

        public boolean contains(Cat c) {
            Node n = new Node(c);
            Node current = head;
            while (!current.equals(n)) {
                if (current.next == null) return false;
                else current = current.next;
            }
            return true;
        }

        public void delete(Cat c) {
            Node n = new Node(c);
            Node current = head;
            Node previous = null;
            while (!current.equals(n)) {
                if (current.next == null) return;
                else {
                    previous = current;
                    current = current.next;
                }
            }

            if (current == head) {
                head = head.next;
            } else {
                previous.next = current.next;
            }

            // return current.c

        }

        public ListIterator getIterator() {
            return iterator;
        }

        @Override
        public String toString() {
            Node cn = head;
            StringBuilder sb = new StringBuilder();
            while (cn != null) {
                sb.append(cn.c.toString());
                sb.append(" ");
                cn = cn.next;
            }
            return sb.toString();
        }

        public Node getHead() {
            return head;
        }
    }

    static class ListIterator {
        private final SingleLinkedList list;
        private SingleLinkedList.Node current;
        private SingleLinkedList.Node prev;

        public ListIterator(SingleLinkedList list) {
            this.list = list;
        }

        public void reset() {
            current = list.getHead();
            prev = null;
        }

        public void next() {
            prev = current;
            current = current.next;
        }

        public SingleLinkedList.Node getCurrent() {
            return current;
        }

        public boolean atEnd() {
            return current.next == null;
        }

        public void insertAfter(Cat c) {
            if (list.isEmpty()) {
                list.push(c);
            } else {
                SingleLinkedList.Node n = new SingleLinkedList.Node(c);
                n.next = current.next;
                current.next = n;
            }
        }

        public void insertBefore(Cat c) {
            if (list.isEmpty()) {
                list.push(c);
            } else {
                SingleLinkedList.Node n = new SingleLinkedList.Node(c);
                SingleLinkedList.Node tmp = prev;
                prev = n;
                prev.next = tmp.next;
                tmp.next = prev;
            }
        }

        public void deleteCurrent() {
            if (prev != null)
                prev.next = current.next;
            else {
                current = current.next;;
            }
        }

    }

    // class iteroCat
    // reset()
    // next(), prev(for dll)
    // getCurrent()
    // atEnd()
    // insertBefore();
    // insertAfter();
    // deleteCurrent();

    public static void main(String[] args) {
        SingleLinkedList list = new SingleLinkedList();
        list.push(new Cat(1,"Barsik"));
        list.push(new Cat(2,"Murzik"));
        list.push(new Cat(3,"Bandit"));
        ListIterator iterator = list.getIterator();
        iterator.reset();
//        while (iterator.getCurrent() != null) {
//            System.out.println(iterator.getCurrent().c.toString());
//            iterator.next();
//        }
        System.out.println(list.toString());
        iterator.reset();
        iterator.next();
        System.out.println(iterator.getCurrent().toString());
        iterator.insertAfter(new Cat(4,"Newbie"));
        System.out.println(list.toString());
        iterator.insertBefore(new Cat(5,"Tom"));
        System.out.println(list.toString());
        iterator.deleteCurrent();
        System.out.println(list.toString());
    }

}
