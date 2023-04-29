package com.lab.convertion.validators;

import com.lab.convertion.entity.ConvResponse;
import com.lab.convertion.controllers.ControllerConvert;
import io.micrometer.common.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ValidatorParam implements ValidatorParamInf {
    private static final Logger logger = LoggerFactory.getLogger(ControllerConvert.class);
    private ConvResponse<String> resp = new ConvResponse<>("", 0);

    @Override
    public ConvResponse<String> isNum(String number) {  // return bool
        if (StringUtils.isEmpty(number)) {
            resp.setStatus("NOT_FOUND");
            resp.setRespStatus(404);
            resp.addResp("GET_MAPPING doesn't have String");
            logger.error("Error. Don't have param.");
        }
        else if (!isNumeric(number)) {
                resp.setStatus("BAD_REQUEST");
                resp.setRespStatus(400);
                resp.addResp("Getting param isn't number.");
                logger.error("Error. Not number.");
            }
            else if (Double.parseDouble(number) < 0) {
                resp.setStatus("BAD_REQUEST");
                resp.setRespStatus(400);
                resp.addResp("Getting param is negative number.");
                logger.error("Error. Negative number.");
            }
            return resp;
    }

    @Override
    public boolean isNumeric(String number) {
        double testDouble;
        try {
            testDouble = Double.parseDouble(number);
            return true;
        }
        catch (Exception exp){
            return false;
        }
    }
}
