package com.nc.kpi.utils.reflections;

import com.nc.kpi.fillers.Filler;
import com.nc.kpi.sorters.Sorter;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.scanners.SubTypesScanner;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Iterator;
import java.util.Set;

public class ReflectionUtil {
    private static ReflectionUtil instance;
    private Reflections sorterReflections;
    private Reflections fillerReflections;

    private ReflectionUtil() {
        sorterReflections = new Reflections("com.nc.kpi.sorters", new SubTypesScanner());
        fillerReflections = new Reflections("com.nc.kpi.fillers", new MethodAnnotationsScanner());
    }

    public static ReflectionUtil getInstance() {
        if (instance == null) {
            instance = new ReflectionUtil();
        }
        return instance;
    }

    public Set<Method> getAllFillMethods() {
        return fillerReflections.getMethodsAnnotatedWith(Filler.class);
    }

    public Set<Class<? extends Sorter>> getAllSorters() {
        Set<Class<? extends Sorter>> sorters = sorterReflections.getSubTypesOf(Sorter.class);
        for (Iterator<Class<? extends Sorter>> iterator = sorters.iterator(); iterator.hasNext(); ) {
            if (Modifier.isAbstract(iterator.next().getModifiers())) {
                iterator.remove();
            }
        }
        return sorters;
    }

    public Method getFillMethod(String name) {
        Set<Method> fillers = getAllFillMethods();
        for (Method filler : fillers) {
            if (filler.getName().equals(name)) {
                return filler;
            }
        }
        return null;
    }

    public Class<? extends Sorter> getSorterClass(String name) {
        Set<Class<? extends Sorter>> sorters = getAllSorters();
        for (Class<? extends Sorter> sorter : sorters) {
            if (sorter.getSimpleName().equals(name)) {
                return sorter;
            }
        }
        return null;
    }
}
