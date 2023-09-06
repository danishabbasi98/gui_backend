package com.vodacom.er.gui.service;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import com.vodacom.er.gui.entity.Groups;
import com.vodacom.er.gui.entity.User_Groups;
import com.vodacom.er.gui.entity.Users;
import com.vodacom.er.gui.interceptor.LoggingInterceptor;
import com.vodacom.er.gui.mapper.UpdateRequestEsUserGroupList;
import com.vodacom.er.gui.mapper.RequestEsUserGroup;
import com.vodacom.er.gui.repository.EsGroupRepository;
import com.vodacom.er.gui.repository.EsUserGroupRepository;
import com.vodacom.er.gui.repository.EsUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

@Service
public class EsUserService {
    @Autowired
    private EsUserRepository esUserRepository;

    @Autowired
    private EsGroupRepository esGroupRepository;

    @Autowired
    private EsUserGroupRepository esUserGroupRepository;

    Date currentDate = new Date();

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    public Users saveUser(Users user) {
        logger.info("EsUserService : saveUser() Method Called : Successfully ");
        return esUserRepository.save(user);
    }

    public List<Users> allUser() {

        logger.info("EsUserService : allUser() Method Called : Successfully ");
        return esUserRepository.findAll();
    }

    public Optional<Users> getUserById(Integer id) {
        logger.info("EsUserService : getUserById() Method Called : Successfully ");
        return esUserRepository.findById(id);
    }

    public Users findByUserName(String name) {
        logger.info("EsUserService : findByUserName() Method Called : Successfully ");
        return esUserRepository.findByUserName(name);
    }

    public Optional<Users> getActualUserById(Integer id) {
        logger.info("EsUserService : getActualUserById() Method Called : Successfully ");
        return esUserRepository.findById(id);
    }

    public Users addEsUser(Users _users) {

        logger.info("EsUserService : addEsUser() Method Called : Successfully ");
        return esUserRepository.save(_users);
    }

    public Users createEsUserGroupForList(UpdateRequestEsUserGroupList es_groups) {
        logger.info("EsUserService : createEsUserGroupForList() Method Called :  ");
        Users s = new Users();
        s.setUser_name(es_groups.getUser_name());
        s.setRole("normal");
        s.setEmail_address(es_groups.getEmail_address());
        s.setCreation_date(currentDate);
        s.setModified_date(currentDate);
        Users _users = esUserRepository.save(s);
        logger.info("EsUserService : createEsUserGroupForList() Method Called : Successfully  ");
        return esUserRepository.findByUserName(_users.getUser_name());
    }

    public Users createAdminEsUserGroupForList(UpdateRequestEsUserGroupList es_groups) {
        logger.info("EsUserService : createAdminEsUserGroupForList() Method Called :   ");
        Users s = new Users();
        s.setUser_name(es_groups.getUser_name());
        s.setRole("admin");
        s.setEmail_address(es_groups.getEmail_address());
        s.setCreation_date(currentDate);
        s.setModified_date(currentDate);
        Users _users = esUserRepository.save(s);
        logger.info("EsUserService : createAdminEsUserGroupForList() Method Called : Successfully  ");
        return esUserRepository.findByUserName(_users.getUser_name());
    }

    public Users createEsUserGroupList(UpdateRequestEsUserGroupList es_groups) {
        logger.info("EsUserService : createEsUserGroupList() Method Called :   ");
        List<Groups> findGroup = es_groups.getEs_groupsList();
        Users findUser = esUserRepository.findByUserName(es_groups.getUser_name());

        if (findUser != null) {
            logger.info("EsUserService : createEsUserGroupList() Method Called : Already User Exist  ");
            return null;
        }
        if (findGroup != null) {

            Users _users1 = createEsUserGroupForList(es_groups);

            for (int i = 0; i < findGroup.size(); i++) {
                User_Groups s = new User_Groups();
//                for function valid check
                Optional<Groups> esGroups = esGroupRepository.findById(findGroup.get(i).getGroup_seq());
                if (esGroups.isPresent()) {
                    s.setGroup_seq(esGroups.get().getGroup_seq());
                    s.setUser_seq(_users1.getUser_seq());
                    s.setModified_date(_users1.getModified_date());
//                    s.setModified_by(_users1.getModified_by());
                    esUserGroupRepository.save(s);
                }else {
                    continue;
                }
            }
            logger.info("EsUserService : createEsUserGroupList() Method Called : Successfully  ");
            return _users1;
        } else {
            logger.info("EsUserService : createEsUserGroupList() Method Called : No record created  ");
            return null;
        }
    }


