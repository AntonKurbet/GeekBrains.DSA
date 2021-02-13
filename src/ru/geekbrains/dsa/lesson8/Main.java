package ru.geekbrains.dsa.lesson8;

import java.util.ArrayList;

public class Main {
    public static class Item {
        private int data;

        public Item(int data) {
            this.data = data;
        }

        public int getData() {
            return data;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Item item = (Item) o;
            return data == item.data;
        }
    }

    public static class HashCat {
        private Item[] hashArray;
        private int arraySize;
        private static Item nullItem = new Item(-1);

        public HashCat(int arraySize) {
            this.arraySize = arraySize;
            this.hashArray = new Item[arraySize];
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < arraySize; i++) {
                sb.append((hashArray[i] != null) ? hashArray[i].getData() : "*");
                if (i < arraySize - 1)
                    sb.append(", ");
            }
            return sb.toString();
        }

        private int hashFunc(int key) {
            return key % arraySize;
        }

        private boolean isFull() {
            for (int i = 0; i < arraySize; i++)
                if (hashArray[i] == null || hashArray[i] == nullItem)
                    return false;
            return true;
        }

        private void increaseCapacity() {
            arraySize *= 2;
            Item[] oldArr = hashArray;
            hashArray = new Item[arraySize];
            for (int i = 0; i < oldArr.length; i++) {
                insert(oldArr[i]);
            }
        }

        // linear probe
        private int linearProbe(int hashVal) {
            ++hashVal;
            hashVal %= arraySize;
            return hashVal;
        }

        // quad probe
        private int quadProbe(int hashVal, int step) {
            hashVal += step * step;
            hashVal %= arraySize;
            return hashVal;
        }

        // double hash
        // shift = const - (key % const); const == prime number < arrSize;
        private int hashFuncDouble(int key) {
            return 11 - (key % 11);
        }

        public void insert(Item item) {
            int key = item.getData();
            int hashVal = hashFunc(key);
            int step = hashFuncDouble(key); //1;
            if (isFull()) increaseCapacity();
            while (hashArray[hashVal] != null && hashArray[hashVal] != nullItem) {
//                hashVal = linearProbe(hashVal);
//                hashVal = quadProbe(hashVal, ++step);
                hashVal += step;
                hashVal %= arraySize;
            }
            hashArray[hashVal] = item;
        }

        public Item find(int key) {
            int hashVal = hashFunc(key);
            int startVal = hashVal;
            int step = hashFuncDouble(key); //1;
            while (hashArray[hashVal] != null) {
                if (hashArray[hashVal].getData() == key)
                    return hashArray[hashVal];
//                hashVal = linearProbe(hashVal);
//                hashVal = quadProbe(hashVal, ++step);
                hashVal += step;
                hashVal %= arraySize;
                if (hashVal == startVal)
                    return null;
            }
            return null;
        }

        public Item delete(int key) {
            int hashVal = hashFunc(key);
            int step = hashFuncDouble(key); //1;
            while (hashArray[hashVal] != null) {
                if (hashArray[hashVal].getData() == key) {
                    Item temp = hashArray[hashVal];
                    hashArray[hashVal] = nullItem;
                    return temp;
                }
//                hashVal = linearProbe(hashVal);
//                hashVal = quadProbe(hashVal, ++step);
                hashVal += step;
                hashVal %= arraySize;
            }
            return null;

        }
    }

