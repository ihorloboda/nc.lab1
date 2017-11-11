package com.nc.kpi.sorters.halfdivision;

import com.nc.kpi.sorters.Sorter;

/**
 * Abstract sorter that uses half division method for sorting
 *
 * @author Ihor Loboda
 * @see HoareSorter
 * @see MergeSorter
 */
public abstract class HalfDivisionSorter extends Sorter {
    protected void divideArrayByHalf(int[] array, int[] leftArray, int[] rightArray, int middlePos) {
        System.arraycopy(array, 0, leftArray, 0, middlePos);
        System.arraycopy(array, middlePos, rightArray, 0, array.length - middlePos);
    }
}
