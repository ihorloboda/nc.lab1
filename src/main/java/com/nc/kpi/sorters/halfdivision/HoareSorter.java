package com.nc.kpi.sorters.halfdivision;

public class HoareSorter extends HalfDivisionSorter {
//    private int leftIndex;
//    private int rightIndex;
//    private int middleIndex;
//    private int i;
//    private int j;
//    private int middleElement;

    //        leftIndex = 0;
//        rightIndex = array.length - 1;
//        middleIndex = leftIndex + (rightIndex - leftIndex) / 2;
//        i = leftIndex;
//        j = rightIndex;
//        middleElement = array[middleIndex];
//        doIteration(array);
//        sortArrayParts(array);


    @Override
    public void doSort(int[] array) {
        isArrayNull(array);
        int leftIndex = 0;
        int rightIndex = array.length - 1;
        int middleIndex = leftIndex + (rightIndex - leftIndex) / 2;
        int i = leftIndex;
        int j = rightIndex;
        int middleElement = array[middleIndex];
        while (i <= j) {
            while (array[i] < middleElement) {
                i++;
            }
            while (array[j] > middleElement) {
                j--;
            }
            if (i <= j) {
                swap(array, i, j);
                i++;
                j--;
            }
        }
        if (array.length > 1) {
            int[] leftArray = new int[i];
            int[] rightArray = new int[array.length - i];
            divideArrayByHalf(array, leftArray, rightArray, i);
            if (leftIndex < j) {
                sort(leftArray);
                System.arraycopy(leftArray, 0, array, 0, leftArray.length);
            }
            if (i < rightIndex) {
                sort(rightArray);
                System.arraycopy(rightArray, 0, array, i, rightArray.length);
            }
        }
    }

//    private void doIteration(int[] array) {
//        while (i <= j) {
//            while (array[i] < middleElement) {
//                i++;
//            }
//            while (array[j] > middleElement) {
//                j--;
//            }
//            if (i <= j) {
//                swap(array, i, j);
//                i++;
//                j--;
//            }
//        }
//    }
//
//    private void sortArrayParts(int[] array) {
//        if (array.length > 1) {
//            int[] leftArray = new int[i];
//            int[] rightArray = new int[array.length - i];
//            divideArrayByHalf(array, leftArray, rightArray, i);
//            if (leftIndex < j) {
//                sort(leftArray);
//                System.arraycopy(leftArray, 0, array, 0, leftArray.length);
//            }
//            if (i < rightIndex) {
//                sort(rightArray);
//                System.arraycopy(rightArray, 0, array, i, rightArray.length);
//            }
//        }
//    }
}
