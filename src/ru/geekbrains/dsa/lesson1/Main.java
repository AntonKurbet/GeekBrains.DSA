package ru.geekbrains.dsa.lesson1;

public class Main {
    public static void main(String[] args) {
//        1. Описать простейшие алгоритмы
//        1.1. Возведение в степень *используя чётность степени*
//        1.2. Поиск минимального элемента в массиве
//        1.3. Нахождение среднего арифметического массива
//        2. Подсчитать сложность описанных алгоритмов
//        3. Какие правила подсчёта применялись, оставьте комментарии в коде

        System.out.printf("Power 3^7 = %8.2f\n", power(3,7));
        int[] a = {1,3,5,7,9,8,6,4,2};
        System.out.printf("Minimum is %d\n", min(a));
        System.out.printf("Average is %8.2f\n", avg(a));
    }

    // сложность O(log n) , т.к. в наихудшем случае это похоже на бинарный поиск
    private static float power(float a, int n) {
        if (n == 0)
            return 1f; // O(1)
        else if (n == 1)
            return a;  // O(1)
        else
            if (n % 2 == 1)
                return power(a,n - 1) * a;
            else {
                float tmp = power(a,n / 2);  // O(log n)
                return tmp * tmp;
            }
    }

    // сложность O(n) , т.к. это полный перебор n элементов
    private static int min(int[] arr) {
        int tmp = arr[0];
        for (int i = 1; i < arr.length; i++) {
            if (tmp > arr[i]) tmp = arr[i];
        }
        return tmp;
    }

    // сложность O(n) , т.к. это полный перебор n элементов
    private static float avg(int[] arr) {
        int tmp = arr[0];
        for (int i = 1; i < arr.length; i++) {
            tmp += arr[i];
        }
        return (float)tmp / arr.length;
    }
}
