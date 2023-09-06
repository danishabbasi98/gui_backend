package com.vodacom.er.gui.controller;


import com.vodacom.er.gui.actionmessage.FunctionActionsService;
import com.vodacom.er.gui.actions.FunctionActions;
import com.vodacom.er.gui.entity.Functions;
import com.vodacom.er.gui.entity.Group_Functions;

import com.vodacom.er.gui.interceptor.LoggingInterceptor;
import com.vodacom.er.gui.service.EsSessionActionService;
import com.vodacom.er.gui.repository.EsFunctionsRepository;
import com.vodacom.er.gui.repository.EsGroupFunctionsRepository;
import com.vodacom.er.gui.service.EsFunctionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "*")
@RestController
public class EsFunctionController {

    @Autowired
    private EsFunctionsRepository esFunctionsRepository;

    @Autowired
    private EsFunctionsService esFunctionsService;

    @Autowired
    private EsGroupFunctionsRepository esGroupFunctionsRepository;

    @Autowired
    private EsSessionActionService esSessionActionService;

    private final FunctionActionsService functionActionsService;

    @Autowired
    public EsFunctionController(FunctionActionsService functionActionsService) {
        this.functionActionsService = functionActionsService;
    }
    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @GetMapping("/function/{id}")
    public Optional<Functions> getEsFunctionById(@RequestHeader("Authorization") String token, @PathVariable("id") Integer id) {
        logger.info("EsFunctionController : getEsFunctionById() Method Called : ");
        FunctionActions action = FunctionActions.FUNCTIONBYID;
        esSessionActionService.addFunctionActions(action, functionActionsService, token);

        if (!esFunctionsRepository.findById(id).isPresent()) {
            logger.info("EsFunctionController : getEsFunctionById() Method Called : Invalid Id ");
            return null;
        }
        logger.info("EsFunctionController : getEsFunctionById() Method Called : Successfully");
        return esFunctionsService.getFunctionById(id);
    }

    @GetMapping("/function/all")
    public List<Functions> allEsFunction(@RequestHeader("Authorization") String token) {
        logger.info("EsFunctionController : allEsFunction() Method Called : ");
        FunctionActions action = FunctionActions.ALLSIMPLEFUNCTIONS;
        esSessionActionService.addFunctionActions(action, functionActionsService, token);
        logger.info("EsFunctionController : allEsFunction() Method Called : Successfully ");
        return esFunctionsService.allFunctions();
    }

    @GetMapping("/function/search/{name}")
    public Functions EsFunctionSearch(@RequestHeader("Authorization") String token, @PathVariable String name) {
        logger.info("EsFunctionController : EsFunctionSearch() Method Called : ");
        FunctionActions action = FunctionActions.SEARCHBYNAME;
        esSessionActionService.addFunctionActions(action, functionActionsService, token);
        Functions _functions = esFunctionsRepository.findByFunctionName(name);
        if (_functions.getSeq() != 0) {
            logger.info("EsFunctionController : EsFunctionSearch() Method Called : Successfully ");
            return _functions;
        } else {
            logger.info("EsFunctionController : EsFunctionSearch() Method Called : No record found ");
            return null;
        }
    }


    @GetMapping("/function/all/{pageNumber}")
    public ResponseEntity<Object> allEsFunction(@RequestHeader("Authorization") String token, @PathVariable Integer pageNumber) {
        logger.info("EsFunctionController : allEsFunction() Method Called : ");
        FunctionActions action = FunctionActions.ALLFUNCTIONWITHPAGINATION;
        esSessionActionService.addFunctionActions(action, functionActionsService, token);
        Integer readCount = pageNumber * 10;
        Integer startLoop = readCount - 10;
        List<Functions> _functions = esFunctionsRepository.findAll();
        if (_functions.size() < startLoop) {
            logger.info("EsFunctionController : allEsFunction() Method Called : Successfully ");
            return EsFunctionController.generateAllFunctionResponseWithCount(HttpStatus.OK, true, "Get All Function Successfully", null, null);
        }
        List<Functions> allAllowSubscription = new ArrayList<>();
        for (int i = startLoop; i < readCount; i++) {
            if (_functions.size() == i) {
                break;
            }
            if (_functions.size() >= startLoop) {
                allAllowSubscription.add(_functions.get(i));
//                    break;
            } else {
                break;
            }
        }
        logger.info("EsFunctionController : allEsFunction() Method Called : Successfully");
        return EsFunctionController.generateAllFunctionResponseWithCount(HttpStatus.OK, true, "Get All Function Successfully", allAllowSubscription, _functions.size());
    }


