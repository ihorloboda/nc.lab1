package com.nc.kpi.sorters;

import com.nc.kpi.fillers.ArrayFiller;
import com.nc.kpi.utils.test.ArrayElementsComparator;
import com.nc.kpi.utils.test.ArrayPrinter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class SorterTest {
    protected final int SIZE = 10;
    protected int[] array;
    protected Sorter sorter;
    protected ArrayElementsComparator comparator;

    @Before
    public abstract void initSorter();

    @Before
    public void init() {
        array = ArrayFiller.generateRandomArray(SIZE, 10);
        comparator = ArrayElementsComparator.getInstance();
    }

    @Test
    public void sortTest() {
        sorter.sort(array);
        ArrayPrinter.printArray(array);
        Assert.assertTrue(comparator.isArraySorted(array, true, 0, SIZE - 1));
    }
}
