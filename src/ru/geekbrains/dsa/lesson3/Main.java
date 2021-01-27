package ru.geekbrains.dsa.lesson3;


import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Main {
    private static class Stack {
        private int[] stack;
        private int head;

        public Stack(int size) {
            this.stack = new int[size];
            this.head = -1;
        }

        public boolean isEmpty() {
            return head == -1;
        }

        public boolean isFull() {
            return head == stack.length - 1;
        }

        public boolean push(int i) {
            if (isFull()) return false;
            stack[++head] = i;
            return true;
        }

        public int pop() throws RuntimeException {
            if (isEmpty()) throw new RuntimeException("Stack is empty");
            return stack[head--];
        }

        public int peek() throws RuntimeException {
            if (isEmpty()) throw new RuntimeException("Stack is empty");
            return stack[head];
        }

    }

    private static int checkBrackets(String input) {
        int size = input.length();
        Stack st = new Stack(size);
        for (int i = 0; i < size; i++) {
            char ch = input.charAt(i);
            if (ch == '[' || ch == '(' || ch == '<' || ch == '{') {
                st.push(ch);
            } else if (ch == ']' || ch == ')' || ch == '>' || ch == '}') {
                if (st.isEmpty())
                    return i;

                char op = (char) st.pop();
                if (!((op == '[' && ch == ']') ||
                        (op == '{' && ch == '}') ||
                        (op == '(' && ch == ')') ||
                        (op == '<' && ch == '>'))) {
                    return i;
                }
            }
        }
        if (!st.isEmpty()) {
            return size;
        }
        return -1;
    }

    private static class Queue {
        protected int[] queue;
        protected int head;
        protected int tail;
        protected int capacity;

        public Queue(int initial) {
            queue = new int[initial];
            head = 0;
            tail = -1;
            capacity = 0;
        }

        public boolean isEmpty() {
            return capacity == 0;
        }

        public boolean isFull() {
            return capacity == queue.length;
        }

        public int length() {
            return capacity;
        }

        public void insert(int i) {
            if (isFull())
                throw new RuntimeException("Queue is full!");
            if (tail == queue.length -1)
                tail = -1;
            queue[++tail] = i;
            capacity++;
        }

        public int remove() {
            if (isEmpty()) throw new RuntimeException("Queue is empty");
            int temp = queue[head++];
            head %= queue.length; //if (head == queue.length) head = 0;
            capacity--;
            return temp;
        }

    }

//  1. Создать программу, которая переворачивает вводимые строки (читает справа налево) при помощи стека.
    private static String InvertString(String s) {
        byte[] chars = s.getBytes(StandardCharsets.UTF_8);
        Stack stack = new Stack(chars.length);
        byte[] result = new byte[chars.length];
        int i;

        for (i = 0; i < chars.length; i++) {
            stack.push(chars[i]);
        }

        i = 0;
        while (!stack.isEmpty()) {
            result[i++] = (byte)stack.pop();
        }
        return new String(result,StandardCharsets.UTF_8);
    }

//  2. Создать класс для реализации дека.
//    https://ru.wikipedia.org/wiki/Двухсторонняя_очередь
//    Типовые операции
//    PushBack — добавление в конец очереди.
//    PushFront — добавление в начало очереди.
//    PopBack — выборка с конца очереди.
//    PopFront — выборка с начала очереди.
//    IsEmpty — проверка наличия элементов.
//    Clear — очистка.

    private static class Deque extends Queue{

        Deque(int initial) {
            super(initial);
        }

        public void pushBack(int i) {
            insert(i);
        }

        public int popFront() {
            return remove();
        }

        public void pushFront (int i) {
            if (isFull())
                throw new RuntimeException("Deque is full!");
            if (head == 0)
                head = queue.length;
            queue[--head] = i;
            capacity++;
        }

        public int popBack() {
            if (isEmpty()) throw new RuntimeException("Deque is empty");
            int temp = queue[tail--];
            if (tail == -1)
                tail = queue.length - 1;
            capacity--;
            return temp;
        }

        public void Clear() {
            head = 0;
            tail = -1;
            capacity = 0;
        }

        @Override
        public String toString() {
            return Arrays.toString(queue);
        }
    }

    //  3. Создать класс с реализацией приоритетной очереди
    // https://ru.wikipedia.org/wiki/Очередь_с_приоритетом_(программирование)
    // Основные методы, реализуемые очередью с приоритетом, следующие:
    //insert(ключ, значение) — добавляет пару (ключ, значение) в хранилище;
    //extract_minimum() — возвращает пару (ключ, значение) с минимальным значением ключа, удаляя её из хранилища.
    //При этом меньшее значение ключа соответствует более высокому приоритету.

    private static class PriorityQueue {
        private int[] data;
        private int[] prior;
        private int capacity;

        public PriorityQueue(int initial) {
            data = new int[initial];
            prior = new int[initial];
            capacity = 0;
        }

        public boolean isEmpty() {
            return capacity == 0;
        }

        public boolean isFull() {
            return capacity == data.length;
        }

        public int length() {
            return capacity;
        }

        public void insert(int i, int p) {
            if (isFull())
                throw new RuntimeException("Queue is full!");
            data[capacity] = i;
            prior[capacity] = p;
            capacity++;
        }

        private int findTopPrior() {
            int j = 0;
            int min = prior[j];

            for (int i = 1; i < capacity ; i++) {
                if (prior[i] < min) {
                    min = prior[i];
                    j = i;
                }
            }
            return j;
        }

        public int remove() {
            if (isEmpty()) throw new RuntimeException("Queue is empty");
            int i = findTopPrior();
            int temp = data[i];
            System.arraycopy(data,i + 1, data, i, capacity - i);
            System.arraycopy(prior,i + 1, prior, i, capacity - i);
            capacity--;
            return temp;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder("{");
            for (int i = 0; i < capacity; i++) {
                sb.append('[')
                        .append(data[i])
                        .append(',')
                        .append(prior[i])
                        .append(']')
                        .append(';');
            }
            sb.setLength(sb.length() - 1);
            sb.append('}');
            return sb.toString();
        }

    }

    public static void main(String[] args) {
        //System.out.println(checkBrackets("<> () [] {} {[() <>]}"));
//        String s = "LeftToRight";
//        System.out.println(s);
//        System.out.println(InvertString(s));
        //Deque
//        dequeTest();
        //Priority Queue
        priorQueueTest();
    }

    private static void priorQueueTest() {
        PriorityQueue p = new PriorityQueue(5);
        p.insert(1,0);
        p.insert(2,5);
        p.insert(3,1);
        System.out.println(p.toString());
        System.out.println(p.remove());
        System.out.println(p.toString());
        System.out.println(p.remove());
        System.out.println(p.toString());
    }

    private static void dequeTest() {
        Deque d = new Deque(5);
        d.pushFront(1);
        d.pushFront(2);
        d.pushFront(3);
        System.out.println(d.toString());
        d.pushBack(4);
        System.out.println(d.toString() + " " + d.capacity);
        System.out.println(d.popBack() + " " + d.capacity);
        System.out.println(d.popFront() + " " + d.capacity);
        System.out.println(d.popBack() + " " + d.capacity);
        System.out.println(d.popFront() + " " + d.capacity);
    }


}
