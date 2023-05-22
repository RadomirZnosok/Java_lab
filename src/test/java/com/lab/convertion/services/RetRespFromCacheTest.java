package com.lab.convertion.services;

import com.lab.convertion.entity.PhysQuantity;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.*;

public class RetRespFromCacheTest {

    MemoryCache<String, PhysQuantity> memoryCache;
    @Test
    public void getRespFromMemory(){
        PhysQuantity putParam = new PhysQuantity("metre", 12.);
        memoryCache = new MemoryCache<>();
        memoryCache.add("12metre", putParam);
        RetRespFromCache<String, PhysQuantity> func1 = new RetRespFromCache<>(memoryCache);
        PhysQuantity param = func1.getRespFromMemory("12metre");
        assertThat(param).isEqualTo(putParam);
    }
}
