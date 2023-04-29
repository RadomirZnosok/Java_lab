package com.lab.convertion.entity;

import java.util.ArrayList;
import java.util.List;

public final class ConvResponse<T> {
    private final List<T> respMes = new ArrayList<>();
    private String status;
    private int respStatus;

    public ConvResponse(String tStatus, int numStatus){
        this.status = tStatus;
        this.respStatus = numStatus;
    }
    public int getRespStatus() {
        return respStatus;
    }
    public List<T> getRespMes(){
        return respMes;
    }
    public void addResp(T newResp){
        this.respMes.add(newResp);
    }
    public void setStatus(String status){
        this.status = status;
    }
    public void setRespStatus (int respStatus){
        this.respStatus = respStatus;
    }
}
