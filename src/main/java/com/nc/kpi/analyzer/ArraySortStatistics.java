package com.nc.kpi.analyzer;

import com.nc.kpi.sorters.Sorter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArraySortStatistics {
    private List<Long> countOfElementsValues;
    private Map<Method, Map<Class<? extends Sorter>, List<Long>>> sortTimeValues;

    public ArraySortStatistics() {
        countOfElementsValues = new ArrayList<>();
        sortTimeValues = new HashMap<>();
    }

    public List<Long> getCountOfElementsValues() {
        return countOfElementsValues;
    }

    public void setCountOfElementsValues(List<Long> countOfElementsValues) {
        this.countOfElementsValues = countOfElementsValues;
    }

    public void addCountOfElementsValue(Long value) {
        countOfElementsValues.add(value);
    }

    public void removeCountOfElementsValue(Long value) {
        countOfElementsValues.remove(value);
    }

    public void addSortTimeValues(Method filler, Class<? extends Sorter> sorterClass, List<Long> values) {
        if (!sortTimeValues.containsKey(filler)) {
            sortTimeValues.put(filler, new HashMap<>());
        }
        sortTimeValues.get(filler).put(sorterClass, values);
    }

    public void removeSortTimeValues(Method filler, Class<? extends Sorter> sorterClass) {
        if (!sortTimeValues.containsKey(filler)) {
            sortTimeValues.get(filler).remove(sorterClass);
        }
        if (sortTimeValues.get(filler).isEmpty()) {
            sortTimeValues.remove(filler);
        }
    }

    public Map<Class<? extends Sorter>, List<Long>> getFillerStatistics(Method filler) {
        return sortTimeValues.get(filler);
    }

    public List<Long> getSorterStatistics(Method filler, Class<? extends Sorter> sorter) {
        if (sortTimeValues.containsKey(filler)) {
            return sortTimeValues.get(filler).get(sorter);
        }
        return null;
    }
}
