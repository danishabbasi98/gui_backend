package com.vodacom.er.gui.service;


import com.vodacom.er.gui.actions.UserActions;
import com.vodacom.er.gui.actionmessage.UserActionsService;
import com.vodacom.er.gui.entity.Sessions;
import com.vodacom.er.gui.entity.Users;
import com.vodacom.er.gui.interceptor.LoggingInterceptor;
import com.vodacom.er.gui.repository.EsSessionsRepository;
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
public class EsSessionService {
    @Autowired
    private EsSessionsRepository esSessionsRepository;

    @Autowired
    private EsSessionActionService esSessionActionService;

    @Autowired
    private EsUserRepository esUserRepository;

    private final UserActionsService userActionsService;

    @Autowired
    public EsSessionService(UserActionsService userActionsService) {
        this.userActionsService = userActionsService;
    }

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    Date currentDate = new Date();

    public Sessions saveSession(Sessions sessions) {
        logger.info("EsSessionService : saveSession() Method Called : Successfully ");
        return esSessionsRepository.save(sessions);
    }

    public List<Sessions> allSession() {
        logger.info("EsSessionService : allSession() Method Called : Successfully ");
        return esSessionsRepository.findAll();
    }

    public Optional<Sessions> getSessionById(Integer id) {
        logger.info("EsSessionService : getSessionById() Method Called : Successfully ");
        return esSessionsRepository.findById(id);
    }

    public Optional<Sessions> getActualSessionById(Integer id) {
        logger.info("EsSessionService : getActualSessionById() Method Called : Successfully ");
        return esSessionsRepository.findById(id);
    }

    public void createSessionByUserSeq(Users _users, String uuid) {
        logger.info("EsSessionService : createSessionByUserSeq() Method Called :  ");
        Sessions _sessions = new Sessions();
        UserActions action = UserActions.LOGIN;
//        userActionsService.performAction(action);

        _sessions.setUser_seq(_users.getUser_seq());
        _sessions.setSession_id(uuid);
        _sessions.setStart_date(currentDate);
        _sessions.setActive_yn("y");
        Sessions _sessions1 = esSessionsRepository.save(_sessions);
        esSessionActionService.addLoginAction(action, userActionsService, _users, _sessions);
        logger.info("EsSessionService : createSessionByUserSeq() Method Called : Successfully ");
    }

    public Object logoutSessionByUser( String token) {

        logger.info("EsSessionService : logoutSessionByUser() Method Called :  ");
        TokenReader tokenReader = new TokenReader();
        UUID uuid = tokenReader.extractUUIDKeyFromToken(token);
        String name = tokenReader.extractUserNameFromToken(token);
        Users _users = esUserRepository.findByUserName(name);

        UserActions action = UserActions.LOGOUT;
        Sessions _sessions1 = esSessionsRepository.findBySessionId(uuid.toString());
        if(_sessions1 != null){
            _sessions1.setEnd_reason(action.getAction());
            _sessions1.setEnd_date(currentDate);
            _sessions1.setActive_yn("n");
            _sessions1.setModified_date(currentDate);
            esSessionActionService.addLogoutAction(action,userActionsService, _users,uuid.toString(), _sessions1);
            logger.info("EsSessionService : logoutSessionByUser() Method Called : Successfully ");
            return _sessions1;
        }
        else{
            logger.info("EsSessionService : logoutSessionByUser() Method Called : No User Found ");
            return null;
        }
    }

    public Optional<Sessions> updateEsSession(Sessions _sessions) {
        logger.info("EsSessionService : updateEsSession() Method Called :  ");
        Optional<Sessions> stu = esSessionsRepository.findById(_sessions.getSeq());
        Sessions s = stu.get();
        s.setSeq(_sessions.getSeq());
        s.setUser_seq(_sessions.getUser_seq());
        s.setSession_id(_sessions.getSession_id());
        s.setStart_date(_sessions.getStart_date());
        s.setEnd_date(_sessions.getEnd_date());
        s.setEnd_reason(_sessions.getEnd_reason());
        s.setActive_yn(_sessions.getActive_yn());
        s.setModified_date(_sessions.getModified_date());
//        s.setModified_by(_sessions.getModified_by());
        esSessionsRepository.save(s);
        logger.info("EsSessionService : updateEsSession() Method Called : Successfully ");
        return esSessionsRepository.findById(_sessions.getSeq());
    }

    public void deleteEsSessionById(Integer id) {
        logger.info("EsSessionService : deleteEsSessionById() Method Called : Successfully ");
        esSessionsRepository.deleteById(id);
    }

}
