package com.nc.kpi.sorters.bubble;

public class InverseBubbleSorter extends BubbleSorter {
    public InverseBubbleSorter() {
        step = -1;
    }

    @Override
    public void doSort(int[] array) {
        bubbleSort(array, array.length - 2, -1);
    }
}
