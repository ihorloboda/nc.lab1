package com.nc.kpi.utils.reflections;


import com.nc.kpi.fillers.Filler;
import com.nc.kpi.sorters.Sorter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class ReflectionUtilTest {
    private ReflectionUtil reflectionUtil;
    private final String SORTER_NAME = "MergeSorter";
    private final String FILLER_NAME = "generateRandomArray";

    @Before
    public void init() {
        reflectionUtil = ReflectionUtil.getInstance();
    }

    @Test
    public void getAllFillMethods() {
        Assert.assertFalse(reflectionUtil.getAllFillMethods().isEmpty());
        for (Method method : reflectionUtil.getAllFillMethods()) {
            Assert.assertTrue(method.isAnnotationPresent(Filler.class));
        }
    }

    @Test
    public void getAllSorters() {
        Assert.assertFalse(reflectionUtil.getAllSorters().isEmpty());
        for (Class<? extends Sorter> sorter : reflectionUtil.getAllSorters()) {
            int mod = sorter.getModifiers();
            Assert.assertFalse(Modifier.isAbstract(mod));
            Assert.assertFalse(Modifier.isInterface(mod));
            Assert.assertTrue(Sorter.class.isAssignableFrom(sorter));
        }
    }

    @Test
    public void getSorterClass() {
        Class<? extends Sorter> sorter = reflectionUtil.getSorterClass(SORTER_NAME);
        Assert.assertEquals(SORTER_NAME, sorter.getSimpleName());
    }

    @Test
    public void getFillerMethod() {
        Method filler = reflectionUtil.getFillMethod(FILLER_NAME);
        Assert.assertEquals(FILLER_NAME, filler.getName());
    }
}
