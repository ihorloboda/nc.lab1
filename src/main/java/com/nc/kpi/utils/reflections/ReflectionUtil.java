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
        sorterReflections = new Reflections("sorters", new SubTypesScanner());
        fillerReflections = new Reflections("fillers", new MethodAnnotationsScanner());
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

    public Set<Class<? extends Sorter>> getSorters() {
        Set<Class<? extends Sorter>> sorters = sorterReflections.getSubTypesOf(Sorter.class);
        for (Iterator<Class<? extends Sorter>> iterator = sorters.iterator(); iterator.hasNext(); ) {
            if (Modifier.isAbstract(iterator.next().getModifiers())) {
                iterator.remove();
            }
        }
        return sorters;
    }
}
