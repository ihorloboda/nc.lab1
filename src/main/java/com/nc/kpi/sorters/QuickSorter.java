package com.nc.kpi.sorters;

import java.util.Arrays;

/**
 * This sorter uses {@code Arrays.sort()}
 *
 * @author Ihor Loboda
 */
public class QuickSorter extends Sorter {
    @Override
    protected void doSort(int[] array) {
        Arrays.sort(array);
    }
}
