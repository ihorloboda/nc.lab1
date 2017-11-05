package com.nc.kpi;

import com.nc.kpi.analyzer.Analyzer;
import com.nc.kpi.analyzer.ArraySortStatistics;
import com.nc.kpi.fillers.ArrayFiller;
import com.nc.kpi.fillers.Filler;
import com.nc.kpi.sorters.Sorter;
import com.nc.kpi.utils.reflections.ReflectionUtil;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;
import org.slf4j.profiler.Profiler;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Ref;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, IllegalAccessException {
        Reflections reflections = new Reflections("com.nc.kpi.fillers", new MethodAnnotationsScanner());
        Set<Method> fillers = reflections.getMethodsAnnotatedWith(Filler.class);
        Method[] methods = new Method[fillers.size()];
        fillers.toArray(methods);
        System.out.println(methods[0].getParameterCount());
        int[] array = (int[]) methods[0].invoke(null,10);
        for (int i = 0; i < array.length; i++) {
            System.out.println(array[i]);
        }
    }
}
