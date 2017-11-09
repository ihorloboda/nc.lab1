package com.nc.kpi.analyzer;

import com.nc.kpi.sorters.Sorter;
import com.nc.kpi.utils.reflections.ReflectionUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class ArraySortStatisticsTest {
    private final long MAX_COUNT_OF_ELEMENTS = 1000;
    private final String SORTER_NAME_1 = "MergeSorter";
    private final String FILLER_NAME_1 = "generateRandomArray";
    private final String SORTER_NAME_2 = "HoareSorter";
    private final String FILLER_NAME_2 = "generateInverseSortedArray";

    private List<Long> countOfElementsValues;
    private List<Long> sortTimeValues;
    private ArraySortStatistics stat;
    private Method filler1, filler2;
    private Class<? extends Sorter> sorter1, sorter2;
    private ReflectionUtil reflectionUtil;

    @Before
    public void init() {
        reflectionUtil = ReflectionUtil.getInstance();
        stat = new ArraySortStatistics();
        countOfElementsValues = new ArrayList<>();
        sortTimeValues = new ArrayList<>();
        for (int i = 10; i <= MAX_COUNT_OF_ELEMENTS; i *= 10) {
            countOfElementsValues.add((long) i);
            sortTimeValues.add((long) Math.log10(i));
        }
        filler1 = reflectionUtil.getFillMethod(FILLER_NAME_1);
        sorter1 = reflectionUtil.getSorterClass(SORTER_NAME_1);
        filler2 = reflectionUtil.getFillMethod(FILLER_NAME_2);
        sorter2 = reflectionUtil.getSorterClass(SORTER_NAME_2);
    }

    //TODO this test
    @Test
    public void addValues() {
        stat.addValues(filler1, sorter1, 1L, 1L);
        stat.addValues(filler2, sorter2, 2L, 2L);
        stat.addValues(filler1, sorter2, 1L, 2L);
        stat.addValues(filler1, sorter2, 1L, 1L);
        stat.addValues(filler1, sorter2, 1L, 2L);
        Assert.assertEquals(2, stat.getCountOfElementsValues().size());
        Assert.assertEquals(1, (long) stat.getSortTime(filler1, sorter1).get(0));
        Assert.assertEquals(1, (long) stat.getSortTime(filler1, sorter1).get(0));
        Assert.assertEquals(2, (long) stat.getSortTime(filler1, sorter2).get(0));
        Assert.assertEquals(2, (long) stat.getSortTime(filler2, sorter2).get(0));
    }
}