package com.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MyHashMapTest {

    MyHashMap hashMap;
    int maxSize = 10000;

    @BeforeEach
    void init() {
        hashMap = new MyHashMap();
    }

    @Test
    void sizeNormal() {
        int amount = 5;
        for (int i = 0; i < amount; i++) {
            hashMap.put(i, i);
        }
        assertEquals(hashMap.size(), amount);
    }

    @Test
    void sizeRepeatingKeys() {
        int amountUnique = 5;
        int amountRepeating = 3;
        for (int i = 0; i < amountUnique; i++) {
            hashMap.put(i, i);
        }
        for (int i = 0; i < amountRepeating; i++) {
            hashMap.put(i, i + 1);
        }
        assertEquals(hashMap.size(), amountUnique);
    }

    @Test
    @DisplayName("throws OutOfMemoryError")
    void sizeOverflow() {
        for (int i = 0; i < maxSize; i++) {
            hashMap.put(i, i);
        }
        assertThrows(OutOfMemoryError.class, () -> hashMap.put(maxSize, maxSize));
    }


    @Test
    void putNormal() {
        assertNull(hashMap.put(1, 1));
    }

    @Test
    void putNegative() {
        assertNull(hashMap.put(-1, 1));
    }

    @Test
    void putRepeatingKeys() {
        hashMap.put(1, 1);
        assertEquals(hashMap.put(1, 2), 1);
    }

    @Test
    void putMax() {
        assertNull(hashMap.put(Integer.MAX_VALUE, 1));
    }

    @Test
    void putMin() {
        assertNull(hashMap.put(Integer.MIN_VALUE, 1));
    }

    @Test
    void getNull() {
        assertNull(hashMap.get(1));
    }

    @Test
    void getNormal() {
        hashMap.put(1, 2);
        assertEquals(hashMap.get(1), 2);
    }

    @Test
    void getRepeatingKeys() {
        hashMap.put(1, 2);
        hashMap.put(1, 3);
        assertEquals(hashMap.get(1), 3);
    }

    @Test
    void getRepeatingValues() {
        hashMap.put(1, 2);
        hashMap.put(2, 2);
        assertEquals(hashMap.get(1), 2);
        assertEquals(hashMap.get(2), 2);
    }
}