package com.lab.convertion.services;

import com.lab.convertion.entity.ConvResponse;
import com.lab.convertion.entity.MyExceptions;
import com.lab.convertion.entity.PhysQuantity;
import com.lab.convertion.validators.ValidatorParam;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.mock;

public class ConvQuantityTest {
    private PhysQuantity param;
    private ConvResponse<?> response;
    private final ConvQuantity convQuantity = new ConvQuantity();

    @Test
    public void inchToMetreTest(){
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
    public void metreToInchTest(){
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

    @Test
    public void conversionTest(){
        assertDoesNotThrow(()->{
            assertThat(convQuantity.conversion(new PhysQuantity("metre", 5.))
                    .getRespStatus())
                    .isEqualTo(200);
//            assertThat(convQuantity.conversion(new PhysQuantity("metre", 5.))
//                    .getRespMes()
//                    .get(0))
//                    .isEqualTo(new PhysQuantity("inch", 5 * 39.37));
            assertThat(convQuantity.conversion(new PhysQuantity("inch", 6.))
                    .getRespStatus()).isEqualTo(200);
//            assertThat(convQuantity.conversion(new PhysQuantity("inch", 6.))
//                    .getRespMes()
//                    .get(0))
//                    .isEqualTo(new PhysQuantity("metre", 6*0.0254));
        });
    }
}
