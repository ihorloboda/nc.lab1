package com.nc.kpi.sorters.bubble;

import com.nc.kpi.sorters.Sorter;

public abstract class BubbleSorter extends Sorter {
    protected int step;

    protected void bubbleSort(int[] array, int from, int to) {
        for (int j = 0; j < array.length - 1; j++) {
            for (int i = from; i != to; i += step) {
                if (array[i] > array[i + 1]) {
                    swap(array, i, i + 1);
                }
            }
        }
    }
}
