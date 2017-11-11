package com.nc.kpi.analyzer;

import com.nc.kpi.sorters.Sorter;
import lombok.Getter;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Container for statistics transfer.
 *
 * @author Ihor Loboda
 * @see Analyzer
 * @see com.nc.kpi.utils.excel.ExcelUtil
 */
public class ArraySortStatistics {
    @Getter
    private List<Long> countOfElementsValues;
    private Map<Method, Map<Class<? extends Sorter>, List<Long>>> sortTimeStat;

    public ArraySortStatistics() {
        countOfElementsValues = new ArrayList<>();
        sortTimeStat = new HashMap<>();
    }

    /**
     * Returns list of sort time values for certain filler and sorter
     *
     * @param filler method which fills array
     * @param sorter sorter class
     * @return list of sort time values or null otherwise
     */
    public List<Long> getSortTime(Method filler, Class<? extends Sorter> sorter) {
        if (sortTimeStat.containsKey(filler)) {
            return sortTimeStat.get(filler).get(sorter);
        }
        return null;
    }

    /**
     * Returns a map with filler statistics
     *
     * @param filler method which fills array
     * @return map with statistics for this filler or null if there is not statistics
     */
    public Map<Class<? extends Sorter>, List<Long>> getFillerStatistics(Method filler) {
        return sortTimeStat.get(filler);
    }

    /**
     * Returns a list of sort time values with sorter statistics
     *
     * @param filler method which fills array
     * @param sorter sorter which sorts array
     * @return list of sort time values for these filler and sorter if they exist, null otherwise
     */
    public List<Long> getSorterStatistics(Method filler, Class<? extends Sorter> sorter) {
        if (sortTimeStat.containsKey(filler)) {
            return sortTimeStat.get(filler).get(sorter);
        }
        return null;
    }

    /**
     * Returns key set of fillers which have statistics
     *
     * @return set of fillers which have statistics, if statistics is empty returns null
     */
    public Set<Method> getFillers() {
        return sortTimeStat.keySet();
    }

    /**
     * Add to statistics one point (count of elements and sort time) for filler and sorter.
     * If this point exists than old value will be replaced by new.
     *
     * @param filler          that generated array
     * @param sorter          that sorted array
     * @param countOfElements length of array
     * @param sortTime        sort time of array which filled by filler with sorter
     */
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
}