//    Метод цепочек
//    Этот метод часто называют открытым хешированием.
//    Его суть проста — элементы с одинаковым хешем попадают в одну ячейку в виде связного списка.
//    Hash chaining
//    То есть, если ячейка с хешем уже занята, но новый ключ отличается от уже имеющегося,
//    то новый элемент вставляется в список в виде пары ключ-значение.

    public static class ChainItem {
        private ArrayList<Item> data;

        public ChainItem() {
            data = new ArrayList<>();
        }

        public int size() {
            return data.size();
        }

        public Item get(int index) {
            return data.get(index);
        }

        public void add(Item item) {
            data.add(item);
        }

        public void set(int i, Item item) {
            data.set(i,item);
        }

        public void addAll(ChainItem items) {
            for (int i = 0; i < items.size(); i++) {
                if (!data.contains(items.get(i)))
                    data.add(items.get(i));
            }
        }

        public boolean find(Item item) {
            return data.contains(item);
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("{");
            for (int i = 0; i < data.size(); i++) {
                sb.append(data.get(i).getData());
                if (i < data.size() - 1)
                    sb.append(", ");
            }
            sb.append("}");
            return sb.toString();
        }
    }

    public static class HashChain {
        private ChainItem[] hashArray;
        private int arraySize;
        private static Item nullItem = new Item(-1);

        public HashChain(int arraySize) {
            this.arraySize = arraySize;
            this.hashArray = new ChainItem[arraySize];
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < arraySize; i++) {
                sb.append((hashArray[i] != null) ? hashArray[i] : "*");
                if (i < arraySize - 1)
                    sb.append(", ");
            }
            return sb.toString();
        }

        private int hashFunc(int key) {
            // for testing purpose & fast hash repeating ))
            String s = Integer.toString(key);
            int result = 0;
            for (int i = 0; i < s.length(); i++) {
                result = result + s.charAt(i) - '0';
            }
            return result;
        }

        private boolean isFull() {
            for (int i = 0; i < arraySize; i++)
                if (hashArray[i] != null)
                    for (int j = 0; j < hashArray[i].size(); j++) {
                        if (hashArray[i].get(j) == null)
                            return false;
                    }
                else return false;
            return true;
        }

        private void increaseCapacity() {
            arraySize *= 2;
            ChainItem[] oldArr = hashArray;
            hashArray = new ChainItem[arraySize];
            for (int i = 0; i < oldArr.length; i++) {
                insertAll(oldArr[i]);
            }
        }

        public void insert(Item item) {
            int key = item.getData();
            int hashVal = hashFunc(key);
            if (isFull()) increaseCapacity();
            if (hashArray[hashVal] == null) {
                hashArray[hashVal] = new ChainItem();
            }
            if (!hashArray[hashVal].find(item))
                hashArray[hashVal].add(item);
        }

        public void insertAll(ChainItem items) {
            if (items != null) {
                int key = items.get(0).getData();
                int hashVal = hashFunc(key);
                if (isFull()) increaseCapacity();
                if (hashArray[hashVal] == null) {
                    hashArray[hashVal] = items;
                }
                hashArray[hashVal].addAll(items);
            }
        }

        public ChainItem find(int key) {
            int hashVal = hashFunc(key);
            if (hashArray[hashVal] != null) {
                return hashArray[hashVal];
            }
            return null;
        }

        public ChainItem delete(int key) {
            int hashVal = hashFunc(key);
            if (hashArray[hashVal] != null) {
                ChainItem result = hashArray[hashVal];
                hashArray[hashVal] = null;
                return result;
            }
            return null;
        }
    }

    public static void main(String[] args) {
//        HashCat cat = new HashCat(25);
//        cat.insert(new Item(10));
//        cat.insert(new Item(20));
//        cat.insert(new Item(30));
//        cat.insert(new Item(40));
//        cat.insert(new Item(50));
//        cat.insert(new Item(75));
//        cat.insert(new Item(60));
//        cat.insert(new Item(70));
//        System.out.println(cat);
//        cat.delete(75);
//        System.out.println(cat);
        HashChain chain = new HashChain(10);
        chain.insert(new Item(10));
        chain.insert(new Item(11));
        chain.insert(new Item(20));
        chain.insert(new Item(2));
        chain.insert(new Item(30));
        chain.insert(new Item(21));
        chain.insert(new Item(12));
        chain.insert(new Item(50));
        System.out.println(chain);
        chain.delete(3);
        System.out.println(chain);
        System.out.println(chain.find(2));

    }

    /*
    Молоко
    Морковь
    Творог
    Яйца
    Ананас
    Тыква 20+29+12+3+1 = 65
    Апельсин
    Яблоко

    0 [....] 263
    * */
}

