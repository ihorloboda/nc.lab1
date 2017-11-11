package com.nc.kpi.sorters.halfdivision;

/**
 * Sorter that uses merge algorithm for sorting
 *
 * @author Ihor Loboda
 * @see HalfDivisionSorter
 * @see HoareSorter
 */
public class MergeSorter extends HalfDivisionSorter {
    @Override
    protected void doSort(int[] array) {
        isArrayNull(array);
        int[] leftArray = new int[array.length / 2];
        int[] rightArray = new int[array.length - leftArray.length];
        divideArrayByHalf(array, leftArray, rightArray, leftArray.length);
        if (array.length >= 2) {
            sort(leftArray);
            sort(rightArray);
        }
        merge(array, leftArray, rightArray);
    }

    private void merge(int[] array, int[] leftArray, int[] rightArray) {
        int i = 0;
        int j = 0;
        int k = 0;
        while (i < leftArray.length && j < rightArray.length) {
            if (leftArray[i] < rightArray[j]) {
                array[k] = leftArray[i];
                i++;
            } else {
                array[k] = rightArray[j];
                j++;
            }
            k++;
        }
        if (i < leftArray.length) {
            System.arraycopy(leftArray, i, array, k, leftArray.length - i);
        }
        if (j < rightArray.length) {
            System.arraycopy(rightArray, j, array, k, rightArray.length - j);
        }
    }
}
