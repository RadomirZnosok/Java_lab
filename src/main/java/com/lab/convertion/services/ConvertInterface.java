package com.lab.convertion.services;


import com.lab.convertion.entity.MyExceptions;
import com.lab.convertion.entity.PhysQuantity;

public interface ConvertInterface {
    PhysQuantity inchToMetre(PhysQuantity inch) throws MyExceptions;
    PhysQuantity metreToInch(PhysQuantity metre) throws MyExceptions;
}
