package com.nc.kpi.sorters;

import com.nc.kpi.sorters.bubble.InverseBubbleSorter;

public class InverseBubbleSorterTest extends SorterTest {
    @Override
    public void initSorter() {
        sorter = new InverseBubbleSorter();
    }
}
