package com.lab.convertion.validators;

import com.lab.convertion.entity.ConvResponse;

public interface ValidatorParamInf {
    public ConvResponse<?> isNum(String number);
    public boolean isNumeric(String number);
}
