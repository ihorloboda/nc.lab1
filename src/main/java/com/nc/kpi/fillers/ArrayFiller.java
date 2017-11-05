package com.nc.kpi.fillers;

public class ArrayFiller {
    private static final int ADDITIONAL_TERM = 5;

    @Filler
    public static int[] generateSortedArray(int size) {
        checkSize(size);
        int[] array = new int[size];
        array[0] = (int) (ADDITIONAL_TERM * Math.random());
        for (int i = 1; i < size; i++) {
            array[i] = (int) (array[i - 1] + ADDITIONAL_TERM * Math.random());
        }
        return array;
    }

    @Filler
    public static int[] generateSortedArrayWithRandomLastElement(int size) {
        checkSize(size);
        int[] array = new int[size];
        array[0] = (int) (ADDITIONAL_TERM * Math.random());
        for (int i = 1; i < size - 1; i++) {
            array[i] = (int) (array[i - 1] + ADDITIONAL_TERM * Math.random());
        }
        array[size - 1] = (int) (size * Math.random());
        return array;
    }

    @Filler
    public static int[] generateInverseSortedArray(int size) {
        checkSize(size);
        int[] array = new int[size];
        array[size - 1] = (int) (ADDITIONAL_TERM * Math.random());
        for (int i = size - 2; i >= 0; i--) {
            array[i] = (int) (array[i + 1] + ADDITIONAL_TERM * Math.random());
        }
        return array;
    }

    @Filler
    public static int[] generateRandomArray(int size, int max) {
        checkSize(size);
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = (int) (max * Math.random());
        }
        return array;
    }

    private static void checkSize(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException("Size of generated array is <= 0");
        }
    }
}
