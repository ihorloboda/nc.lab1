package com.nc.kpi;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Map<Integer, Integer> map  = new HashMap<>();
        map.put(1,2);
        map.remove(1);
        System.out.println(map.isEmpty());
    }
}