    @PostMapping("/function/create")
    Functions createEsFunction(@RequestHeader("Authorization") String token, @RequestBody Functions _functions) {
        logger.info("EsFunctionController : createEsFunction() Method Called : ");
        FunctionActions action = FunctionActions.CREATESFUNCTION;
        esSessionActionService.addFunctionActions(action, functionActionsService, token);
//        if (_functions.getSeq() == 0) {
//            logger.info("EsFunctionController : createEsFunction() Method Called : Please create proper request");
//            return null;
//        }
        Optional<Functions> stu = esFunctionsRepository.findById(_functions.getSeq());
        if (stu.isPresent()) {
            logger.info("EsFunctionController : createEsFunction() Method Called : Already User Exit ");
            return null;
        } else {
            logger.info("EsFunctionController : createEsFunction() Method Called : Successfully");
            return esFunctionsService.saveFunction(_functions);
        }
    }


    @PutMapping("/function/update/{id}")
    Object updateEsFunction(@RequestHeader("Authorization") String token, @RequestBody Functions _functions, @PathVariable("id") Integer id) {
        logger.info("EsFunctionController : updateEsFunction() Method Called : ");
        FunctionActions action = FunctionActions.UPDATEFUNCTION;
        esSessionActionService.addFunctionActions(action, functionActionsService, token);

        Optional<Functions> stu = esFunctionsRepository.findById(id);
        if (stu.isPresent()) {
            logger.info("EsFunctionController : updateEsFunction() Method Called : Successfully ");
            Optional<Functions> s = esFunctionsService.updateEsFunction(_functions);
            return s;
        } else {
            logger.info("EsFunctionController : updateEsFunction() Method Called : No update");
            return null;
        }

    }

    @DeleteMapping("/function/delete/{id}")
    Optional<Functions> deleteEsFunctionById(@RequestHeader("Authorization") String token, @PathVariable Integer id) {
        logger.info("EsFunctionController : deleteEsFunctionById() Method Called : ");
        FunctionActions action = FunctionActions.DELETEAFUNCTIONBYID;
        esSessionActionService.addFunctionActions(action, functionActionsService, token);

        Optional<Functions> es_functions = esFunctionsRepository.findById(id);
        if (!es_functions.isPresent()) {
            logger.info("EsFunctionController : deleteEsFunctionById() Method Called : Invalid Id");
            return null;
        }
        List<Group_Functions> findFunctionGroupTest = esGroupFunctionsRepository.findAll();

        List<Group_Functions> filteredFunctions = findFunctionGroupTest.stream()
                .filter(function -> function.getFunction_seq() == id)
                .collect(Collectors.toList());
        for (int i = 0; i < filteredFunctions.size(); i++) {
            esGroupFunctionsRepository.deleteById(filteredFunctions.get(i).getId());
        }
        logger.info("EsFunctionController : deleteEsFunctionById() Method Called : Successfully ");
        esFunctionsRepository.deleteById(id);
        return null;
    }

    public static ResponseEntity<Object> generateResponse(HttpStatus status, boolean error, String message, String token) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Properties properties = new Properties();
            properties.put("JsonWebToken", "");
            map.put("timestamp", new Date());
            map.put("token", token);
            map.put("data", properties);

            return new ResponseEntity<Object>(map, status);
        } catch (Exception e) {
            map.clear();
            Properties properties = new Properties();
            properties.put("JsonWebToken", "");
            map.put("timestamp", new Date());
            map.put("token", token);
            map.put("data", properties);
            return new ResponseEntity<Object>(map, status);
        }
    }

    public static ResponseEntity<Object> generateAllFunctionResponseWithCount(HttpStatus status, boolean error, String message, List<Functions> token, Integer count) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {

            Integer perPage = 10;
            Integer pageValue = 0;
            if (count != null) {
                pageValue = Integer.divideUnsigned(count, perPage);
                if (pageValue == 0) {
                    pageValue = 1;
                }
            }

            Properties properties = new Properties();
            properties.put("message", message);
            properties.put("Function", token);
            map.put("success", true);
            map.put("total_count", count);
            map.put("pages", pageValue);
            map.put("response", properties);

            return new ResponseEntity<Object>(map, status);
        } catch (Exception e) {
            map.clear();
            Properties properties = new Properties();
            properties.put("message", message);
            properties.put("Function", "");
            map.put("success", false);
            map.put("total_count", count);
            map.put("pages", "");
            map.put("response", properties);
            return new ResponseEntity<Object>(map, status);
        }
    }
}
