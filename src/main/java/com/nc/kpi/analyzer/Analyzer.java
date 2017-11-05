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

    public Map<Method, List<ArraySortStatistics>> getFullStat(int maxCountOfElements) {
        if(maxCountOfElements<10){
            throw new IllegalArgumentException("Max count of elemnts must be > 10");
        }
        Set<Method> fillers = reflectionUtil.getAllFillMethods();
        Set<Class<? extends Sorter>> sorters = reflectionUtil.getSorters();
        int n = 10;
        Map<Method, List<ArraySortStatistics>> stat = new HashMap<>();
        while (n <= maxCountOfElements) {
            for (Method filler : fillers) {
                stat.put(filler, new ArrayList<>());
                int[] array = invokeFiller(filler, n);
                for (Class<? extends Sorter> sorter : sorters) {
                    stat.get(filler).add(getStat(n, array, filler, sorter));
                }
            }
            n *= 10;
        }
        return stat;
    }

    public ArraySortStatistics getStat(int countOfElements, int[] array, Method filler, Class<? extends Sorter> sorterClass) {
        if (countOfElements <= 0 || array == null || filler == null || sorterClass == null) {
            throw new IllegalArgumentException();
        }
        ArraySortStatistics stat = new ArraySortStatistics(filler, sorterClass);
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
        stat.addValues((long) countOfElements, averageTime);
        return stat;
    }

    private int[] invokeFiller(Method filler, int n) {
        try {
            System.out.println(filler.getParameterCount());
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
