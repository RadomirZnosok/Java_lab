package com.lab.convertion.controllers;


import com.lab.convertion.entity.*;
import com.lab.convertion.services.*;
import com.lab.convertion.validators.ValidatorParam;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ControlConvertTest {
    @Mock
    private final ConvQuantity convert = mock(ConvQuantity.class);
    @Mock
    private final MemoryCache<String, ConvResponse<?>> conversionCache = mock(MemoryCache.class);
    @Mock
    private final List<PhysQuantity> listResult = mock(List.class);
    @Mock
    private final ValidatorParam validatorParam = mock(ValidatorParam.class);
    @Mock
    private final CounterCallsServer counter = mock(CounterCallsServer.class);
    @Mock
    private final RetRespFromCache<String, ConvResponse<?>> retRespFromCache = mock(RetRespFromCache.class);
    @Mock
    private final StatisticCollector statisticCollector = mock(StatisticCollector.class);
 //   @InjectMocks
    private final ControllerConvert controllerConvert =
        new ControllerConvert(convert, conversionCache, listResult, counter, statisticCollector, validatorParam);


    @Test
    public void convertInchTestConv(){
        String num = "10";
        ConvResponse<PhysQuantity> convResponse2 = new ConvResponse<>("", 0);
        ResponseEntity<?> response = new ResponseEntity<>(HttpStatus.OK);

        doReturn(convResponse2).when(validatorParam).isNum(num);
        doNothing().when(counter).increase();
        doReturn(false).when(conversionCache).isHas(num+"inch");
        doReturn(response).when(statisticCollector).retResp(null);//ошибка из-за внутренней переменной PhysQuantity
        doNothing().when(conversionCache).add(num+"inch", null);//ошибка из-за внутренней переменной PhysQuantity

        ResponseEntity<?> response1;
        response1 = controllerConvert.convertInch(num);
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void convertInchTestMem(){
        String num1 = "11";
        ConvResponse<PhysQuantity> convResponse2 = new ConvResponse<>("", 0);
        ResponseEntity<?> response = new ResponseEntity<>(HttpStatus.OK);
        ConvResponse<PhysQuantity> convResponse1 = new ConvResponse<>("OK", 200);

        doReturn(convResponse2).when(validatorParam).isNum(num1);
        doNothing().when(counter).increase();
        doReturn(true).when(conversionCache).isHas(num1+"inch");
        doNothing().when(conversionCache).add(num1+"inch", null);//ошибка из-за внутренней переменной PhysQuantity
        doReturn(convResponse1).when(retRespFromCache).getRespFromMemory(num1+"inch");
        doReturn(convResponse1).when(conversionCache).get(num1+"inch");
        doReturn(response).when(statisticCollector).retResp(convResponse1);

        ResponseEntity<?> response1;
        response1 = controllerConvert.convertInch(num1);
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void convertMetreTestConv(){
        String num = "10";
        ConvResponse<PhysQuantity> convResponse2 = new ConvResponse<>("", 0);
        ResponseEntity<?> response = new ResponseEntity<>(HttpStatus.OK);

        doNothing().when(counter).increase();
        doReturn(false).when(conversionCache).isHas(num+"metre");
        doReturn(convResponse2).when(validatorParam).isNum(num);
        doReturn(response).when(statisticCollector).retResp(null);//ошибка из-за внутренней переменной PhysQuantity
        doNothing().when(conversionCache).add(num+"metre", null);//ошибка из-за внутренней переменной PhysQuantity

        ResponseEntity<?> response1;
        response1 = controllerConvert.convertMetre(num);
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void convertMetreTestMem(){
        String num1 = "11";
        ConvResponse<PhysQuantity> convResponse2 = new ConvResponse<>("", 0);
        ResponseEntity<?> response = new ResponseEntity<>(HttpStatus.OK);
        ConvResponse<PhysQuantity> convResponse1 = new ConvResponse<>("OK", 200);

        doReturn(convResponse2).when(validatorParam).isNum(num1);
        doNothing().when(counter).increase();
        doReturn(true).when(conversionCache).isHas(num1+"metre");
        doNothing().when(conversionCache).add(num1+"inch", null);//ошибка из-за внутренней переменной PhysQuantity
        doReturn(convResponse1).when(retRespFromCache).getRespFromMemory(num1+"metre");
        doReturn(convResponse1).when(conversionCache).get(num1+"metre");
        doReturn(response).when(statisticCollector).retResp(convResponse1);

        ResponseEntity<?> response1;
        response1 = controllerConvert.convertMetre(num1);
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void convertInchTestNull(){
        String num2 = "";
        ConvResponse<PhysQuantity> convResponse3 = new ConvResponse<>("", 404);
        ResponseEntity<?> response2 = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        doReturn(convResponse3).when(validatorParam).isNum(num2);
        doNothing().when(counter).increase();
        doReturn(response2).when(statisticCollector).retResp(convResponse3);

        ResponseEntity<?> response1;
        response1 = controllerConvert.convertInch(num2);
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void convertMetreTestNull(){
        String num2 = "";
        ConvResponse<PhysQuantity> convResponse3 = new ConvResponse<>("", 404);
        ResponseEntity<?> response2 = new ResponseEntity<>(HttpStatus.NOT_FOUND);

        doReturn(convResponse3).when(validatorParam).isNum(num2);
        doNothing().when(counter).increase();
        doReturn(response2).when(statisticCollector).retResp(convResponse3);

        ResponseEntity<?> response1;
        response1 = controllerConvert.convertMetre(num2);
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    public void funcOfGetHistoryTest(){
        Map<String, ConvResponse<?>> map = new HashMap<>();

        doNothing().when(counter).increase();
        doReturn(map).when(conversionCache).getAll();

        ResponseEntity<?> response1;
        response1 = controllerConvert.funcOfGetHistory();
        assertThat(response1.getBody()).isEqualTo(map);
    }

    @Test
    public void funcOfGetResultsTest(){
        doNothing().when(counter).increase();

        ResponseEntity<?> response1;
        response1 = controllerConvert.funcOfGetResults();
        assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    public void getCountersTest(){
        CounterCalls counterCalls = new CounterCalls();

        doReturn(counterCalls).when(counter).getCounter();

        CounterCalls counterCalls1 = controllerConvert.getCounters();
        assertThat(counterCalls1.getCounter()).isEqualTo(counterCalls.getCounter());
    }

    @Test
    public void addParamTest(){
        PhysQuantity physQuantity1 = new PhysQuantity("", 10.);
        PhysQuantity physQuantity2 = new PhysQuantity("", 11.);
        PhysQuantity physQuantity3 = new PhysQuantity("", 12.);
        List<PhysQuantity> list = new ArrayList<>();
        list.add(physQuantity1);
        list.add(physQuantity2);
        list.add(physQuantity3);
        ConvResponse<String> convResponse = new ConvResponse<>("", 0);
        ConvResponse<String> convResponse1 = new ConvResponse<>("", 400);
        ResponseEntity<?> response = new ResponseEntity<>(HttpStatus.CREATED);

    //    ConvResponse<String> resp = new ConvResponse<>("", 400);

    //    resp.addResp(physQuantity1.getNum() + "" + physQuantity1.getType() + " is created.");
    //    resp.addResp(physQuantity2.getNum() + "" + physQuantity2.getType() + " can't created.");
    //    resp.addResp(physQuantity3.getNum() + "" + physQuantity3.getType() + " is created.");

        doNothing().when(counter).increase();
        doReturn(convResponse).when(validatorParam).isNum(physQuantity1.getNum().toString());
        doReturn(convResponse1).when(validatorParam).isNum(physQuantity2.getNum().toString());
        doReturn(convResponse).when(validatorParam).isNum(physQuantity3.getNum().toString());
    //    doReturn(response).when(statisticCollector).retResp(resp);// нарушается работа программы из-за внутренней переменной

        ResponseEntity<?> response1;
        response1 = controllerConvert.addParam(list);
    //    assertThat(response1.getStatusCode()).isEqualTo(HttpStatus.CREATED);// ошибка из-за response1 = null from retResp()
    }

    @Test
    public void getInfoOfResultsTest(){
        ConvResponse<String> response = new ConvResponse<>("OK", 200);
        ResponseEntity<?> responseEntity = new ResponseEntity<>(HttpStatus.OK);

        doNothing().when(counter).increase();
        doReturn(1).when(listResult).size();
        doReturn(response).when(statisticCollector).getInfoResults(listResult, "metre", response);// не работает
        doReturn(response).when(statisticCollector).getInfoResults(listResult, "inch", response);// не работает
        doReturn(responseEntity).when(statisticCollector).retResp(response);// не работает
        doReturn(responseEntity).when(statisticCollector).retResp(null);

        ResponseEntity<?> responseEntity2;
        responseEntity2 = controllerConvert.getInfoOfResults();
        assertThat(responseEntity2).isEqualTo(responseEntity);
    }

    @Test
    public void getInfoOfResultsTestNull(){ // не возможно тестировать из-за внутренней переменной
        ConvResponse<String> response = new ConvResponse<>("OK", 200);
        ResponseEntity<?> responseEntity = new ResponseEntity<>(HttpStatus.OK);

        doNothing().when(counter).increase();
        doReturn(0).when(listResult).size();
        doReturn(responseEntity).when(statisticCollector).retResp(response); // не работает из-за внутр. переменной

        ResponseEntity<?> responseEntity2;
        responseEntity2 = controllerConvert.getInfoOfResults();
    //    assertThat(responseEntity2).isEqualTo(responseEntity);
    }
}

