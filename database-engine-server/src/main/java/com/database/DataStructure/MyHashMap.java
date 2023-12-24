package com.database.DataStructure;

public class MyHashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    private Entry<K, V>[] buckets;
    private int size;

    public MyHashMap() {
        this(DEFAULT_CAPACITY);
    }

    public MyHashMap(int capacity) {
        buckets = new Entry[capacity];
        size = 0;
    }

    public void put(K key, V value) {
        int hash = key.hashCode();
        int index = hash % buckets.length;

        Entry<K, V> entry = buckets[index];
        while (entry != null) {
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                return;
            }
            entry = entry.getNext();
        }

        Entry<K, V> newNode = new Entry<>(key, value);
        newNode.setNext(buckets[index]);
        buckets[index] = newNode;

        size++;

        if ((double) size / buckets.length > LOAD_FACTOR) {
            resize();
        }
    }

    public V get(K key) {
        int hash = key.hashCode();
        int index = hash % buckets.length;

        Entry<K, V> entry = buckets[index];
        while (entry != null) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
            entry = entry.getNext();
        }

        return null;
    }

    public void remove(K key) {
        int hash = key.hashCode();
        int index = hash % buckets.length;

        Entry<K, V> prev = null;
        Entry<K, V> current = buckets[index];

        while (current != null) {
            if (current.getKey().equals(key)) {
                if (prev == null) {
                    buckets[index] = current.getNext();
                } else {
                    prev.setNext(current.getNext());
                }
                size--;
                return;
            }
            prev = current;
            current = current.getNext();
        }
    }


    private void resize() {
        int newCapacity = buckets.length * 2;
        Entry<K, V>[] newBuckets = new Entry[newCapacity];
    
        for (Entry<K, V> entry : buckets) {
            while (entry != null) {
                int newIndex = entry.getKey().hashCode() % newCapacity;
                Entry<K, V> next = entry.getNext();
                entry.setNext(newBuckets[newIndex]);
                newBuckets[newIndex] = entry;
                entry = next;
            }
        }
    
        buckets = newBuckets;
    }
}