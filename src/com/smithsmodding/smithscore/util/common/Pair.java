/*
 * Copyright (c) 2015.
 *
 * Copyrighted by SmithsModding according to the project License
 */

package com.smithsmodding.smithscore.util.common;

import java.util.*;

public class Pair<K, V> {
    K iObjectOne;
    V iObjectTwo;

    public Pair(K pItemOne, V pItemTwo) {
        iObjectOne = pItemOne;
        iObjectTwo = pItemTwo;
    }

    public K getKey() {
        return iObjectOne;
    }

    public V getValue() {
        return iObjectTwo;
    }

    @Override
    public int hashCode() {
        return iObjectOne.hashCode() + iObjectTwo.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pair))
            return false;

        try {
            Pair<K, V> tOtherPair = (Pair<K, V>) obj;
            return tOtherPair.iObjectTwo.equals(iObjectTwo) && tOtherPair.iObjectOne.equals(iObjectOne);

        } catch (Exception ex) {
            return false;
        }

    }
}
