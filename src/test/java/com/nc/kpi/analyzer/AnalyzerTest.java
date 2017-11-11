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
    private final int SIZE = 1000;
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
        ArraySortStatistics fullStat = analyzer.getFullStat(SIZE);
        Assert.assertEquals(reflectionUtil.getAllFillMethods(), fullStat.getFillers());
        Assert.assertEquals(Math.log10(SIZE), fullStat.getCountOfElementsValues().size(), 10E-6);
        for (Method filler : fullStat.getFillers()) {
            Map<Class<? extends Sorter>, List<Long>> fillerStat = fullStat.getFillerStatistics(filler);
            for (Class<? extends Sorter> sorter : fillerStat.keySet()) {
                Assert.assertEquals(Math.log10(SIZE), fullStat.getSorterStatistics(filler, sorter).size(), 10E-6);
            }
        }
        analyzer.getFullStat(9);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getStat() {
        int[] array = ArrayFiller.generateRandomArray(SIZE, SIZE);
        ArraySortStatistics stat = analyzer.getStat(SIZE, array, filler, sorter);
        Assert.assertEquals(stat.getCountOfElementsValues().size(), stat.getSortTime(filler, sorter).size());
        analyzer.getStat(0, null, null, null);
    }
}