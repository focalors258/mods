package com.integration_package_core.tool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SortMap {


    Map<String, Integer> map = new HashMap<>();
    List<String> list;


    boolean update = true;


    public SortMap(Map<String, Integer> m) {
        map = m;
    }

    public SortMap() {
    }

    public void put(String k, int v) {

        update = true;
        map.put(k, v);
    }


    public String getKey(int i) {

        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .toList().get(i).getKey();

    }


    public int size(){

        return map.size();

    }


    public int getValue(int i) {

        return map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue())
                .toList().get(i).getValue();
    }

    public List<String> getList() {
        if (!update) return list;
        update = false;
        list = new ArrayList<>();
        map.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue()).toList().forEach(e -> {
                    list.add(e.getKey());
                });

        return list;
    }


}
