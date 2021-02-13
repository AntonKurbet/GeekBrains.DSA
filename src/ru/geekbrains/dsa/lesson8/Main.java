package ru.geekbrains.dsa.lesson8;

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

    public static void main(String[] args) {
        HashCat cat = new HashCat(25);
        cat.insert(new Item(10));
        cat.insert(new Item(20));
        cat.insert(new Item(30));
        cat.insert(new Item(40));
        cat.insert(new Item(50));
        cat.insert(new Item(75));
        cat.insert(new Item(60));
        cat.insert(new Item(70));
        System.out.println(cat);
        cat.delete(75);
        System.out.println(cat);
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

