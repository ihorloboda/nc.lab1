package com.nc.kpi.sorters.bubble;

/**
 * Bubble sorter which sorts from right to left
 *
 * @author Ihor Loboda
 * @see BubbleSorter
 * @see PopupBubbleSorter
 */
public class InverseBubbleSorter extends BubbleSorter {
    public InverseBubbleSorter() {
        step = -1;
    }

    @Override
    protected void doSort(int[] array) {
        bubbleSort(array, array.length - 2, -1);
    }
}
