package com.nc.kpi.analyzer;

import com.nc.kpi.fillers.ArrayFiller;
import com.nc.kpi.sorters.QuickSorter;
import com.nc.kpi.sorters.Sorter;
import com.nc.kpi.utils.reflections.ReflectionUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public class AnalyzerTest {
    private final int SIZE = 10;
    private Analyzer analyzer;
    private ReflectionUtil reflectionUtil;
    private Method filler;
    private Class<? extends Sorter> sorter;

    @Before
    public void init() throws NoSuchMethodException {
        analyzer = Analyzer.getInstance();
        reflectionUtil = ReflectionUtil.getInstance();
        filler = ArrayFiller.class.getMethod("generateRandomArray", int.class, int.class);
        sorter = QuickSorter.class;
    }

    @Test(expected = IllegalArgumentException.class)
    public void getFullStat() {
        Map<Method, List<ArraySortStatistics>> fullStat = analyzer.getFullStat(SIZE);
        System.out.println(fullStat);
        Assert.assertEquals(reflectionUtil.getAllFillMethods(), fullStat.keySet());
        analyzer.getFullStat(9);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getStat() {
        int[] array = ArrayFiller.generateRandomArray(SIZE, SIZE);
        ArraySortStatistics stat = analyzer.getStat(SIZE, array, filler, sorter);
        Assert.assertEquals(stat.getTimeOfSortValues().size(), stat.getCountOfElementsValues().size());
        analyzer.getStat(0, null, null, null);
    }

}