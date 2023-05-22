package com.lab.convertion.controllers;

import com.lab.convertion.entity.*;
import com.lab.convertion.services.*;
import com.lab.convertion.validators.ValidatorParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/lab")
public class ControllerConvert {
    private static final Logger logger = LoggerFactory.getLogger(ControllerConvert.class);
    private final ConvQuantity convert;// = new ConvQuantity();
    private final MemoryCache<String, ConvResponse<?>> conversionCache;// = new MemoryCache<>();
    private final List<PhysQuantity> listResult;// = new ArrayList<>();
    private final CounterCallsServer counter;// = new CounterCallsServer();
    private final StatisticCollector statisticCollector;// = new StatisticCollector();
    private final ValidatorParam validator;

    //@Autowired
    public ControllerConvert(ConvQuantity convert,
                             MemoryCache<String, ConvResponse<?>> memoryCache,
                             List<PhysQuantity> listResult,
                             CounterCallsServer counter,
                             StatisticCollector statisticCollector,
                             ValidatorParam validator
                             ){
        this.convert = convert;
        this.conversionCache = memoryCache;
        this.listResult = listResult;
        this.counter = counter;
        this.statisticCollector = statisticCollector;
        this.validator = validator;
    }

    @GetMapping("/inch")
    public ResponseEntity<?> convertInch(@RequestParam String num) {
        counter.increase();
        logger.info("Start of inch-to-metre.");
        if (conversionCache.isHas(num+"inch")) {
            logger.info("Result is found in memory cache.");
            RetRespFromCache<String, ConvResponse<?>> retRespFromCache = new RetRespFromCache<>(conversionCache);
            return statisticCollector.retResp(retRespFromCache.getRespFromMemory(num+"inch"));
        }
        logger.info("Getting param...");
        ConvResponse<String> respInfo = validator.isNum(num);
        if (respInfo.getRespStatus() == 0){
            logger.info("Getting param: success.");
            PhysQuantity gotParam = new PhysQuantity("inch", Double.parseDouble(num));
            ConvResponse<?> resp = convert.conversion(gotParam);
            logger.info("Conversion completed.");
            conversionCache.add(num+"inch", resp);
            listResult.add(gotParam);
            return statisticCollector.retResp(resp);
        }
        else {
            conversionCache.add(num+"inch", respInfo);
            return statisticCollector.retResp(respInfo);
        }
    }

    @GetMapping("/metre")
    public ResponseEntity<?> convertMetre(@RequestParam String num) {
        counter.increase();
        logger.info("Start of metre-to-inch.");
        if (conversionCache.isHas(num+"metre")) {
            logger.info("Result is found in memory cache.");
            RetRespFromCache<String, ConvResponse<?>> retRespFromCache = new RetRespFromCache<>(conversionCache);
            return statisticCollector.retResp(retRespFromCache.getRespFromMemory(num+"metre"));
        }
        logger.info("Getting param...");
        ConvResponse<String> respInfo = validator.isNum(num);
        if (respInfo.getRespStatus() == 0){
            logger.info("Getting param: success.");
            PhysQuantity gotParam = new PhysQuantity("metre", Double.parseDouble(num));
            ConvResponse<?> resp = convert.conversion(gotParam);
            logger.info("Conversion completed.");
            conversionCache.add(num+"metre", resp);
            listResult.add(gotParam);
            return statisticCollector.retResp(resp);
        }
        else {
            conversionCache.add(num+"metre", respInfo);
            return statisticCollector.retResp(respInfo);
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
        return statisticCollector.retResp(resp);
    }

    @GetMapping("/info/results")
    public ResponseEntity<?> getInfoOfResults(){
        counter.increase();
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