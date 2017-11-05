package com.nc.kpi.sorters.bubble;

public class PopupBubbleSorter extends BubbleSorter {
    public PopupBubbleSorter() {
        step = 1;
    }

    @Override
    public void doSort(int[] array) {
        bubbleSort(array, 0, array.length - 1);
    }
}