    public Users createAdminEsUserGroupList(UpdateRequestEsUserGroupList es_groups) {
        logger.info("EsUserService : createAdminEsUserGroupList() Method Called :   ");
        List<Groups> findGroup = es_groups.getEs_groupsList();
        Users findUser = esUserRepository.findByUserName(es_groups.getUser_name());

        if (findUser != null) {
            logger.info("EsUserService : createAdminEsUserGroupList() Method Called : User Already Exist  ");
            return null;
        }
        if (findGroup != null) {

            Users _users1 = createAdminEsUserGroupForList(es_groups);

            for (int i = 0; i < findGroup.size(); i++) {
                User_Groups s = new User_Groups();
                //                for function valid check
                Optional<Groups> esGroups = esGroupRepository.findById(findGroup.get(i).getGroup_seq());
                if (esGroups.isPresent()) {
                    s.setGroup_seq(esGroups.get().getGroup_seq());
                    s.setUser_seq(_users1.getUser_seq());
                    s.setModified_date(_users1.getModified_date());
                    //                    s.setModified_by(_users1.getModified_by());
                    esUserGroupRepository.save(s);
                }else {
                    continue;
                }
            }
            logger.info("EsUserService : createAdminEsUserGroupList() Method Called : Successfully  ");
            return _users1;
        } else {
            logger.info("EsUserService : createAdminEsUserGroupList() Method Called : No record created  ");
            return null;
        }
    }
    public Users updateUser(String name) {
        logger.info("EsUserService : updateUser() Method Called : Successfully  ");
        return esUserRepository.findByUserName(name);
    }

    public Optional<Users> updateEsUser(Users _users) {
        logger.info("EsUserService : updateEsUser() Method Called :   ");
        Optional<Users> stu = esUserRepository.findById(_users.getSeq());
        Users s = stu.get();
        s.setUser_name(_users.getUser_name());
        s.setCreation_date(_users.getCreation_date());
        s.setLast_login_date(_users.getLast_login_date());
        s.setNo_of_attempts(_users.getNo_of_attempts());
        s.setLocked_yn(_users.getLocked_yn());
        s.setFirst_name(_users.getFirst_name());
        s.setLast_name(_users.getLast_name());
        s.setContact_number(_users.getContact_number());
        s.setRole(_users.getRole());
        s.setEmail_address(_users.getEmail_address());
        s.setActive_yn(_users.getActive_yn());
        s.setModified_date(_users.getModified_date());
        s.setModified_by(_users.getModified_by());
        esUserRepository.save(s);
        logger.info("EsUserService : updateEsUser() Method Called : Successfully  ");
        return esUserRepository.findById(_users.getSeq());
    }

    public Groups requestToActualGroupConvert(Optional es_group) {
        logger.info("EsUserService : requestToActualGroupConvert() Method Called :   ");
        Groups s = new Groups();
        Groups _groups = (Groups) es_group.get();
        s.setSeq(_groups.getSeq());
//      s.setEs_group_functions(_groups.getEs_group_functions());
        s.setName(_groups.getName());
        s.setDescription(_groups.getDescription());
        s.setActive_yn(_groups.getActive_yn());
        s.setModified_date(_groups.getModified_date());
        s.setModified_by(_groups.getModified_by());
//        esGroupRepository.save(s);
        logger.info("EsUserService : requestToActualGroupConvert() Method Called : Successfully  ");
        return s;
    }

