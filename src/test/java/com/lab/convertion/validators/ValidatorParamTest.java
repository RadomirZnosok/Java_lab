package com.lab.convertion.validators;

import com.lab.convertion.entity.ConvResponse;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ValidatorParamTest {
    boolean check;
    ConvResponse<String> resp;
    private final ValidatorParam validatorParam = new ValidatorParam();

    @Test
    public void checkIsNum(){
        assertThat(resp).isNull();
        resp = validatorParam.isNum("");
        assertThat(resp.getRespStatus()).isEqualTo(404);
        resp = validatorParam.isNum("asd");
        assertThat(resp.getRespStatus()).isEqualTo(400);
        resp = validatorParam.isNum("-2");
        assertThat(resp.getRespStatus()).isEqualTo(400);
    }
    @Test
    public void checkIsNumeric(){
        check = validatorParam.isNumeric("23");
        assertThat(check).isEqualTo(true);
        check = validatorParam.isNumeric("asd");
        assertThat(check).isEqualTo(false);
        check = validatorParam.isNumeric("-4");
        assertThat(check).isEqualTo(true);
        check = validatorParam.isNumeric("0");
        assertThat(check).isEqualTo(true);
    }
}
