package com.nc.kpi.fillers;

/**
 * <p>
 * Class for array filling.
 * </p>
 * @author Igor Loboda
 */
public class ArrayFiller {
    private static final int ADDITIONAL_TERM = 5;

    /**
     * Generate sorted by ascending array
     *
     * @param size size of generated array
     * @return generated array which is sorted by ascending
     * @see #generateInverseSortedArray(int)
     * @exception IllegalArgumentException if size &lt;= 0
     */
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

    /**
     * Generate sorted by ascending array with random last element
     *
     * @param size size of generated array
     * @return generated array which is sorted by ascending and has random last element
     * @see #generateSortedArray(int)
     * @exception IllegalArgumentException if size &lt;= 0
     */
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

    /**
     * Generate sorted by descending array
     *
     * @param size size of generated array
     * @return generated array which is sorted by descending
     * @see #generateSortedArray(int)
     * @exception IllegalArgumentException if size &lt;= 0
     */
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

    /**
     * Generate array with random elements
     *
     * @param size size of generated array
     * @param max  max possible value of random element
     * @return generated array with random elements
     * @see #generateSortedArray(int)
     * @see #generateInverseSortedArray(int)
     * @see #generateSortedArrayWithRandomLastElement(int)
     * @exception IllegalArgumentException if size &lt;= 0
     */
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
