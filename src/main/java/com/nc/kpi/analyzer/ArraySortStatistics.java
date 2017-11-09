package com.nc.kpi.analyzer;

import com.nc.kpi.sorters.Sorter;
import lombok.Getter;

import java.lang.reflect.Method;
import java.util.*;

public class ArraySortStatistics {
    @Getter
    private List<Long> countOfElementsValues;
    private Map<Method, Map<Class<? extends Sorter>, List<Long>>> sortTimeStat;

    public ArraySortStatistics() {
        countOfElementsValues = new ArrayList<>();
        sortTimeStat = new HashMap<>();
    }

    public List<Long> getSortTime(Method filler, Class<? extends Sorter> sorter) {
        if (sortTimeStat.containsKey(filler)) {
            return sortTimeStat.get(filler).get(sorter);
        }
        return null;
    }

    public Map<Class<? extends Sorter>, List<Long>> getFillerStatistics(Method filler) {
        return sortTimeStat.get(filler);
    }

    public List<Long> getSorterStatistics(Method filler, Class<? extends Sorter> sorter) {
        if (sortTimeStat.containsKey(filler)) {
            return sortTimeStat.get(filler).get(sorter);
        }
        return null;
    }

    public Set<Method> getFillers() {
        return sortTimeStat.keySet();
    }

    public void addValues(Method filler, Class<? extends Sorter> sorter, Long countOfElements, Long sortTime) {
        Map<Class<? extends Sorter>, List<Long>> fillerStat;
        if (sortTimeStat.containsKey(filler)) {
            fillerStat = sortTimeStat.get(filler);
            if (fillerStat.containsKey(sorter)) {
                List<Long> sorterStat = sortTimeStat.get(filler).get(sorter);
                if (!sorterStat.contains(sortTime)) {
                    sorterStat.add(sortTime);
                }
            } else {
                fillerStat.put(sorter, new ArrayList<>());
                fillerStat.get(sorter).add(sortTime);
            }
        } else {
            sortTimeStat.put(filler, new HashMap<>());
            fillerStat = sortTimeStat.get(filler);
            fillerStat.put(sorter, new ArrayList<>());
            fillerStat.get(sorter).add(sortTime);
        }
        if (!countOfElementsValues.contains(countOfElements)) {
            countOfElementsValues.add(countOfElements);
        }
    }

    public void print() {
        System.out.println(sortTimeStat);
    }
}
