package com.nc.kpi.sorters;

import com.nc.kpi.sorters.halfdivision.HoareSorter;

public class HoareSorterTest extends SorterTest {

    @Override
    public void initSorter() {
        sorter = new HoareSorter();
    }
}
