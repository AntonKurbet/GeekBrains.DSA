package ru.geekbrains.dsa.lesson6;

import java.util.Objects;

public class Garden {
    private static class Cat {
        int age;
        String name;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Cat cat = (Cat) o;
            return age == cat.age &&
                    Objects.equals(name, cat.name);
        }

        public Cat(int age, String name) {
            this.age = age;
            this.name = name;
        }

        @Override
        public String toString() {
            //return String.format("C(%s, %d)", name, age);
            return Integer.toString(age);
        }
    }
    private static class Tree {
        private static class TreeNode implements Comparable {
            private Cat c;
            public TreeNode left;
            public TreeNode right;

            public TreeNode(Cat c) {
                this.c = c;
            }

            @Override
            public String toString() {
                return String.format("TN(%s)", c);
            }

            @Override
            public int compareTo(Object o) {
                if (!(o instanceof Cat))
                    throw new ClassCastException("Not a cat!");
                return c.age - ((Cat) o).age;
            }
        }
        TreeNode root;

        public void insert(Cat c) {
            TreeNode node = new TreeNode(c);
            if (root == null) {
                root = node;
            } else {
                TreeNode current = root;
                TreeNode parent;
                while (true) {
                    parent = current;
                    if (c.age < current.c.age) {
                        current = current.left;
                        if (current == null) {
                            parent.left = node;
                            return;
                        }
                    } else if (c.age > current.c.age) {
                        current = current.right;
                        if (current == null) {
                            parent.right = node;
                            return;
                        }
                    } else {
                        return;
                    }
                }
            }
        }
        public Cat find(int age) {
            TreeNode current = root;
            while (current.c.age != age) {
                current = (age < current.c.age) ? current.left : current.right;
                if (current == null) return null;
            }
            return current.c;
        }
        public void preOrderTraverse(TreeNode currentNode) {
            if (currentNode != null) {
                System.out.println(currentNode);
                System.out.print("L:");
                preOrderTraverse(currentNode.left);
                System.out.print("R:");
                preOrderTraverse(currentNode.right);
            } else System.out.println("null");
        }
        public int leftRootRight(TreeNode currentNode,int c) {
            int l = c;
            int r = c;

            if (currentNode != null) {
                leftRootRight(currentNode.left,l++);
                //System.out.println(currentNode);
                leftRootRight(currentNode.right,r++);
            }
            return r - l;
        }
        public void rightLeftRoot(TreeNode currentNode) {
            if (currentNode != null) {
                rightLeftRoot(currentNode.right);
                rightLeftRoot(currentNode.left);
                System.out.println(currentNode);
            }
        }
        public void displayTree() {
            preOrderTraverse(root);
        }
        public Cat delete(int age)  {
            TreeNode current = root;
            TreeNode parent = root;
            boolean isLeftChild = true;
            while (current.c.age != age) {
                parent = current;
                if (age < current.c.age) {
                    current = current.left;
                    isLeftChild = true;
                } else  {
                    current = current.right;
                    isLeftChild = false;
                }
                if (current == null) {
                    return null;
                }
            }
            //leaf
            if (current.left == null && current.right == null) {
                if (current == root) {
                    root = null;
                } else if (isLeftChild) {
                    parent.left = null;
                } else {
                    parent.right = null;
                }
            }
            // one ancestor
            else if (current.right == null) {
                if (isLeftChild)
                    parent.left = current.left;
                else
                    parent.right = current.left;
            }
            else if (current.left == null) {
                if (isLeftChild)
                    parent.left = current.right;
                else
                    parent.right = current.right;
            }
            // two ancestors
            else {
                TreeNode successor = getSuccessor(current);
                if (current == root) {
                    root = successor;
                } else if (isLeftChild) {
                    parent.left = successor;
                } else {
                    parent.right = successor;
                }
                successor.left = current.left;
            }
            return current.c;
        }

        private TreeNode getSuccessor(TreeNode node) {
            TreeNode current = node.right;
            TreeNode parent = node;
            TreeNode successor = node;
            while (current != null) {
                parent = successor;
                successor = current;
                current = current.left;
            }

            if (successor != node.right) {
                parent.left = successor.right;
                successor.right = node.right;
            }
            return successor;
        }

        public int height(TreeNode n)
        {
            return n == null ? 0 : 1 + Math.max(height(n.left), height(n.right));
        }

        // Где-то видимо зациклился, поэтому так сложно
        public void insertRecursive(Cat c) {
            if (root == null) root = insertRecursive(root,c);
            insertRecursive(root,c);
        }

        private TreeNode insertRecursive(TreeNode current, Cat c) {
            if (current == null) {
                return new TreeNode(c);
            } else {
                TreeNode node;
                if (current.compareTo(c) > 0) {
                    node = insertRecursive(current.left, c);
                    if (node != null) current.left = node;
                } else {
                    node = insertRecursive(current.right,c);
                    if (node != null) current.right = node;
                }
                return null;
            }
        }
    }

//    1. Создать и запустить программу для построения двоичного дерева. В цикле построить двадцать деревьев.
//    Данные, которыми необходимо заполнить узлы деревьев, представляются в виде чисел типа int.
//    Число, которое попадает в узел, должно генерироваться случайным образом в диапазоне от -100 до 100.
//    2. Проанализировать, какой процент созданных деревьев являются несбалансированными.
//    3. * Переписать метод добавления элемента в дерево с использованием компаратора и рекурсивного подхода

    private static Tree createTree(int count) {
        Tree result = new Tree();
        for (int i = 0; i < count; i++) {
            Cat c = new Cat(100 - (int)(Math.random() * 200), "Cat" + i );
            result.insert(c);
        }
        return result;
    }

//АВЛ-дерево — сбалансированное по высоте двоичное дерево поиска: для каждой
// его вершины высота её двух поддеревьев различается не более чем на 1.

    public static void main(String[] args) {
        int bal = 0;
        int unbal = 0;
        for (int i = 0; i < 20; i++) {
            Tree t = createTree(20);
            if (Math.abs(t.height(t.root.left) - t.height(t.root.right)) <= 1)
                bal++;
            else
                unbal++;
        }
        System.out.printf("Unbalanced percent: %6.2f", 100f * unbal / (bal + unbal));

        Tree t = new Tree();
        Tree tr = new Tree();

        for (int i = 0; i < 10; i++) {
            Cat c = new Cat(100 - (int)(Math.random() * 200), "Cat" + i );
            t.insert(c);
            tr.insertRecursive(c);
        }
        System.out.println("Iterative");
        t.displayTree();
        System.out.println("Recursive");
        tr.displayTree();
    }
}