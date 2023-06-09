package com.lab.convertion.services;

import org.springframework.stereotype.Service;

@Service
public class RetRespFromCache<key,value> {
    private final MemoryCache<key, value> memoryCache;
    public RetRespFromCache(MemoryCache<key, value> tMemoryCache){
        this.memoryCache = tMemoryCache;
    }
    public value getRespFromMemory(key id){
        return memoryCache.get(id);
    }
}
