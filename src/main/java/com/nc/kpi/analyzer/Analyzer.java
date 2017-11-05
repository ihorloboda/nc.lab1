package com.nc.kpi.analyzer;

import com.nc.kpi.sorters.Sorter;
import com.nc.kpi.utils.reflections.ReflectionUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

public class Analyzer {
    private static Analyzer instance;

    private ReflectionUtil reflectionUtil;

    private Analyzer() {
        reflectionUtil = ReflectionUtil.getInstance();
    }

    public static Analyzer getInstance() {
        if (instance == null) {
            instance = new Analyzer();
        }
        return instance;
    }

    public ArraySortStatistics getAllStatistics(int maxCountOfElements) {
        ArraySortStatistics stat = new ArraySortStatistics();
        Set<Method> fillers = reflectionUtil.getAllFillMethods();
        Set<Class<? extends Sorter>> sorters = reflectionUtil.getSorters();
        int n = 10;
        putCountOfElementsValues(stat, n, maxCountOfElements);
        putSortTimeValues(stat, fillers, sorters, n);
        return stat;
    }

    private ArraySortStatistics getStatistics(ArraySortStatistics stat, int[] array, Class<? extends Sorter> sorterClass) {
        //TODO timer
        return stat;
    }

    private void putCountOfElementsValues(ArraySortStatistics stat, int n, int maxCountOfElements) {
        while (n < maxCountOfElements) {
            stat.addCountOfElementsValue((long) n);
            n *= 10;
        }
    }

    private void putSortTimeValues(ArraySortStatistics stat, Set<Method> fillers,
                                   Set<Class<? extends Sorter>> sorters, int n) {
        for (Method filler : fillers) {
            int[] array = new int[n];
            try {
                switch (filler.getParameterCount()) {
                    case 1:
                        array = (int[]) filler.invoke(n);
                        break;
                    case 2:
                        array = (int[]) filler.invoke(n, n);
                        break;
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            for (Class<? extends Sorter> sorter : sorters) {
                getStatistics(stat, array, sorter);
            }
        }
    }
}
