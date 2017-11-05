package com.nc.kpi.analyzer;

import com.nc.kpi.sorters.Sorter;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ArraySortStatistics {
    private List<Long> countOfElementsValues;
    private List<Long> timeOfSortValues;
    private Method filler;
    private Class<? extends Sorter> sorter;

    public ArraySortStatistics(Method filler, Class<? extends Sorter> sorter) {
        countOfElementsValues = new ArrayList<>();
        timeOfSortValues = new ArrayList<>();
        this.filler = filler;
        this.sorter = sorter;
    }

    public ArraySortStatistics(List<Long> countOfElementsValues, List<Long> timeOfSortValues,
                               Method filler, Class<? extends Sorter> sorter) {
        this.countOfElementsValues = countOfElementsValues;
        this.timeOfSortValues = timeOfSortValues;
        this.filler = filler;
        this.sorter = sorter;
    }

    public void addCountOfElementsValue(Long value) {
        countOfElementsValues.add(value);
    }

    public void addTimeOfSortValue(Long value) {
        timeOfSortValues.add(value);
    }

    public void addValues(Long countOfElements, Long timeOfSort) {
        addCountOfElementsValue(countOfElements);
        addTimeOfSortValue(timeOfSort);
    }

    public void removeCountOfElementsValue(Long value) {
        countOfElementsValues.remove(value);
    }

    public void removeTimeOfSortValue(Long value) {
        timeOfSortValues.remove(value);
    }

    @Override
    public String toString() {
        return System.lineSeparator() + "{filler: " + filler.getName() + ", sorter: " + sorter.getSimpleName()
                + "countOfElementsValues: " + countOfElementsValues.toString() + ", timeOfSortValues: "
                + timeOfSortValues.toString() + "}";
    }
}
