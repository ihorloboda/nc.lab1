package com.nc.kpi.analyzer;

import com.nc.kpi.sorters.Sorter;
import com.nc.kpi.utils.reflections.ReflectionUtil;
import org.slf4j.profiler.Profiler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class Analyzer {
    private static Analyzer instance;
    private final int COUNT_OF_REPEATING = 10;
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

    public ArraySortStatistics getFullStat(int maxCountOfElements) {
        if (maxCountOfElements < 10) {
            throw new IllegalArgumentException("Max count of elements must be > 10");
        }
        Set<Method> fillers = reflectionUtil.getAllFillMethods();
        Set<Class<? extends Sorter>> sorters = reflectionUtil.getAllSorters();
        ArraySortStatistics stat = new ArraySortStatistics();
        for (Method filler : fillers) {
            int countOfElements = 10;
            while (countOfElements <= maxCountOfElements) {
                int[] array = invokeFiller(filler, countOfElements);
                for (Class<? extends Sorter> sorter : sorters) {
                    getStat(stat, countOfElements, array, filler, sorter);
                }
                countOfElements *= 10;
            }
        }
        return stat;
    }

    public ArraySortStatistics getStat(int countOfElements, int[] array, Method filler, Class<? extends Sorter> sorterClass) {
        if (countOfElements <= 0 || array == null || filler == null || sorterClass == null) {
            throw new IllegalArgumentException();
        }
        ArraySortStatistics stat = new ArraySortStatistics();
        getStat(stat, countOfElements, array, filler, sorterClass);
        return stat;
    }

    private ArraySortStatistics getStat(ArraySortStatistics stat, int countOfElements, int[] array, Method filler,
                                        Class<? extends Sorter> sorterClass) {
        Sorter sorter = instantiateSorter(sorterClass);
        long averageTime = 0;
        for (int i = 0; i < COUNT_OF_REPEATING; i++) {
            Profiler profiler = new Profiler("filler: " + filler.getName() + ", countOfElements: " + countOfElements +
                    ", sorter: " + sorterClass.getSimpleName());
            profiler.start("i = " + i);
            sorter.sort(array);
            profiler.stop();
            averageTime += profiler.elapsedTime();
        }
        averageTime /= COUNT_OF_REPEATING;
        stat.addValues(filler, sorterClass, Long.valueOf(countOfElements), averageTime);
        return stat;
    }

    private int[] invokeFiller(Method filler, int n) {
        try {
            switch (filler.getParameterCount()) {
                case 1:
                    return (int[]) filler.invoke(null, n);
                case 2:
                    return (int[]) filler.invoke(null, n, n);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Sorter instantiateSorter(Class<? extends Sorter> sorter) {
        try {
            return sorter.newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
