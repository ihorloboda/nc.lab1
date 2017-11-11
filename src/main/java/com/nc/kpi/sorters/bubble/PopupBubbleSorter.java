package com.nc.kpi.sorters.bubble;

/**
 * Bubble sorter which sorts from left to right
 *
 * @author Ihor Loboda
 * @see BubbleSorter
 * @see InverseBubbleSorter
 */
public class PopupBubbleSorter extends BubbleSorter {
    public PopupBubbleSorter() {
        step = 1;
    }

    @Override
    protected void doSort(int[] array) {
        bubbleSort(array, 0, array.length - 1);
    }
}
