package com.lab.convertion.services;

import com.lab.convertion.entity.ConvResponse;
import com.lab.convertion.entity.PhysQuantity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class StatisticCollector {

    public ConvResponse<String> getInfoResults(List<PhysQuantity> list, String type, ConvResponse<String> response){
        List<PhysQuantity> info = new ArrayList<>();

        info.add(0, new PhysQuantity(type, Double.MAX_VALUE));
        list.stream()
                .filter(e -> Objects.equals(e.getType(), type))
                .forEach((e) -> {
                    if(info.get(0).getNum() > e.getNum())
                        info.add(0, e);//min value
                });

        info.add(1, new PhysQuantity(type, Double.MIN_VALUE));
        list.stream()
                .filter(e -> Objects.equals(e.getType(), type))
                .forEach((e) -> {
                    if(info.get(1).getNum() < e.getNum())
                        info.add(1, e);//max value
                });

        info.add(2, new PhysQuantity(type, 0.));
        list.stream()
                .filter(e -> Objects.equals(e.getType(), type))
                .forEach((e) -> {
                    info.add(2, new PhysQuantity(type, info.get(2).getNum() + e.getNum()));//sum
                });
        info.add(2, new PhysQuantity(type, info.get(2).getNum() / list.size()));

        response.addResp("min result: " + info.get(0).toString());
        response.addResp("max result: " + info.get(1).toString());
        response.addResp("average value of results: " + info.get(2).toString());

        return response;
    }
    public ResponseEntity<?> retResp(ConvResponse<?> response){
        if (response.getRespStatus() == 200){
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        if (response.getRespStatus() == 201){
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }
        else if (response.getRespStatus() == 404) { // for other exception
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
        else if (response.getRespStatus() == 400) {
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        else if (response.getRespStatus() == 500){
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        else {
            return new ResponseEntity<>(/*resp,*/ HttpStatus.NO_CONTENT);
        }
    }
}
