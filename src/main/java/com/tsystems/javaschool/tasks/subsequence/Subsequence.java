package com.tsystems.javaschool.tasks.subsequence;

import java.util.Iterator;
import java.util.List;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {
        if (x == null || y == null) {
            throw new IllegalArgumentException("x and y must not be null");
        }
        Iterator it = y.iterator();
        for (Object element : x) {
            while (it.hasNext() && !element.equals(it.next())) ;
            if (!it.hasNext()) {
                return false;
            }
        }
        return true;
    }
}
