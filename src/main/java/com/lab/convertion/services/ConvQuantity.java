package com.lab.convertion.services;

import com.lab.convertion.entity.ConvResponse;
import com.lab.convertion.entity.MyExceptions;
import com.lab.convertion.entity.PhysQuantity;
import org.springframework.stereotype.Service;

@Service
public class ConvQuantity implements ConvertInterface {
    public ConvQuantity(){}
    @Override
    public PhysQuantity inchToMetre(PhysQuantity inch) throws MyExceptions{
        if (inch.getType().equals("inch")) {
            inch.setType("metre");
            inch.setNum(inch.getNum() * 0.0254);
            return inch;
        }
        else {
            throw new MyExceptions("Incorrect parameter. Expected inch.");
        }
    }
    @Override
    public PhysQuantity metreToInch(PhysQuantity metre) throws MyExceptions{
        if (metre.getType().equals("metre")) {
            metre.setType("inch");
            metre.setNum(metre.getNum() * 39.37);
            return metre;
        }
        else {
            throw new MyExceptions("Incorrect parameter. Expected inch.");
        }
    }
    public ConvResponse<?> conversion(PhysQuantity param){
        String oldType = param.getType();
        try {
            ConvResponse<PhysQuantity> resp = new ConvResponse<>("OK", 200);
            if (oldType.equals("inch"))
                resp.addResp(this.inchToMetre(param));
            else if (oldType.equals("metre"))
                resp.addResp(this.metreToInch(param));
            return resp;
        }
        catch(MyExceptions | Exception myError){
            ConvResponse<String> respErr = new ConvResponse<>("Internal error", 500);
            respErr.addResp(myError.getMessage());
            return respErr;
        }
    }

    public PhysQuantity conversionPh(PhysQuantity param){
        String oldType = param.getType();
        PhysQuantity physQuantity;
        try {
            if (oldType.equals("inch"))
                physQuantity = this.inchToMetre(param);
            else if (oldType.equals("metre"))
                physQuantity = this.metreToInch(param);
            else
                physQuantity = new PhysQuantity("");
            return physQuantity;
        }
        catch(MyExceptions | Exception myError){
            physQuantity = new PhysQuantity("");
            return physQuantity;
        }
    }
}
