package com.lab.convertion.entity;

public class PhysQuantity {//PhysQuantity
    private String type;
    private Double number;
    public PhysQuantity(String tType){
        type = tType;
        this.number = 0.;
    }
    public PhysQuantity(String tType, Double number){
        type = tType;
        this.number = number;
    }
    public String getType(){
        return type;
    }
    public void setType(String type){
        this.type = type;
    }
    public void setNum(double newNum) {
        this.number = newNum;
    }
    public Double getNum(){
        return this.number;
    }

    @Override
    public String toString(){
        return new String(this.number + " " + this.type);
    }
}
