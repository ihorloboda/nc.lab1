package com.nc.kpi.analyzer;

import com.nc.kpi.sorters.Sorter;
import com.nc.kpi.utils.reflections.ReflectionUtil;
import org.slf4j.profiler.Profiler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Class for sort time tracking
 *
 * @author Ihor Loboda
 */
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

    /**
     * <p>Tracks time of array sort for different array length, fillers and sorters.</p>
     * <p>Array length changes from 10 to maxCountOfElements with multiplying on 10.
     * If maxCountOfElements is not divided by 10 than array lenght changes to max value which is
     * divided by 10 and less than maxCountOfElements.</p>
     * <p>Array will be filled by all fillers, which is present in {@link com.nc.kpi.fillers} package
     * and all sorters which extends {@link Sorter}.</p>
     *
     * @param maxCountOfElements max value of array length
     * @return statistics for all fillers and sorters
     * @throws IllegalArgumentException if max count of elements is less than 10 or if at least of params is null or zero
     */
    public ArraySortStatistics getFullStat(int maxCountOfElements) {
        if (maxCountOfElements < 10) {
            throw new IllegalArgumentException("Max count of elements must be >= 10");
        }
        Set<Method> fillers = reflectionUtil.getAllFillMethods();
        Set<Class<? extends Sorter>> sorters = reflectionUtil.getAllSorters();
        ArraySortStatistics stat = new ArraySortStatistics();
        for (Method filler : fillers) {
            int countOfElements = 10;
            while (countOfElements <= maxCountOfElements) {
                int[] array = invokeFiller(filler, countOfElements);
                for (Class<? extends Sorter> sorter : sorters) {
                    getStat(stat, array, filler, sorter);
                }
                countOfElements *= 10;
            }
        }
        return stat;
    }

    /**
     * Tracks sort time of array which is filled by filler and sorted by sorter.
     * Wraps results into {@link ArraySortStatistics} object, which contains filler, sorter,
     * elements count of array and sort time.
     *
     * @param array       array for time tracking
     * @param filler      filler method (for key in {@link ArraySortStatistics}
     * @param sorterClass sorter (for sorting and for key in {@link ArraySortStatistics}
     * @return statistics for array
     * @throws IllegalArgumentException if at least of params is null or zero.
     */
    public ArraySortStatistics getStat(int[] array, Method filler, Class<? extends Sorter> sorterClass) {
        if (array == null || filler == null || sorterClass == null) {
            throw new IllegalArgumentException();
        }
        ArraySortStatistics stat = new ArraySortStatistics();
        getStat(stat, array, filler, sorterClass);
        return stat;
    }

    private ArraySortStatistics getStat(ArraySortStatistics stat, int[] array, Method filler,
                                        Class<? extends Sorter> sorterClass) {
        Sorter sorter = instantiateSorter(sorterClass);
        long averageTime = 0;
        for (int i = 0; i < COUNT_OF_REPEATING; i++) {
            Profiler profiler = new Profiler("filler: " + filler.getName() + ", countOfElements: " + array.length +
                    ", sorter: " + sorterClass.getSimpleName());
            profiler.start("i = " + i);
            sorter.sort(array);
            profiler.stop();
            averageTime += profiler.elapsedTime();
        }
        averageTime /= COUNT_OF_REPEATING;
        stat.addValues(filler, sorterClass, Long.valueOf(array.length), averageTime);
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
