package com.vodacom.er.gui.controller;

import com.vodacom.er.gui.entity.Sub_Functions;
import com.vodacom.er.gui.interceptor.LoggingInterceptor;
import com.vodacom.er.gui.repository.EsSubFunctionsRepository;
import com.vodacom.er.gui.service.EsSubFunctionsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

//@CrossOrigin(origins = "*")
@RestController
public class EsSubFunctionController {

  @Autowired
  private EsSubFunctionsRepository esSubFunctionsRepository;

  @Autowired
  private EsSubFunctionsService esSubFunctionsService;

  private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

  @GetMapping("/sub/{id}")
  public Optional<Sub_Functions> getEsSessionById(@PathVariable("id") Integer id) {
    logger.info("EsSubFunctionController : getEsSessionById() Method Called :  ");
    if(!esSubFunctionsRepository.findById(id).isPresent()){
      logger.info("EsSubFunctionController : getEsSessionById() Method Called : No record found");
      return null;
    }
    logger.info("EsSubFunctionController : getEsSessionById() Method Called : Successfully ");
    return esSubFunctionsService.getSubFunctionById(id);
  }

  @GetMapping("/sub/all")
  public List<Sub_Functions> allEsSubFunction() {
    logger.info("EsSubFunctionController : allEsSubFunction() Method Called : Successfully ");
    return esSubFunctionsService.allSubFunction();
  }

  @PostMapping("/sub/create")
  Sub_Functions createEsSubFunction(@RequestBody Sub_Functions _sub_functions) {
    logger.info("EsSubFunctionController : createEsSubFunction() Method Called : ");
    if(_sub_functions.getSeq() == 0){
      logger.info("EsSubFunctionController : createEsSubFunction() Method Called : Please create proper request ");
      return null;
    }
    Optional<Sub_Functions> stu = esSubFunctionsRepository.findById(_sub_functions.getSeq());
    if(stu.isPresent()) {
      logger.info("EsSubFunctionController : createEsSubFunction() Method Called : Already User Exit");
      return null;
    }
    else {
      logger.info("EsSubFunctionController : createEsSubFunction() Method Called : Successfully");
      return esSubFunctionsService.saveSubFunction(_sub_functions);
    }
  }

  @PutMapping("/sub/update/{id}")
  Object updateEsSubFunction(@RequestBody Sub_Functions _sub_functions, @PathVariable("id") Integer id) {
    logger.info("EsSubFunctionController : updateEsSubFunction() Method Called : ");
    Optional<Sub_Functions> stu = esSubFunctionsRepository.findById(id);
    if(stu.isPresent()){
      logger.info("EsSubFunctionController : updateEsSubFunction() Method Called : Successfully");
      Optional<Sub_Functions> s = esSubFunctionsService.updateEsSubFunction(_sub_functions);
      return s;
    }
    else {
      logger.info("EsSubFunctionController : updateEsSubFunction() Method Called : Invalid Id");
      return null;
    }

  }

  @DeleteMapping("/sub/delete/{id}")
  void deleteEsSubFunctionById(@PathVariable Integer id) {
    logger.info("EsSubFunctionController : deleteEsSubFunctionById() Method Called :");
    System.out.print("Delete called.");
    if(!esSubFunctionsRepository.findById(id).isPresent()){
      logger.info("EsSubFunctionController : deleteEsSubFunctionById() Method Called : Invalid Id");
    }
    esSubFunctionsService.deleteEsSubFunctionById(id);
    logger.info("EsSubFunctionController : deleteEsSubFunctionById() Method Called : Successfully");
  }

  public static ResponseEntity<Object> generateResponse(HttpStatus status, boolean error, String message, String token) {
    Map<String, Object> map = new HashMap<String, Object>();
    try {
      Properties properties = new Properties();
      properties.put("JsonWebToken","");
      map.put("timestamp", new Date());
      map.put("token", token);
      map.put("data", properties);

      return new ResponseEntity<Object>(map,status);
    } catch (Exception e) {
      map.clear();
      Properties properties = new Properties();
      properties.put("JsonWebToken","");
      map.put("timestamp", new Date());
      map.put("token", token);
      map.put("data", properties);
      return new ResponseEntity<Object>(map,status);
    }
  }
}
