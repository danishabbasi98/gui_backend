package com.vodacom.er.gui.service;

import com.vodacom.er.gui.actionmessage.FunctionActionsService;
import com.vodacom.er.gui.actionmessage.GroupActionsService;
import com.vodacom.er.gui.actionmessage.SubscriptionActionsService;
import com.vodacom.er.gui.actions.FunctionActions;
import com.vodacom.er.gui.actions.GroupActions;
import com.vodacom.er.gui.actions.NormalUserActions;
import com.vodacom.er.gui.actions.SubscriptionActions;
import com.vodacom.er.gui.actions.UserActions;
import com.vodacom.er.gui.actionmessage.UserActionsService;
import com.vodacom.er.gui.entity.Session_Actions;
import com.vodacom.er.gui.entity.Sessions;
import com.vodacom.er.gui.entity.Users;
import com.vodacom.er.gui.interceptor.LoggingInterceptor;
import com.vodacom.er.gui.repository.EsSessionActionsRepository;
import com.vodacom.er.gui.repository.EsUserRepository;
import com.vodacom.er.gui.util.TokenReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class EsSessionActionService {
    @Autowired
    private EsSessionActionsRepository esSessionActionsRepository;

    @Autowired
    private EsUserRepository esUserRepository;

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    Date currentDate = new Date();

    public Session_Actions saveSessionAction(Session_Actions _session_actions) {
        logger.info("EsSessionActionService : saveSessionAction() Method .Successfully");
        return esSessionActionsRepository.save(_session_actions);
    }

    public List<Session_Actions> allSessionAction() {
        logger.info("EsSessionActionService : allSessionAction() Method .Successfully");
        return esSessionActionsRepository.findAll();
    }

    public Optional<Session_Actions> getSessionActionById(Integer id) {
        logger.info("EsSessionActionService : getSessionActionById() Method .");
        if (!esSessionActionsRepository.findById(id).isPresent()) {
            logger.info("EsSessionActionService : getSessionActionById() Method .No record found");
            return null;
        }
        logger.info("EsSessionActionService : getSessionActionById() Method .Successfully");
        return esSessionActionsRepository.findById(id);
    }

    public Optional<Session_Actions> getActualSessionActionById(Integer id) {
        logger.info("EsSessionActionService : getActualSessionActionById() Method .Successfully");
        return esSessionActionsRepository.findById(id);
    }

    public void addLogoutAction(UserActions tasks, UserActionsService userActionsService, Users _users, String uuid, Sessions _sessions) {
        logger.info("EsSessionActionService : addLogoutAction() Method .");
        Session_Actions _session_actions = new Session_Actions();
        _session_actions.setSession_seq(_sessions.getSession_id());
        _session_actions.setAction_id(tasks.getAction());
        _session_actions.setAction_details(userActionsService.performAction(tasks));
        _session_actions.setUser_id(_users.getUser_seq());
        _session_actions.setModified_date(currentDate);
        _session_actions.setStart_date(currentDate);
        _session_actions.setFailure_message("");
        _session_actions.setStatus("true");
        Session_Actions _session_actions1 = esSessionActionsRepository.save(_session_actions);
        logger.info("EsSessionActionService : addLogoutAction() Method .Successfully");
    }

    public void addLoginAction(UserActions tasks, UserActionsService userActionsService, Users _users, Sessions _sessions) {
        logger.info("EsSessionActionService : addLoginAction() Method .");
        Session_Actions _session_actions = new Session_Actions();
        _session_actions.setSession_seq(_sessions.getSession_id());
        _session_actions.setAction_id(tasks.getAction());
        _session_actions.setAction_details(userActionsService.performAction(tasks));
        _session_actions.setUser_id(_users.getUser_seq());
        _session_actions.setModified_date(currentDate);
        _session_actions.setStart_date(currentDate);
        _session_actions.setFailure_message("");
        _session_actions.setStatus("true");
        Session_Actions _session_actions1 = esSessionActionsRepository.save(_session_actions);
        logger.info("EsSessionActionService : addLoginAction() Method .Successfully");
    }

    public void addActions(UserActions tasks, UserActionsService userActionsService, String token) {
        logger.info("EsSessionActionService : addActions() Method .");
        TokenReader tokenReader = new TokenReader();
        UUID uuid = tokenReader.extractUUIDKeyFromToken(token);
        String name = tokenReader.extractUserNameFromToken(token);
        Users _users = esUserRepository.findByUserName(name);

        Session_Actions _session_actions = new Session_Actions();
        _session_actions.setSession_seq(uuid.toString());
        _session_actions.setAction_id(tasks.getAction());
        _session_actions.setAction_details(userActionsService.performAction(tasks));
        _session_actions.setUser_id(_users.getUser_seq());
        _session_actions.setModified_date(currentDate);
        _session_actions.setStart_date(currentDate);
        _session_actions.setFailure_message("");
        _session_actions.setStatus("true");
        Session_Actions _session_actions1 = esSessionActionsRepository.save(_session_actions);
        logger.info("EsSessionActionService : addActions() End Method Successfully.");
    }

    public Session_Actions addActionsWithNormalUser(NormalUserActions tasks, String normalUserActionsService, String token) {
        logger.info("EsSessionActionService : addActionsWithNormalUser() Method.");
        TokenReader tokenReader = new TokenReader();
        UUID uuid = tokenReader.extractUUIDKeyFromToken(token);
        String name = tokenReader.extractUserNameFromToken(token);
        Users _users = esUserRepository.findByUserName(name);
        Session_Actions _session_actions = new Session_Actions();
        _session_actions.setSession_seq(uuid.toString());
        _session_actions.setAction_id(tasks.getAction());
        _session_actions.setAction_details(normalUserActionsService);
        _session_actions.setUser_id(_users.getUser_seq());
        _session_actions.setModified_date(currentDate);
        _session_actions.setStart_date(currentDate);
        _session_actions.setFailure_message("");
        _session_actions.setStatus("true");
        Session_Actions _session_actions1 = esSessionActionsRepository.save(_session_actions);
        logger.info("EsSessionActionService : addActionsWithNormalUser() Method. Successfully");
        return _session_actions1;
    }
    public void addSessionActions(SubscriptionActions tasks, SubscriptionActionsService subscriptionActionsService, String token) {

        logger.info("EsSessionActionService : addSessionActions() Method. ");
        TokenReader tokenReader = new TokenReader();
        UUID uuid = tokenReader.extractUUIDKeyFromToken(token);
        String name = tokenReader.extractUserNameFromToken(token);
        Users _users = esUserRepository.findByUserName(name);

        Session_Actions _session_actions = new Session_Actions();
        _session_actions.setSession_seq(uuid.toString());
        _session_actions.setAction_id(tasks.getAction());
        _session_actions.setAction_details(subscriptionActionsService.performAction(tasks));
        _session_actions.setUser_id(_users.getUser_seq());
        _session_actions.setModified_date(currentDate);
        _session_actions.setStart_date(currentDate);
        _session_actions.setFailure_message("");
        _session_actions.setStatus("true");
        Session_Actions _session_actions1 = esSessionActionsRepository.save(_session_actions);
        logger.info("EsSessionActionService : addSessionActions() Method. Successfully");
    }

    public void addGroupActions(GroupActions tasks, GroupActionsService groupActionsService, String token) {

        logger.info("EsSessionActionService : addGroupActions() Method.");
        TokenReader tokenReader = new TokenReader();
        UUID uuid = tokenReader.extractUUIDKeyFromToken(token);
        String name = tokenReader.extractUserNameFromToken(token);
        Users _users = esUserRepository.findByUserName(name);

        Session_Actions _session_actions = new Session_Actions();
        _session_actions.setSession_seq(uuid.toString());
        _session_actions.setAction_id(tasks.getAction());
        _session_actions.setAction_details(groupActionsService.performAction(tasks));
        _session_actions.setUser_id(_users.getUser_seq());
        _session_actions.setModified_date(currentDate);
        _session_actions.setStart_date(currentDate);
        _session_actions.setFailure_message("");
        _session_actions.setStatus("true");
        Session_Actions _session_actions1 = esSessionActionsRepository.save(_session_actions);

        logger.info("EsSessionActionService : addGroupActions() Method. Successfully");
    }

    public void addFunctionActions(FunctionActions tasks, FunctionActionsService functionActionsService, String token) {

        logger.info("EsSessionActionService : addFunctionActions() Method.");
        TokenReader tokenReader = new TokenReader();
        UUID uuid = tokenReader.extractUUIDKeyFromToken(token);
        String name = tokenReader.extractUserNameFromToken(token);
        Users _users = esUserRepository.findByUserName(name);

        Session_Actions _session_actions = new Session_Actions();
        _session_actions.setSession_seq(uuid.toString());
        _session_actions.setAction_id(tasks.getAction());
        _session_actions.setAction_details(functionActionsService.performAction(tasks));
        _session_actions.setUser_id(_users.getUser_seq());
        _session_actions.setModified_date(currentDate);
        _session_actions.setStart_date(currentDate);
        _session_actions.setFailure_message("");
        _session_actions.setStatus("true");
        Session_Actions _session_actions1 = esSessionActionsRepository.save(_session_actions);
        logger.info("EsSessionActionService : addFunctionActions() Method. Successfully");
    }

    public Optional<Session_Actions> updateEsSessionAction(Session_Actions _session_actions) {
        logger.info("EsSessionActionService : updateEsSessionAction() Method. ");
        Optional<Session_Actions> stu = esSessionActionsRepository.findById(_session_actions.getSeq());
        Session_Actions s = stu.get();
        s.setSeq(_session_actions.getSeq());
        s.setSession_seq(_session_actions.getSession_seq());
        s.setAction_id(_session_actions.getAction_id());
        s.setAction_details(_session_actions.getAction_details());
        s.setUser_id(_session_actions.getUser_id());
        s.setStart_date(_session_actions.getStart_date());
        s.setEnd_date(_session_actions.getEnd_date());
        s.setStatus(_session_actions.getStatus());
        s.setFailure_message(_session_actions.getFailure_message());
        s.setModified_date(_session_actions.getModified_date());
//        s.setModified_by(_session_actions.getModified_by());
        esSessionActionsRepository.save(s);
        logger.info("EsSessionActionService : updateEsSessionAction() Method. Successfully");
        return esSessionActionsRepository.findById(_session_actions.getSeq());
    }

    public void deleteEsSessionActionById(Integer id) {
        logger.info("EsSessionActionService : deleteEsSessionActionById() Method.");
        esSessionActionsRepository.deleteById(id);
    }

}
