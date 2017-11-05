package com.nc.kpi.sorters;

import com.nc.kpi.sorters.halfdivision.MergeSorter;

public class MergeSorterTest extends SorterTest {
    @Override
    public void initSorter() {
        sorter = new MergeSorter();
    }
}
