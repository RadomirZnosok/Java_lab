package com.lab.convertion.services;

import com.lab.convertion.controllers.ControllerConvert;
import com.lab.convertion.entity.MyExceptions;
import com.lab.convertion.entity.PhysQuantity;

import java.util.List;

public class Algorithms {
    final private ConvQuantity converter = new ConvQuantity();

    public void sortByInch(List<PhysQuantity> list){
        for(PhysQuantity param: list){
            try {
                if (param.getType().equals("metre")) {
                    list.add(list.indexOf(param), converter.metreToInch(param));
                }
            }
            catch(MyExceptions error){}
            catch(Exception myError){
                //logger.error(myError.getMessage());
                return;
            }
        }
    }

    public void sortByMetre(List<PhysQuantity> list){
        for(PhysQuantity param: list){
            try {
                if (param.getType().equals("inch")) {
                    list.add(list.indexOf(param), converter.inchToMetre(param));
                }
            }
            catch(MyExceptions error){}
            catch(Exception myError){
                //logger.error(myError.getMessage());
                return;
            }
        }
    }

    public PhysQuantity getMin(List<PhysQuantity> list){
        int i = 0;
        Double min = -1.;
        //проверка на пустоту листа
        if (list.size()>0){
            for(PhysQuantity param: list){
                if (min > param.getNum()){
                    i = list.indexOf(param);
                }
            }
            return list.get(i);
        }
        else{
            return new PhysQuantity("", 0.);
        }
    }
    public PhysQuantity getMax(List<PhysQuantity> list){
        int i = 0;
        Double max = -1.;
        //проверка на пустоту листа
        if (list.size()>0){
            for(PhysQuantity param: list){
                if (max < param.getNum()){
                 i = list.indexOf(param);
                }
            }
            return list.get(i);
        }
        else{
            return new PhysQuantity("", 0.);
        }
    }
    public PhysQuantity getAverageValue(List<PhysQuantity> list){
        int i = 0;
        Double average = 0.;
        //проверка на пустоту листа
        if (list.size()>0){
            for(PhysQuantity param: list){
                average += param.getNum();
            }
            average/=list.size();
            return new PhysQuantity(list.get(i).getType(), average);
        }
        else{
            return new PhysQuantity("", 0.);
        }
    }
}
