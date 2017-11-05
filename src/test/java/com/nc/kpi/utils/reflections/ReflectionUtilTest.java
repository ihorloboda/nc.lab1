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

    @Before
    public void init() {
        reflectionUtil = ReflectionUtil.getInstance();
    }

    @Test
    public void getFillMethodsTest() {
        Assert.assertFalse(reflectionUtil.getAllFillMethods().isEmpty());
        for (Method method : reflectionUtil.getAllFillMethods()) {
            Assert.assertTrue(method.isAnnotationPresent(Filler.class));
        }
    }

    @Test
    public void getSortersTest() {
        Assert.assertFalse(reflectionUtil.getSorters().isEmpty());
        for (Class<? extends Sorter> sorter : reflectionUtil.getSorters()) {
            int mod = sorter.getModifiers();
            Assert.assertFalse(Modifier.isAbstract(mod));
            Assert.assertFalse(Modifier.isInterface(mod));
            Assert.assertTrue(Sorter.class.isAssignableFrom(sorter));
        }
    }
}
