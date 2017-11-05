package com.nc.kpi.utils.test;

import java.util.Comparator;

public class ArrayElementsComparator implements Comparator<Integer> {
    private static ArrayElementsComparator instance;

    private ArrayElementsComparator() {
    }

    public static ArrayElementsComparator getInstance() {
        if (instance == null) {
            instance = new ArrayElementsComparator();
        }
        return instance;
    }

    public int compare(Integer o1, Integer o2) {
        if (o1 > o2) {
            return 1;
        } else if (o1 < o2) {
            return -1;
        } else {
            return 0;
        }
    }

    public boolean isArraySorted(int[] array, boolean increasing, int from, int to) {
        for (int i = from; i < to; i++) {
            int compareResult = compareArrayNeighboringElements(array, i);
            if (increasing && compareResult > 0) {
                return false;
            }
            if (!increasing && compareResult < 0) {
                return false;
            }
        }
        return true;
    }

    public int compareArrayNeighboringElements(int[] array, int i) {
        return compare(array[i], array[i + 1]);
    }
}
