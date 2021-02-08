package ru.geekbrains.dsa.lesson7;

import ru.geekbrains.dsa.lesson3.Main.*;

public class Manor {

    private static class Graph {
        private class Vertex {
            char label;
            boolean wasVisited;
            private int prev;


            public Vertex(char label) {
                this.label = label;
                this.wasVisited = false;
                this.prev = -1;
            }

            @Override
            public String toString() {
                return String.format("V=%c", label);
            }
        }

        private final int MAX_VERTICES = 16;
        private Vertex[] vertexList;
        private int[][] adjacencyMatrix;
        private int currentSize;

        public Graph() {
            vertexList = new Vertex[MAX_VERTICES];
            adjacencyMatrix = new int[MAX_VERTICES][MAX_VERTICES];
            currentSize = 0;
        }
        public void addVertex(char label) {
            vertexList[currentSize++] = new Vertex(label);
        }
        public void addEdge(int start, int end) {
            adjacencyMatrix[start][end] = 1; // change 1 to weight for weight
            adjacencyMatrix[end][start] = 1; // delete this for direction
        }
        public void displayVertex(int v) {
            System.out.print(vertexList[v] + " ");
        }
        private int getUnvisitedVertex(int current) {
            for (int i = 0; i < currentSize; i++) {
                if (adjacencyMatrix[current][i] == 1 &&
                        !vertexList[i].wasVisited) {
                    return i;
                }
            }
            return -1;
        }
        public void depthTraverse() {
            Stack stack = new Stack(MAX_VERTICES);
            vertexList[0].wasVisited = true;
            displayVertex(0);
            stack.push(0);
            while (!stack.isEmpty()) {
                int v = getUnvisitedVertex(stack.peek());
                if (v == -1) {
                    stack.pop();
                } else {
                    vertexList[v].wasVisited = true;
                    displayVertex(v);
                    stack.push(v);
                }
            }
            resetFlags();
        }
        public void widthTraverse() {
            Queue queue = new Queue(MAX_VERTICES);
            vertexList[0].wasVisited = true;
            queue.insert(0);
            while (!queue.isEmpty()) {
                int current = queue.remove();
                displayVertex(current);
                int next;
                while ((next = getUnvisitedVertex(current)) != -1) {
                    vertexList[next].wasVisited = true;
                    queue.insert(next);
                }
            }
            resetFlags();
        }
        private void resetFlags() {
            for (int i = 0; i < currentSize; i++) {
                vertexList[i].wasVisited = false;
            }
        }
        public void pathFinder(int from, int to) {
            Queue queue = new Queue(MAX_VERTICES);
            Stack path = new Stack(MAX_VERTICES);

            boolean reached = false;
            vertexList[from].wasVisited = true;
            queue.insert(from);
            while (!queue.isEmpty()) {
                int current = queue.remove();
//                displayVertex(current);
                int next;
                while ((next = getUnvisitedVertex(current)) != -1) {
                    vertexList[next].wasVisited = true;
                    vertexList[next].prev = current;
                    queue.insert(next);
                    if (next == to) {
                        reached = true;
                        break;
                    }
                }
                if (next == to) break;
            }
            if (reached) {
                int current = to;
                while (current != from) {
                    path.push(current);
                    current = vertexList[current].prev;
                }
                path.push(current);
                while(!path.isEmpty())
                    System.out.print(path.pop() + " ");
            }
            resetFlags();
        }
    }

//    1. Реализовать программу, в которой задается граф из 10 вершин.
//    Задать ребра и найти кратчайший путь с помощью поиска в ширину.
    public static void main(String[] args) {
        Graph g = new Graph();
//        g.addVertex('a');
//        g.addVertex('b');
//        g.addVertex('c');
//        g.addVertex('d');
//        g.addVertex('e');
//        g.addEdge(1, 2);
//        g.addEdge(2, 0);
//        g.addEdge(3, 4);
//        g.addEdge(0, 4);
//        g.depthTraverse();
//        System.out.println();
//        g.widthTraverse();

        for (int i = 0; i < 10; i++) {
            g.addVertex((char) ('a' + i));
        }
        g.addEdge(0, 1);
        g.addEdge(0, 2);
        g.addEdge(1, 3);
        g.addEdge(1, 4);
        g.addEdge(2, 4);
        g.addEdge(2, 5);
        g.addEdge(3, 7);
        g.addEdge(4, 7);
        g.addEdge(4, 6);
        g.addEdge(5, 6);
        g.addEdge(6, 8);
        g.addEdge(7, 8);
//        g.addEdge(0, 3);
//        g.addEdge(3, 9);
        g.addEdge(7, 9);


        g.pathFinder(0,9);
    }
}