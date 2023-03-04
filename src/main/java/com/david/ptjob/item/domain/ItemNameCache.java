package com.david.ptjob.item.domain;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class ItemNameCache {

    private ConcurrentMap<String, String> itemNames = new ConcurrentHashMap<>();

    public void addAll(List<String> names) {
        names.stream().forEach(name -> itemNames.put(name, name));
    }

    public boolean isPresent(String name) {
        return itemNames.containsKey(name);
    }
}
