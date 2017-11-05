package com.nc.kpi.fillers;

import com.nc.kpi.utils.test.ArrayElementsComparator;
import com.nc.kpi.utils.test.ArrayPrinter;
import org.junit.Assert;
import org.junit.Test;

public class ArrayFillerTest {
    private final int SIZE = 10;
    private int[] array;
    private ArrayElementsComparator comparator = ArrayElementsComparator.getInstance();

    @Test
    public void generateSortedArrayTest() {
        array = ArrayFiller.generateSortedArray(SIZE);
        ArrayPrinter.printArray(array);
        Assert.assertEquals(true, comparator.isArraySorted(array, true, 0, SIZE - 1));
    }

    @Test
    public void generateSortedArrayWithRandomLastElementTest() {
        array = ArrayFiller.generateSortedArrayWithRandomLastElement(SIZE);
        ArrayPrinter.printArray(array);
        Assert.assertEquals(true, comparator.isArraySorted(array, true, 0, SIZE - 2));
    }

    @Test
    public void generateInverseSortedArrayTest() {
        array = ArrayFiller.generateInverseSortedArray(SIZE);
        ArrayPrinter.printArray(array);
        Assert.assertEquals(true, comparator.isArraySorted(array, false, 0, SIZE - 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void generateRandomArrayTest() {
        array = ArrayFiller.generateRandomArray(SIZE, 10);
        ArrayPrinter.printArray(array);
        ArrayFiller.generateRandomArray(-1, 10);
    }
}
