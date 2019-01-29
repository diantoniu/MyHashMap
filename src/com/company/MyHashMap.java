package com.company;

public class MyHashMap {

    /**
     * Map node class.
     */
    private class Node {
        int key;
        long value;

        Node(int key, long value) {
            this.key = key;
            this.value = value;
        }

        void setValue(Long value) {

            this.value = value;
        }
    }

    /** Current size of the map */
    private int size;

    /** Max size of the map */
    private int maxSize;

    /** Special marker indicating that node was deleted */
    private Node fake;

    /** Array of map nodes */
    private Node[] nodes;

    MyHashMap() {
        this.size = 0;
        this.maxSize = 10000;
        this.fake = new Node(-1, -1);
        nodes = new Node[maxSize];
    }

    /**
     * Returns current size of the map.
     */
    public Integer size() {

        return size;
    }


    /**
     * Returns previous value associated with key, or null if there was no mapping for key.
     *
     * @throws OutOfMemoryError If it is not possible to add a new item because the map
     *                          is out of memory.
     *
     * @param key Key with which the specified value is to be associated.
     *
     * @param value Value to be associated with the specified key.
     */
    public Long put(int key, long value) {
        Node node = searchNode(key);
        // if the specified key already exists in the map -- replace the value
        if (node != null) {
            long prevValue = node.value;
            node.setValue(value);
            return prevValue;
        } else {
            // probing until an empty slot is found
            for (int i = 0; i < maxSize; i++) {
                int hash = getHash(key, i);
                if (nodes[hash] == null || nodes[hash] == fake) {
                    size++;
                    nodes[hash] = new Node(key, value);
                    return null;
                }
            }
        }
        throw new OutOfMemoryError("HashMap is full.");
    }

    /**
     * Returns the value to which the specified key is mapped, or null otherwise.
     *
     * @param key The key whose associated value is to be returned.
     */
    public Long get(int key) {
        Node node = searchNode(key);
        if (node != null) {
            return node.value;
        }
        return null;
    }

    /**
     * Returns the map node which has specified key, or null otherwise.
     *
     * @param key The key whose associated node is to be returned.
     */
    private Node searchNode(int key) {
        for (int i = 0; i < maxSize; i++) {
            int hash = getHash(key, i);
            // probing until node.key not equal to specified or find an empty slot
            if (nodes[hash] == null)
                return null;
            else if (nodes[hash] != fake && nodes[hash].key == key) {
                return nodes[hash];
            }
        }
        return null;
    }

    /**
     * Returns hash of the key.
     *
     * @param key The key whose associated hash value is to be returned.
     *
     * @param i Ordinal sequence element.
     */
    private int getHash(int key, int i) {
        // k and maxSize should be coprime integers
        int k = 31;
        return (((Integer.hashCode(key) + i * k) % maxSize) + maxSize) % maxSize;
    }
}