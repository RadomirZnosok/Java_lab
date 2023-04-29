package com.lab.convertion.controllers;

import com.lab.convertion.entity.*;
import com.lab.convertion.services.Algorithms;
import com.lab.convertion.services.ConvQuantity;
import com.lab.convertion.services.CounterCallsServer;
import com.lab.convertion.services.RetRespFromCache;
import com.lab.convertion.validators.ValidatorParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/lab")
public class ControllerConvert {
    private static final Logger logger = LoggerFactory.getLogger(ControllerConvert.class);
    private final ConvQuantity convert = new ConvQuantity();
    private final MemoryCache<String, ConvResponse<?>> conversionCache = new MemoryCache<>();
    private final List<PhysQuantity> listResult = new ArrayList<>();
    private final CounterCallsServer counter= new CounterCallsServer();
    private final Algorithms algorithms = new Algorithms();
    public ControllerConvert(){}

    @GetMapping("/inch")
    public ResponseEntity<?> convertInch(@RequestParam String num) {
        counter.increase();
        logger.info("Start of inch-to-metre.");
        if (conversionCache.isHas(num+"inch")) {
            logger.info("Result is found in memory cache.");
            RetRespFromCache<String, ConvResponse<?>> retRespFromCache = new RetRespFromCache<>(conversionCache);
            return retResp(retRespFromCache.getRespFromMemory(num+"inch"));
        }
        logger.info("Getting param...");
        ValidatorParam validator = new ValidatorParam();
        ConvResponse<String> respInfo = validator.isNum(num);
        if (respInfo.getRespStatus() == 0){
            logger.info("Getting param: success.");
            PhysQuantity gotParam = new PhysQuantity("inch", Double.parseDouble(num));
            ConvResponse<?> resp = convert.conversion(gotParam);
            logger.info("Conversion completed.");
            conversionCache.add(num+"inch", resp);
            listResult.add(gotParam);
            return retResp(resp);
        }
        else {
            conversionCache.add(num+"inch", respInfo);
            return retResp(respInfo);
        }
    }

    @GetMapping("/metre")
    public ResponseEntity<?> convertMetre(@RequestParam String num) {
        counter.increase();
        logger.info("Start of metre-to-inch.");
        if (conversionCache.isHas(num+"metre")) {
            logger.info("Result is found in memory cache.");
            RetRespFromCache<String, ConvResponse<?>> retRespFromCache = new RetRespFromCache<>(conversionCache);
            return retResp(retRespFromCache.getRespFromMemory(num+"metre"));
        }
        logger.info("Getting param...");
        ValidatorParam validator = new ValidatorParam();
        ConvResponse<String> respInfo = validator.isNum(num);
        if (respInfo.getRespStatus() == 0){
            logger.info("Getting param: success.");
            PhysQuantity gotParam = new PhysQuantity("metre", Double.parseDouble(num));
            ConvResponse<?> resp = convert.conversion(gotParam);
            logger.info("Conversion completed.");
            conversionCache.add(num+"metre", resp);
            listResult.add(gotParam);
            return retResp(resp);
        }
        else {
            conversionCache.add(num+"metre", respInfo);
            return retResp(respInfo);
        }
    }

    @GetMapping("/history")
    public ResponseEntity<?> funcOfGetHistory(){
        counter.increase();
        return new ResponseEntity<>(conversionCache.getAll(), HttpStatus.OK);
    }

    @GetMapping("/results")
    public ResponseEntity<?> funcOfGetResults(){
        counter.increase();
        return new ResponseEntity<>(listResult, HttpStatus.OK);
    }

    @GetMapping("/counter")
    public CounterCalls getCounters(){
        return counter.getCounter();
    }

    @PostMapping("/param/add")
    public ResponseEntity<?> addParam(@RequestBody List<PhysQuantity> params){
        counter.increase();
        ConvResponse<String> resp = new ConvResponse<>("", 201);
        for (PhysQuantity newParam: params) {
            ValidatorParam validator = new ValidatorParam();
            ConvResponse<String> respInfo = validator.isNum(newParam.getNum().toString());
            if (respInfo.getRespStatus() == 0){
                logger.info("Success getting param. Param will add to list.");
                listResult.add(newParam);
                resp.addResp(newParam.getNum() + "" + newParam.getType() + " is created.");
            }
            else {
                logger.error("Bad param from Post Request.");
                resp.addResp(newParam.getNum() + "" + newParam.getType() + " can't created.");
                resp.setRespStatus(400);
            }
        }
        return retResp(resp);
    }

    @GetMapping("/info/results")
    public ResponseEntity<?> getInfoOfResults(){
        counter.increase();
        ConvResponse<String> response = new ConvResponse<>("OK", 200);
        if (listResult.size()>0){
            PhysQuantity physQuantity = algorithms.getMin(listResult);
            response.addResp("min result: "+ physQuantity.getNum() +
                    " "+ physQuantity.getType());
            physQuantity = algorithms.getMax(listResult);
            response.addResp("max result: "+ physQuantity.getNum() +
                    " "+ physQuantity.getType());
            physQuantity = algorithms.getAverageValue(listResult);
            response.addResp("average value of results: "+ physQuantity.getNum() +
                    " "+ physQuantity.getType());
        }
        else{
            response.addResp("list of results is empty.");
        }
        return retResp(response);
    }

    private ResponseEntity<?> retResp(ConvResponse<?> response){
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

    /*public ConvResponse<?> conversion(PhysQuantity param){
        String oldType = param.getType();
        try {
            ConvResponse<PhysQuantity> resp = new ConvResponse<>("OK", 200);
            if (oldType.equals("inch"))
                resp.addResp(convert.inchToMetre(param));
            else if (oldType.equals("metre"))
                resp.addResp(convert.metreToInch(param));
            return resp;
        }
        catch(MyExceptions | Exception myError){
            logger.error(myError.getMessage());
            ConvResponse<String> respErr = new ConvResponse<>("Internal error", 500);
            respErr.addResp(myError.getMessage());
            return respErr;
        }
    }
*/

}