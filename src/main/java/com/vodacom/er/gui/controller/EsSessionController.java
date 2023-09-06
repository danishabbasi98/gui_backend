package com.vodacom.er.gui.controller;

import com.vodacom.er.gui.entity.Sessions;
import com.vodacom.er.gui.interceptor.LoggingInterceptor;
import com.vodacom.er.gui.repository.EsSessionsRepository;
import com.vodacom.er.gui.service.EsSessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

//@CrossOrigin(origins = "*")
@RestController
public class EsSessionController {

  @Autowired
  private EsSessionsRepository esSessionsRepository;

  @Autowired
  private EsSessionService esSessionService;

  private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

  @GetMapping("/session/{id}")
  public Optional<Sessions> getEsSessionById(@PathVariable("id") Integer id) {
    logger.info("EsSessionController : getEsSessionById() Method Called :");
    if(!esSessionsRepository.findById(id).isPresent()){
      logger.info("EsSessionController : getEsSessionById() Method Called :Invalid Session ID.");
      return  null;
    }
    logger.info("EsSessionController : getEsSessionById() Method Called : Successfully");
    return esSessionService.getSessionById(id);
  }

  @GetMapping("/session/all")
  public List<Sessions> allEsSession() {
    logger.info("EsSessionController : allEsSession() Method Called :");
    return esSessionService.allSession();
  }

  @PostMapping("/session/create")
  Sessions createEsUser(@RequestBody Sessions _sessions) {
    logger.info("EsSessionController : createEsUser() Method Called :");
    if(_sessions.getSeq() == 0){
      logger.info("EsSessionController : createEsUser() Method Called : Please create proper request");
      return null;
    }
    Optional<Sessions> stu = esSessionsRepository.findById(_sessions.getSeq());
    if(stu.isPresent()) {
      logger.info("EsSessionController : createEsUser() Method Called : Already User Exit");
      return null;
    }
    else {
      logger.info("EsSessionController : createEsUser() Method Called : Successfully");
      return esSessionService.saveSession(_sessions);
    }
  }


  @PutMapping("/session/update/{id}")
  Object updateEsSession(@RequestBody Sessions _sessions, @PathVariable("id") Integer id) {
    logger.info("EsSessionController : updateEsSession() Method Called : ");
    Optional<Sessions> stu = esSessionsRepository.findById(id);
    if(stu.isPresent()){
      logger.info("EsSessionController : updateEsSession() Method Called : Successfully");
      Optional<Sessions> s = esSessionService.updateEsSession(_sessions);
      return s;
    }
    else {
      logger.info("EsSessionController : updateEsSession() Method Called : Invalid Id ");
      return null;
    }

  }

  @DeleteMapping("/session/delete/{id}")
  void deleteEsSessionById(@PathVariable Integer id) {
    logger.info("EsSessionController : deleteEsSessionById() Method Called : ");
    if(!esSessionsRepository.findById(id).isPresent()){
      logger.info("EsSessionController : deleteEsSessionById() Method Called :Invalid ID. ");
    }
    logger.info("EsSessionController : deleteEsSessionById() Method Called : Successfully ");
    esSessionService.deleteEsSessionById(id);
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
