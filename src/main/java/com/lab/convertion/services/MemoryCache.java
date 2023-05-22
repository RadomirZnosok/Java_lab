package com.lab.convertion.services;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MemoryCache<key, value> {
    final private Map<key, value> memStorage;
                                //как положить только определённые классы?//смотреть..generic quantity
    public MemoryCache() {
        memStorage = new HashMap<>();
    }
    public Map<key, value> getAll(){
        return memStorage;
    }
    public void add(key id, value info) {
        memStorage.put(id, info);
    }
    public value get(key id) {
        return memStorage.get(id);
    }
    public boolean isHas(key id) {
        return memStorage.size() > 0 && memStorage.containsKey(id);
    }
/*    public void popAll(key id) {
        memStorage.remove(id);
    }
    public void addMap(Map<key, value> addedMap){
        memStorage.putAll(addedMap);
    }
    public int getsize(){
        return this.memStorage.size();
    }
 */
}
