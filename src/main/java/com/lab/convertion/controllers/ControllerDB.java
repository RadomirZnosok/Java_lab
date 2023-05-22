package com.lab.convertion.controllers;

import com.lab.convertion.entity.ConvResponse;
import com.lab.convertion.entity.CounterCalls;
import com.lab.convertion.entity.PhysQuantity;
import com.lab.convertion.models.DbEntity;
import com.lab.convertion.services.*;
import com.lab.convertion.validators.ValidatorParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/lab/db")
public class ControllerDB {
    @Autowired
    private DbRepositoryService dbPostRepository;
    private static final Logger logger = LoggerFactory.getLogger(ControllerConvert.class);
    private final CounterCallsServer counter;
    private final ConvQuantity convert;// = new ConvQuantity();
    private final StatisticCollector statisticCollector;// = new StatisticCollector();
    private final ValidatorParam validator;


    public ControllerDB(CounterCallsServer counter, ConvQuantity convert, StatisticCollector statisticCollector, ValidatorParam validator){
        this.counter = counter;
        this.convert = convert;
        this.statisticCollector = statisticCollector;
        this.validator = validator;
    }

    @GetMapping("/history")
    public ResponseEntity<?> getListResults(){
        List<DbEntity> list = dbPostRepository.listAll();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @GetMapping("/inch")
    public ResponseEntity<?> convertInch(@RequestParam String num) {
        counter.increase();
        logger.info("Start of inch-to-metre.");
        if (dbPostRepository.isHas(num+" inch")) {
            logger.info("Result is found in DB.");
            ConvResponse<String> response = new ConvResponse<>("OK", 200);
            response.addResp(dbPostRepository.get(num+" inch").toString());
            return statisticCollector.retResp(response);
        }
        logger.info("Getting param...");
        ConvResponse<String> respInfo = validator.isNum(num);
        if (respInfo.getRespStatus() == 0){
            logger.info("Getting param: success.");
            PhysQuantity physQuantity = convert.conversionPh(new PhysQuantity("inch", Double.parseDouble(num)));
            if (physQuantity.getType().equals("metre")){
                respInfo.setRespStatus(200);
                respInfo.setStatus("OK");
                logger.info("Conversion completed. Saving in DB.");
                dbPostRepository.save(new DbEntity(num+" inch", physQuantity));
                respInfo.addResp("Result: " + physQuantity.toString());
            }
            else{
                respInfo.setRespStatus(500);
                respInfo.setStatus("ERROR");
                respInfo.addResp("Error when trying to convert a physical quantity.");
            }
        }
        return statisticCollector.retResp(respInfo);
    }

    @GetMapping("/metre")
    public ResponseEntity<?> convertMetre(@RequestParam String num) {
        counter.increase();
        logger.info("Start of metre-to-inch.");
        if (dbPostRepository.isHas(num+" metre")) {
            logger.info("Result is found in DB.");
            ConvResponse<String> response = new ConvResponse<>("OK", 200);
            response.addResp(dbPostRepository.get(num+" metre").toString());
            return statisticCollector.retResp(response);
        }
        logger.info("Getting param...");
        ConvResponse<String> respInfo = validator.isNum(num);
        if (respInfo.getRespStatus() == 0){
            logger.info("Getting param: success.");
            PhysQuantity physQuantity = convert.conversionPh(new PhysQuantity("metre", Double.parseDouble(num)));
            if (physQuantity.getType().equals("inch")){
                respInfo.setRespStatus(200);
                respInfo.setStatus("OK");
                logger.info("Conversion completed. Saving in DB.");
                dbPostRepository.save(new DbEntity(num+" metre", physQuantity));
                respInfo.addResp("Result: " + physQuantity.toString());
            }
            else{
                respInfo.setRespStatus(500);
                respInfo.setStatus("ERROR");
                respInfo.addResp("Error when trying to convert a physical quantity.");
            }
        }
        return statisticCollector.retResp(respInfo);
    }

    @DeleteMapping("/delparam")
    public ResponseEntity<?> deleteFromDB(@RequestParam String param){
        ConvResponse<String> response = new ConvResponse<>("DELETED", 200);
        if (dbPostRepository.isHas(param)){
            dbPostRepository.delete(param);
            response.addResp(param + "deleted from DB.");
        }
        else{
            response.addResp("Incorrect param or DB haven't " + param);
            response.setRespStatus(404);
            response.setStatus("NOT_FOUND");
        }
        return statisticCollector.retResp(response);
    }
    @DeleteMapping("/delall")
    public ResponseEntity<?> deleteAllFromDB(){
        Integer size = dbPostRepository.count();
        dbPostRepository.deleteAll();
        ConvResponse<String> response = new ConvResponse<>("DELETED", 200);
        response.addResp("DB have been cleaned.");
        return statisticCollector.retResp(response);
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
            ConvResponse<String> respInfo = validator.isNum(newParam.getNum().toString());
            if (respInfo.getRespStatus() == 0){
                logger.info("Success getting param. Param will add to DB.");
                dbPostRepository.save(new DbEntity(newParam.toString(), newParam));
                resp.addResp(newParam.getNum() + "" + newParam.getType() + " is created.");
            }
            else {
                logger.error("Bad param from Post Request.");
                resp.addResp(newParam.getNum() + "" + newParam.getType() + " can't created.");
                resp.setRespStatus(400);
            }
        }
        return statisticCollector.retResp(resp);
    }

    @GetMapping("/info/results")
    public ResponseEntity<?> getInfoOfResults(){
        counter.increase();
        List<DbEntity> list = dbPostRepository.listAll();
        List<PhysQuantity> listResult = new ArrayList<>();
        for(DbEntity param: list){
            listResult.add(new PhysQuantity(param.getType(), param.getNumber()));
        }
        ConvResponse<String> response = new ConvResponse<>("OK", 200);
        if (listResult.size() > 0){
            response = statisticCollector.getInfoResults(listResult, "metre", response);
            response = statisticCollector.getInfoResults(listResult, "inch", response);
        }
        else{
            response.addResp("list of results is empty.");
        }
        return statisticCollector.retResp(response);
    }
}
