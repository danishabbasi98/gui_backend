package com.vodacom.er.gui.controller;


import com.vodacom.er.gui.actionmessage.NormalUserActionsService;
import com.vodacom.er.gui.actions.NormalUserActions;
import com.vodacom.er.gui.entity.Functions;
import com.vodacom.er.gui.entity.Session_Actions;
import com.vodacom.er.gui.interceptor.LoggingInterceptor;
import com.vodacom.er.gui.service.EsSessionActionService;
import com.vodacom.er.gui.repository.EsFunctionsRepository;
import com.vodacom.er.gui.repository.EsSessionActionsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

//@CrossOrigin(origins = "*")
@RestController
public class EsSessionActionController {


  @Autowired
  private EsSessionActionsRepository esSessionActionsRepository;

  @Autowired
  private EsSessionActionService esSessionActionService;

  @Autowired
  private EsFunctionsRepository esFunctionsRepository;

  private final NormalUserActionsService normalUserActionsService;

  @Autowired
  public EsSessionActionController(NormalUserActionsService normalUserActionsService) {
    this.normalUserActionsService = normalUserActionsService;
  }

  private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);
  Date currentDate = new Date();

  @GetMapping("/session/action/{id}")
  public Optional<Session_Actions> getEsSessionActionById(@PathVariable("id") Integer id) {
    logger.info("EsSessionActionController : getEsSessionActionById() Method Called : ");
    Optional<Session_Actions> es_session_actions = esSessionActionsRepository.findById(id);
    if (!es_session_actions.isPresent()) {
      logger.info("EsSessionActionController : getEsSessionActionById() Method Called : Invalid Id ");
      return null;
    }
    logger.info("EsSessionActionController : getEsSessionActionById() Method Called : Successfully ");
    return esSessionActionService.getSessionActionById(id);
  }

  @GetMapping("/session/action/all")
  public List<Session_Actions> allEsSessionAction() {
    logger.info("EsSessionActionController : allEsSessionAction() Method Called : ");
    return esSessionActionService.allSessionAction();
  }

  @PostMapping("/session/action/create")
  Session_Actions createEsSessionAction(@RequestBody Session_Actions _session_actions) {
    logger.info("EsSessionActionController : createEsSessionAction() Method Called : ");
    if (_session_actions.getSeq() == 0) {
      logger.info("EsSessionActionController : createEsSessionAction() Method Called : Please create proper request");
      return null;
    }
    Optional<Session_Actions> stu = esSessionActionsRepository.findById(_session_actions.getSeq());
    if (stu.isPresent()) {
      logger.info("EsSessionActionController : createEsSessionAction() Method Called : Already User Exit");
      return null;
    } else {
      logger.info("EsSessionActionController : createEsSessionAction() Method Called : Successfully");
      return esSessionActionService.saveSessionAction(_session_actions);
    }
  }

  @PostMapping("/session/function/create")
  Session_Actions createEsSessionActionWithFunction(@RequestHeader("Authorization") String token, @RequestBody Functions _functions) {
    logger.info("EsSessionActionController : createEsSessionAction() Method Called : ");
    NormalUserActions action = NormalUserActions.SESSIONWITHFUNCTION;
    String Message = normalUserActionsService.performAction(action);
    StringBuilder sb = new StringBuilder();
    sb.append(Message).append(" ").append(_functions.getSeq()+", Name : "+ _functions.getName());
    String concatenatedString = sb.toString();
    Optional<Functions> esFunctions = esFunctionsRepository.findById(_functions.getSeq());
    if(esFunctions.isPresent()){
      Session_Actions _session_actions = esSessionActionService.addActionsWithNormalUser(action, concatenatedString, token);
      System.out.println("Create createEsSessionActionWithFunction() route called: ");
      return _session_actions;
    }
    else{
      System.out.println("Create createEsSessionActionWithFunction() Invalid Function ID: ");
      return null;
    }
  }

  @PutMapping("/session/action/update/{id}")
  Object updateEsSessionAction(@RequestBody Session_Actions _session_actions, @PathVariable("id") Integer id) {
    logger.info("EsSessionActionController : updateEsSessionAction() Method Called : ");
    Optional<Session_Actions> stu = esSessionActionsRepository.findById(id);
    if (stu.isPresent()) {
      Session_Actions session_action = _session_actions;
      session_action.setSeq(stu.get().getSeq());
      Optional<Session_Actions> s = esSessionActionService.updateEsSessionAction(session_action);
      logger.info("EsSessionActionController : updateEsSessionAction() Method Called : Successfully");
      return s;
    } else {
      logger.info("EsSessionActionController : updateEsSessionAction() Method Called :Invalid Id ");
      return null;
    }

  }

  @DeleteMapping("/session/action/delete/{id}")
  void deleteEsSessionActionById(@PathVariable Integer id) {
    logger.info("EsSessionActionController : deleteEsSessionActionById() Method Called : ");
    if (!esSessionActionsRepository.findById(id).isPresent()) {
      logger.info("EsSessionActionController : deleteEsSessionActionById() Method Called : Invalid Id");
    }
    logger.info("EsSessionActionController : deleteEsSessionActionById() Method Called : Successfully");
    esSessionActionService.deleteEsSessionActionById(id);
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
}