    public Users createEsUserGroup(RequestEsUserGroup es_users) {
        logger.info("EsUserService : createEsUserGroup() Method Called :   ");
        Users s = new Users();
        Optional<Groups> es_groups = esGroupRepository.findById(es_users.getGroup_seq());
//        if(es_groups.isPresent()){
//            Groups es_groups1 = requestToActualGroupConvert(es_groups);
////            Set<Users> setUser = new HashSet<>();
////            setUser.add()
////            es_groups1.setEs_user_groups(setUser);
//            Set<Groups> setGroup = new HashSet<>();
//            setGroup.add(es_groups1);
//            s.setGroups(setGroup);
//        }
        s.setUser_name(es_users.getUser_name());
        s.setCreation_date(es_users.getCreation_date());
        s.setLast_login_date(es_users.getLast_login_date());
        s.setNo_of_attempts(es_users.getNo_of_attempts());
        s.setLocked_yn(es_users.getLocked_yn());
        s.setFirst_name(es_users.getFirst_name());
        s.setLast_name(es_users.getLast_name());
        s.setContact_number(es_users.getContact_number());
        s.setRole(es_users.getRole());
        s.setEmail_address(es_users.getEmail_address());
        s.setActive_yn(es_users.getActive_yn());
        s.setModified_date(es_users.getModified_date());
        s.setModified_by(es_users.getModified_by());
        Users saveUser = esUserRepository.save(s);
        if (saveUser.getSeq() != 0) {
            Groups _groups1 = requestToActualGroupConvert(es_groups);
            Set<Users> setUser = new HashSet<>();
            setUser.add(saveUser);
            _groups1.setEs_user_groups(setUser);
            Set<Groups> setGroup = new HashSet<>();
            setGroup.add(_groups1);
            s.setGroups(setGroup);
            Users _users1 = esUserRepository.save(s);
            logger.info("EsUserService : createEsUserGroup() Method Called : Successfully  ");
            return _users1;
        }
        logger.info("EsUserService : createEsUserGroup() Method Called : No record created  ");
        return null;
    }

    public Users createListEsUserGroup(Users _users) {
        logger.info("EsUserService : createListEsUserGroup() Method Called :  ");
        Users s = new Users();

        s.setUser_name(_users.getUser_name());
        s.setCreation_date(_users.getCreation_date());
        s.setLast_login_date(_users.getLast_login_date());
        s.setNo_of_attempts(_users.getNo_of_attempts());
        s.setLocked_yn(_users.getLocked_yn());
        s.setFirst_name(_users.getFirst_name());
        s.setLast_name(_users.getLast_name());
        s.setContact_number(_users.getContact_number());
//        s.setRole(_users.getRole());
        s.setRole("normal");
        s.setEmail_address(_users.getEmail_address());
        s.setActive_yn(_users.getActive_yn());
        s.setModified_date(_users.getModified_date());
        s.setModified_by(_users.getModified_by());
        Users saveUser = esUserRepository.save(s);
        if (saveUser.getSeq() != 0) {
//            Groups es_groups1 = requestToActualGroupConvert(es_groups);
            Set<Users> setUser = new HashSet<>();
            setUser.add(saveUser);
//            es_groups1.setEs_user_groups(setUser);
            Set<Groups> setGroup = new HashSet<>();
//            setGroup.add(es_groups1);
            s.setGroups(setGroup);
            Users _users1 = esUserRepository.save(s);
            logger.info("EsUserService : createListEsUserGroup() Method Called : Successfully ");
            return _users1;
        }
        logger.info("EsUserService : createListEsUserGroup() Method Called : No record created ");
        return null;
    }

    public boolean validateEsUser(Users _users) {

        // mandatory fields
        if (_users.getUser_name() != "" && _users.getActive_yn() != "" && _users.getRole() != "") {
            return true;
        } else {
            return false;
        }
    }

    public void deleteEsUserById(Integer id) {
        esUserRepository.deleteById(id);
    }

    public List<Users> readCsvFormat(String csv) {
        logger.info("EsUserService : readCsvFormat() Method Called :  ");
        String csvString = csv;
        StringReader reader = new StringReader(csvString);

        List<Users> _usersList = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(reader)) {
            List<String[]> csvData = csvReader.readAll();

            for (String[] row : csvData) {
                for (String cell : row) {
                    Users _users = new Users();
                    Users _users1 = esUserRepository.findByUserName(cell);
                    if (_users1 != null) {
                        continue;
                    } else {
                        _users.setUser_name(cell);
                        _usersList.add(_users);
                    }
                }
            }
            logger.info("EsUserService : readCsvFormat() Method Called : Successfully ");
            return _usersList;
        } catch (IOException | CsvException e) {
            // Handle any exceptions
            logger.error("EsUserService : readCsvFormat() Method Called : CsvException : "+e);
            e.printStackTrace();
            return null;
        }
    }
}
