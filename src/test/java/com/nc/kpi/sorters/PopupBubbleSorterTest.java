package com.nc.kpi.sorters;

import com.nc.kpi.sorters.bubble.PopupBubbleSorter;

public class PopupBubbleSorterTest extends SorterTest {
    @Override
    public void initSorter() {
        sorter = new PopupBubbleSorter();
    }
}
