package com.vodacom.er.gui.service;


import com.vodacom.er.gui.entity.Groups;
import com.vodacom.er.gui.entity.User_Groups;
import com.vodacom.er.gui.entity.Users;
import com.vodacom.er.gui.interceptor.LoggingInterceptor;
import com.vodacom.er.gui.mapper.RequestSearchEsUser;
import com.vodacom.er.gui.mapper.UpdateRequestEsUserGroup;
import com.vodacom.er.gui.mapper.UpdateRequestEsUserGroupList;
import com.vodacom.er.gui.mapper.RequestEsUserGroup;
import com.vodacom.er.gui.repository.EsGroupRepository;
import com.vodacom.er.gui.repository.EsUserGroupRepository;
import com.vodacom.er.gui.repository.EsUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class EsUserGroupService {
    @Autowired
    private EsUserGroupRepository esUserGroupRepository;

    @Autowired
    private EsGroupRepository esGroupRepository;


    @Autowired
    private EsUserService esUserService;

    @Autowired
    private EsUserRepository esUserRepository;

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    Date currentDate = new Date();

    public User_Groups saveUserGroup(User_Groups groups) {
        logger.info("EsUserGroupService : saveUserGroup() Method Called : Successfully ");
        return esUserGroupRepository.save(groups);
    }

    public List<User_Groups> allUserGroup() {
        logger.info("EsUserGroupService : allUserGroup() Method Called : Successfully ");
        return esUserGroupRepository.findAll();
    }

    public Optional<User_Groups> findUserGroupById(Integer id) {
        logger.info("EsUserGroupService : findUserGroupById() Method Called : Successfully ");
        return esUserGroupRepository.findById(id);
    }


    public Optional<User_Groups> getActualUserGroupById(Integer id) {
        logger.info("EsUserGroupService : getActualUserGroupById() Method Called : Successfully ");
        return esUserGroupRepository.findById(id);
    }


    public List<Groups> getActualUserGroupBySeqId(RequestSearchEsUser requestSearchEsUser, Integer user_id) {

        logger.info("EsUserGroupService : getActualUserGroupBySeqId() Method Called :  ");
        List<User_Groups> findUserGroupTest = esUserGroupRepository.findAll();
        List<User_Groups> findUserGroupAllow = findUserGroupTest.stream()
                .filter(group -> group.getUser_seq() == user_id && group.getGroup_seq() == requestSearchEsUser.getGroup_seq())
                .collect(Collectors.toList());
        List<Groups> allGroups = esGroupRepository.findAll();
//
        List<Groups> allAllowGroupf = new ArrayList<>();
        for (int i = 0; i < findUserGroupAllow.size(); i++) {
//                Groups group = filteredGroups.get(i);
            final int group_seq = findUserGroupAllow.get(i).getGroup_seq();
            List<Groups> allAllowGroup = allGroups.stream()
                    .filter(group -> group.getSeq() == group_seq)
                    .collect(Collectors.toList());
            if (allAllowGroup.size() != 0) {
                allAllowGroupf.addAll(allAllowGroup);
            }
        }
        logger.info("EsUserGroupService : getActualUserGroupBySeqId() Method Called : Successfully ");
        return allAllowGroupf;
    }

    public Optional<User_Groups> updateEsUserGroup(User_Groups es_groups) {
        logger.info("EsUserGroupService : updateEsUserGroup() Method Called :  ");
        Optional<Groups> findGroup = esGroupRepository.findById(es_groups.getGroup_seq());
        if (findGroup.isPresent()) {
            User_Groups s = new User_Groups();
            Optional<User_Groups> es_user_groups = esUserGroupRepository.findById(es_groups.getGroup_seq());
            if (es_user_groups.isPresent()) {
                logger.info("EsUserGroupService : updateEsUserGroup() Method Called : Already define user with this group ");
                return null;
            } else {
                Optional<Users> es_users = esUserService.getUserById(es_groups.getUser_seq());
                if (es_users.isPresent()) {
                    es_user_groups.get().setUser_seq(es_groups.getUser_seq());
                    es_user_groups.get().setGroup_seq(es_groups.getGroup_seq());
                    es_user_groups.get().setModified_date(es_groups.getModified_date());
                    es_user_groups.get().setModified_by(es_groups.getModified_by());
                    logger.info("EsUserGroupService : updateEsUserGroup() Method Called : Successfully ");
                }
                logger.info("EsUserGroupService : updateEsUserGroup() Method Called : Not Update ");
                return null;
            }
        } else {
            logger.info("EsUserGroupService : updateEsUserGroup() Method Called : Not Updated  ");
            return null;
        }
    }


    public Users createEsUserGroup(RequestEsUserGroup es_groups) {
        logger.info("EsUserGroupService : createEsUserGroup() Method Called :  ");
        Optional<Groups> findGroup = esGroupRepository.findById(es_groups.getGroup_seq());
        Users findUser = esUserService.findByUserName(es_groups.getUser_name());

        if (findUser != null) {
            logger.info("EsUserGroupService : createEsUserGroup() Method Called : User already exist ");
            return null;
        }
        if (findGroup.isPresent()) {
            User_Groups s = new User_Groups();
            Users _users = esUserService.createEsUserGroup(es_groups);

//            s.setUser_seq(_users.getSeq());
            s.setUser_seq(_users.getUser_seq());
            s.setGroup_seq(es_groups.getGroup_seq());
            s.setModified_date(es_groups.getModified_date());
            s.setModified_by(es_groups.getModified_by());
            esUserGroupRepository.save(s);
            logger.info("EsUserGroupService : createEsUserGroup() Method Called : Successfully ");
            return _users;
        } else {
            logger.info("EsUserGroupService : createEsUserGroup() Method Called : Not created ");
            return null;
        }
    }


    public List<Users> createListEsUserGroup(List<Users> _usersList, RequestEsUserGroup es_groups) {
        logger.info("EsUserGroupService : createListEsUserGroup() Method Called :  ");
        Optional<Groups> findGroup = esGroupRepository.findById(es_groups.getGroup_seq());
        Users findUser = esUserService.findByUserName(es_groups.getUser_name());
        List<Users> usersList = new ArrayList<>();
        if (findUser != null) {
            logger.info("EsUserGroupService : createListEsUserGroup() Method Called : User already exist ");
            return null;
        }
        if (findGroup.isPresent()) {
            for (int i = 0; i < _usersList.size(); i++) {
                User_Groups group_s = new User_Groups();
                Users _users = _usersList.get(i);

                Users s = new Users();

                s.setUser_name(_users.getUser_name());
                s.setCreation_date(currentDate);
//                s.setLast_login_date(_users.getLast_login_date());
                s.setNo_of_attempts(_users.getNo_of_attempts());
                s.setLocked_yn(_users.getLocked_yn());
                s.setFirst_name(_users.getFirst_name());
                s.setLast_name(_users.getLast_name());
                s.setContact_number(_users.getContact_number());
                s.setRole("normal");
                s.setEmail_address(_users.getEmail_address());
                s.setActive_yn(_users.getActive_yn());
                s.setModified_date(currentDate);
                s.setModified_by("admin");
                Users saveUser = esUserRepository.save(s);
                if (saveUser.getSeq() != 0) {
                    Set<Users> setUser = new HashSet<>();
                    setUser.add(saveUser);
                    Set<Groups> setGroup = new HashSet<>();
                    s.setGroups(setGroup);
                    Users _users1 = esUserRepository.save(s);
                    usersList.add(_users1);
                }

                group_s.setUser_seq(_users.getUser_seq());
                group_s.setGroup_seq(es_groups.getGroup_seq());
                group_s.setModified_date(es_groups.getModified_date());
                group_s.setModified_by(es_groups.getModified_by());
                esUserGroupRepository.save(group_s);

            }
            logger.info("EsUserGroupService : createListEsUserGroup() Method Called : Successfully ");
            return usersList;
        } else {
            logger.info("EsUserGroupService : createListEsUserGroup() Method Called : Not Created ");
            return null;
        }
    }


    public List<Users> createListEsAdminUserGroup(List<Users> _usersList, RequestEsUserGroup es_groups) {
        logger.info("EsUserGroupService : createListEsAdminUserGroup() Method Called :  ");
        Optional<Groups> findGroup = esGroupRepository.findById(es_groups.getGroup_seq());
        Users findUser = esUserService.findByUserName(es_groups.getUser_name());
        List<Users> usersList = new ArrayList<>();
        if (findUser != null) {
            logger.info("EsUserGroupService : createListEsAdminUserGroup() Method Called : User already exist ");
            return null;
        }
        if (findGroup.isPresent()) {

            for (int i = 0; i < _usersList.size(); i++) {
                User_Groups group_s = new User_Groups();
                Users _users = _usersList.get(i);

                Users s = new Users();
                s.setUser_name(_users.getUser_name());
                s.setCreation_date(currentDate);
                s.setLast_login_date(currentDate);
                s.setNo_of_attempts(_users.getNo_of_attempts());
                s.setLocked_yn(_users.getLocked_yn());
                s.setFirst_name(_users.getFirst_name());
                s.setLast_name(_users.getLast_name());
                s.setContact_number(_users.getContact_number());
                s.setRole("admin");
                s.setEmail_address(_users.getEmail_address());
                s.setActive_yn(_users.getActive_yn());
                s.setModified_date(currentDate);
                s.setModified_by("admin");
//                System.out.println("created user : s : "+s );
                Users saveUser = esUserRepository.save(s);
                if (saveUser.getSeq() != 0) {
                    Set<Users> setUser = new HashSet<>();
                    setUser.add(saveUser);
                    Set<Groups> setGroup = new HashSet<>();
                    s.setGroups(setGroup);
                    Users _users1 = esUserRepository.save(s);
                    usersList.add(_users1);
                }

                group_s.setUser_seq(_users.getUser_seq());
                group_s.setGroup_seq(es_groups.getGroup_seq());
                group_s.setModified_date(es_groups.getModified_date());
                group_s.setModified_by(es_groups.getModified_by());
                esUserGroupRepository.save(group_s);

            }
            logger.info("EsUserGroupService : createListEsAdminUserGroup() Method Called : Successfully ");
            return usersList;
        } else {
            logger.info("EsUserGroupService : createListEsAdminUserGroup() Method Called : Not created ");
            return null;
        }
    }

    public Users updateActualEsUserGroup(UpdateRequestEsUserGroup es_groups) {
        logger.info("EsUserGroupService : updateActualEsUserGroup() Method Called :  ");
        Users findUser = esUserService.findByUserName(es_groups.getUser_name());
        List<User_Groups> findUserGroupTest = esUserGroupRepository.findAll();

        List<User_Groups> filteredUsers = findUserGroupTest.stream()
                .filter(user -> user.getUser_seq() == es_groups.getSeq())
                .collect(Collectors.toList());
        User_Groups esUserGroups = new User_Groups();
        for (int i = 0; i < filteredUsers.size(); i++) {
            if (filteredUsers.get(i).getGroup_seq() == es_groups.getGroup_seq()) {
                esUserGroups = filteredUsers.get(i);
            }
        }
        if (esUserGroups.getGroup_seq() != 0) {
            findUser.setUser_name(es_groups.getUser_name());
            findUser.setCreation_date(es_groups.getCreation_date());
            findUser.setLast_login_date(es_groups.getLast_login_date());
            findUser.setNo_of_attempts(es_groups.getNo_of_attempts());
            findUser.setLocked_yn(es_groups.getLocked_yn());
            findUser.setFirst_name(es_groups.getFirst_name());
            findUser.setLast_name(es_groups.getLast_name());
            Users saveUser = esUserRepository.save(findUser);
            esUserGroups.setGroup_seq(es_groups.getUpdate_group_seq());
            esUserGroupRepository.save(esUserGroups);
            logger.info("EsUserGroupService : updateActualEsUserGroup() Method Called : Successfully ");
            return findUser;
        } else {
            logger.info("EsUserGroupService : updateActualEsUserGroup() Method Called : Not Updated ");
            return null;
        }
    }


    public List<Groups> updateActualEsUserGroupList(UpdateRequestEsUserGroupList es_groups) {
        logger.info("EsUserGroupService : updateActualEsUserGroupList() Method Called :  ");

        Optional<Users> stu = esUserRepository.findById(es_groups.getSeq());
        List<Groups> _groupsList = es_groups.getEs_groupsList();
        if (stu.isPresent()) {
            stu.get().setUser_name(es_groups.getUser_name());
            esUserRepository.save(stu.get());

            if (_groupsList != null) {
                List<User_Groups> esUserGroupsList = esUserGroupRepository.findAll();
                List<Groups> es_groupAll = esGroupRepository.findAll();
                List<Groups> _groupsListCount = new ArrayList<>();
                for (int i = 0; i < _groupsList.size(); i++) {
                    final int group_seq = _groupsList.get(i).getGroup_seq();
                    final int user_seq = es_groups.getSeq();
                    for (int k = 0; k < esUserGroupsList.size(); k++) {
                        if (group_seq == esUserGroupsList.get(k).getGroup_seq() && esUserGroupsList.get(k).getUser_seq()== user_seq) {
                            _groupsListCount.add(_groupsList.get(i));
                        } else {
                            continue;
                        }
                    }

                }

                List<User_Groups> totalGroupUserValue = esUserGroupRepository.findAll();

                for (int i = 0; i < totalGroupUserValue.size(); i++) {
                    if (totalGroupUserValue.get(i).getUser_seq() == es_groups.getSeq()) {
                        esUserGroupRepository.deleteById(totalGroupUserValue.get(i).getId());
                    }
                }

                List<User_Groups> esUserGroupsTotal = esUserGroupRepository.findAll();
                List<Groups> differentObjects = new ArrayList<>();
                differentObjects.addAll(_groupsList.stream()
                        .filter(obj -> !_groupsList.contains(obj))
                        .collect(Collectors.toList()));

                for (int i = 0; i < _groupsList.size(); i++) {
                    User_Groups _user_groups = new User_Groups();
                    _user_groups.setGroup_seq(_groupsList.get(i).getSeq());
                    _user_groups.setUser_seq(stu.get().getSeq());
                    _user_groups.setModified_date(currentDate);
                    _user_groups.setModified_by(es_groups.getRole());
                    User_Groups _user_groups1 = esUserGroupRepository.save(_user_groups);
                }

                logger.info("EsUserGroupService : updateActualEsUserGroupList() Method Called : Successfully ");
                return _groupsList;
            }
            logger.info("EsUserGroupService : updateActualEsUserGroupList() Method Called : Not updated ");
            return null;
        } else {
            logger.info("EsUserGroupService : updateActualEsUserGroupList() Method Called : Not Updated  ");
            return null;
        }
    }
//    public void  deleteEsUserGroupById(Integer id) {
//        esUserGroupRepository.deleteById(id);
//    }
}
