package com.lab.convertion.controllers;


import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

public class ControllerConvertTest {
    private ResponseEntity<?> responseEntity;
    private final ControllerConvert controllerConvert = new ControllerConvert();

    @Test
    public void ControllerConvertTest(){
        responseEntity = controllerConvert.convertInch("23");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        responseEntity = controllerConvert.convertInch("23");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        responseEntity = controllerConvert.convertInch("");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        responseEntity = controllerConvert.convertInch("asd");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        responseEntity = controllerConvert.convertInch("-1");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);

        responseEntity = controllerConvert.convertMetre("23");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        responseEntity = controllerConvert.convertMetre("23");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        responseEntity = controllerConvert.convertMetre("");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        responseEntity = controllerConvert.convertMetre("asd");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        responseEntity = controllerConvert.convertMetre("-1");
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);


    }
}
