package com.nc.kpi.sorters;

public abstract class Sorter {

    public void sort(int[] array){
        isArrayNull(array);
        doSort(array);
    }

    protected abstract void doSort(int[] array);

    protected void swap(int[] array, int pos1, int pos2) {
        int arrayElement = array[pos1];
        array[pos1] = array[pos2];
        array[pos2] = arrayElement;
    }

    protected void isArrayNull(int[] array) {
        if (array == null) {
            throw new IllegalArgumentException("Array is null");
        }
    }
}
