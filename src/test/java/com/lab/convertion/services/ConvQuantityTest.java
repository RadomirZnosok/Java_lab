package com.lab.convertion.services;

import com.lab.convertion.entity.MyExceptions;
import com.lab.convertion.entity.PhysQuantity;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class ConvQuantityTest {
    PhysQuantity param;
    private ConvQuantity convQuantity = new ConvQuantity();

    @Test
    public void inchToMetreTest() {
        assertDoesNotThrow(()->{
            param = convQuantity.inchToMetre(new PhysQuantity("inch", 34.));
            assertThat(param).isNotNull();
            param = convQuantity.inchToMetre(new PhysQuantity("inch", 0.));
            assertThat(param.getNum()).isEqualTo(0);
            param = convQuantity.inchToMetre(new PhysQuantity("inch", 6.));
            assertThat(param.getNum()).isEqualTo(6*0.0254);
        });
        Assertions.assertThrows(MyExceptions.class, ()->convQuantity.inchToMetre(new PhysQuantity("metre", 6.)));
    }

    @Test
    public void metreToInchTest() {
        assertDoesNotThrow(()->{
            param = convQuantity.metreToInch(new PhysQuantity("metre", 5.));
            assertThat(param).isNotNull();
            param = convQuantity.metreToInch(new PhysQuantity("metre", 0.));
            assertThat(param.getNum()).isEqualTo(0);
            param = convQuantity.metreToInch(new PhysQuantity("metre", 5.));
            assertThat(param.getNum()).isEqualTo(5*39.37);
        });
        Assertions.assertThrows(MyExceptions.class, ()->convQuantity.metreToInch(new PhysQuantity("inch", 6.)));
    }
}
