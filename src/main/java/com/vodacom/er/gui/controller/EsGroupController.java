package com.vodacom.er.gui.controller;

import com.vodacom.er.gui.actionmessage.GroupActionsService;
import com.vodacom.er.gui.actions.GroupActions;
import com.vodacom.er.gui.entity.Functions;
import com.vodacom.er.gui.entity.Group_Functions;
import com.vodacom.er.gui.entity.Groups;
import com.vodacom.er.gui.entity.User_Groups;
import com.vodacom.er.gui.interceptor.LoggingInterceptor;
import com.vodacom.er.gui.service.EsFunctionsService;
import com.vodacom.er.gui.service.EsGroupFunctionService;
import com.vodacom.er.gui.service.EsGroupService;
import com.vodacom.er.gui.service.EsSessionActionService;
import com.vodacom.er.gui.mapper.GroupListFunction;
import com.vodacom.er.gui.mapper.RequestEsGroupFunction;
import com.vodacom.er.gui.mapper.UpdateRequestEsGroupFunction;
import com.vodacom.er.gui.repository.EsFunctionsRepository;
import com.vodacom.er.gui.repository.EsGroupFunctionsRepository;
import com.vodacom.er.gui.repository.EsGroupRepository;
import com.vodacom.er.gui.repository.EsUserGroupRepository;
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
public class EsGroupController {

    @Autowired
    private EsGroupService esGroupService;

    @Autowired
    private EsGroupRepository esGroupRepository;

    @Autowired
    private EsGroupFunctionsRepository esGroupFunctionsRepository;

    @Autowired
    private EsFunctionsRepository esFunctionsRepository;

    @Autowired
    private EsUserGroupRepository esUserGroupRepository;

    @Autowired
    private EsGroupFunctionService esGroupFunctionService;

    @Autowired
    private EsFunctionsService esFunctionsService;

    @Autowired
    private EsSessionActionService esSessionActionService;

    private final GroupActionsService groupActionsService;

    @Autowired
    public EsGroupController(GroupActionsService groupActionsService) {
        this.groupActionsService = groupActionsService;
    }

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);
    Date currentDate = new Date();

    @GetMapping("/group/{id}")
    public Optional<Groups> getGroupById(@RequestHeader("Authorization") String token, @PathVariable("id") Integer id) {
        logger.info("EsGroupController : getGroupById() Method Called : ");
        GroupActions action = GroupActions.GROUPBYID;
        esSessionActionService.addGroupActions(action, groupActionsService, token);
        Optional<Groups> es_groups = esGroupService.findGroupById(id);
        if (!es_groups.isPresent()) {
            logger.info("EsGroupController : getGroupById() Method Called : Invalid group id");
            return null;
        }
        logger.info("EsGroupController : getGroupById() Method Called : Successfully");
        return esGroupService.findGroupById(id);
    }

    @GetMapping("/group/all")
    public List<Groups> allGroup(@RequestHeader("Authorization") String token) {

        GroupActions action = GroupActions.ALLSIMPLEGROUPS;
        esSessionActionService.addGroupActions(action, groupActionsService, token);
        logger.info("EsGroupController : allGroup() Method Called : Successfully");
        return esGroupService.allGroup();
    }

    @GetMapping("/group/listFunctions/all")
    public List<GroupListFunction> allGroupWithFunctions(@RequestHeader("Authorization") String token) {
        logger.info("EsGroupController : allGroupWithFunctions() Method Called : ");
        GroupActions action = GroupActions.GROUPWITHLISTFUNCTIONS;
        esSessionActionService.addGroupActions(action, groupActionsService, token);
        List<Groups> _groups = esGroupRepository.findAll();
        List<Functions> _functions = esFunctionsRepository.findAll();
        List<Group_Functions> esGroupFunctionsList = esGroupFunctionsRepository.findAll();
        List<GroupListFunction> allAllowGroupFunction = new ArrayList<>();
        for (int i = 0; i < _groups.size(); i++) {
            GroupListFunction esGroupFunctionResponse = new GroupListFunction();
            esGroupFunctionResponse.setEs_groups(_groups.get(i));
            final int group_seq = _groups.get(i).getGroup_seq();

            List<Group_Functions> allFunction = esGroupFunctionsList.stream()
                    .filter(function -> function.getGroup_seq() == group_seq)
                    .collect(Collectors.toList());
            List<Functions> esFunctionsArrayList = new ArrayList<>();
            for (int j = 0; j < allFunction.size(); j++) {
                final int function_seq = allFunction.get(j).getFunction_seq();
                for (int k = 0; k < _functions.size(); k++) {
                    if (function_seq == _functions.get(k).getSeq()) {
                        esFunctionsArrayList.add(_functions.get(k));
                    }
                }
            }
            esGroupFunctionResponse.setEsFunctions(esFunctionsArrayList);
            allAllowGroupFunction.add(esGroupFunctionResponse);

        }
        logger.info("EsGroupController : allGroupWithFunctions() Method Called : Successfully");
        return allAllowGroupFunction;
    }

    @GetMapping("/group/all/{pageNumber}")
    public ResponseEntity<Object> allGroup(@RequestHeader("Authorization") String token, @PathVariable Integer pageNumber) {
        logger.info("EsGroupController : allGroup() Method Called : ");
        GroupActions action = GroupActions.ALLGROUPWITHPAGINATION;
        esSessionActionService.addGroupActions(action, groupActionsService, token);
        Integer readCount = pageNumber * 10;
        Integer startLoop = readCount - 10;
        List<Groups> _groups = esGroupRepository.findAll();
        if (_groups.size() < startLoop) {
            logger.info("EsGroupController : allGroup() Method Called : Successfully");
            return EsGroupController.generateAllGroupResponseWithCount(HttpStatus.OK, true, "Get All Group Successfully", null, null);
        }
        List<Groups> allAllowSubscription = new ArrayList<>();
        for (int i = startLoop; i < readCount; i++) {
            System.out.println(" i = " + i);
            if (_groups.size() == i) {
                break;
            }
            if (_groups.size() >= startLoop) {
                allAllowSubscription.add(_groups.get(i));
//                    break;
            } else {
                break;
            }
        }
        logger.info("EsGroupController : allGroup() Method Called : Successfully");
        return EsGroupController.generateAllGroupResponseWithCount(HttpStatus.OK, true, "Get All Group Successfully", allAllowSubscription, _groups.size());
    }

    @PostMapping("/group/add")
    public Groups addGroup(@RequestHeader("Authorization") String token, @RequestBody Groups groups) {
        logger.info("EsGroupController : addGroup() Method Called : ");
        GroupActions action = GroupActions.ADDESGROUP;
        esSessionActionService.addGroupActions(action, groupActionsService, token);
        logger.info("EsGroupController : addGroup() Method Called : Successfully");
        return esGroupService.saveGroup(groups);
    }
//  @DeleteMapping("/es/{id}")
//  void deleteEsUserById(@PathVariable Integer id) {
//    System.out.print("Delete called.");
//    esGroupService.deleteEsGroupById(id);
//  }
//

    @PostMapping("/group/create")
    Groups createEsGroup(@RequestHeader("Authorization") String token, @RequestBody Groups _groups) {
        logger.info("EsGroupController : createEsGroup() Method Called : ");
        GroupActions action = GroupActions.CREATESGROUP;
        esSessionActionService.addGroupActions(action, groupActionsService, token);
        if (_groups.getSeq() == 0) {
            logger.info("EsGroupController : createEsGroup() Method Called : Please create proper request");
            return null;
        }
        Optional<Groups> stu = esGroupRepository.findById(_groups.getSeq());
        if (stu.isPresent()) {
            logger.info("EsGroupController : createEsGroup() Method Called : Already User Exist");
            return null;
        } else {
            logger.info("EsGroupController : createEsGroup() Method Called : Successfully");
            return esGroupService.saveGroup(_groups);
        }
    }

    @PostMapping("/group/function/search")
    Object searchEsGroupFunction(@RequestHeader("Authorization") String token, @RequestBody RequestEsGroupFunction requestEsGroupFunction) {
        logger.info("EsGroupController : searchEsGroupFunction() Method Called : ");
        GroupActions action = GroupActions.CREATESGROUPWITHAFUNCTION;
        esSessionActionService.addGroupActions(action, groupActionsService, token);

        Optional<Functions> es_functions = esFunctionsService.getFunctionById(requestEsGroupFunction.getFunction_seq());
        Groups _groups = esGroupService.findByGroupName(requestEsGroupFunction.getName());
        if (_groups == null) {
            logger.info("EsGroupController : searchEsGroupFunction() Method Called :No record found ");
            return EsGroupController.generateSearchResponse(HttpStatus.OK, false, "No record found", null, null);
        }
        if (es_functions.isPresent() && _groups.toString() != null) {
            Optional<Groups> s = Optional.ofNullable(esGroupRepository.findByGroupName(requestEsGroupFunction.getName()));
            if (s.isPresent()) {
                List<Functions> allAllowFunctions = esGroupFunctionService.getActualUserGroupWithFunctionsSeqId(requestEsGroupFunction, s.get().getGroup_seq());
                logger.info("EsGroupController : searchEsGroupFunction() Method Called : Successfully");
                return EsGroupController.generateSearchFunctionResponse(HttpStatus.OK, false, "Success", _groups, allAllowFunctions);

            } else {
                logger.info("EsGroupController : searchEsGroupFunction() Method Called : No group relationship with this user ");
                return EsGroupController.generateSearchResponse(HttpStatus.OK, false, "User not found in this group ", null, null);
            }
        } else if (requestEsGroupFunction.getFunction_seq() == 0) {
            List<Functions> allFunctions = esFunctionsRepository.findAll();
            List<Group_Functions> findGroupFunctionTest = esGroupFunctionsRepository.findAll();
            Optional<Groups> s = Optional.ofNullable(esGroupRepository.findByGroupName(requestEsGroupFunction.getName()));

            List<Group_Functions> filteredFunctions = findGroupFunctionTest.stream()
                    .filter(function -> function.getGroup_seq() == s.get().getGroup_seq())
                    .collect(Collectors.toList());
            List<Functions> allAllowGroupf = new ArrayList<>();
            for (int i = 0; i < filteredFunctions.size(); i++) {
                final int group_seq = filteredFunctions.get(i).getFunction_seq();
                List<Functions> allAllowGroup = allFunctions.stream()
                        .filter(group -> group.getSeq() == group_seq)
                        .collect(Collectors.toList());
                if (allAllowGroup.size() != 0) {
                    allAllowGroupf.addAll(allAllowGroup);
                }
            }
            logger.info("EsGroupController : searchEsGroupFunction() Method Called : Successfully");
            return EsGroupController.generateSearchFunctionResponse(HttpStatus.OK, false, "Success", _groups, allAllowGroupf);
        } else {
            logger.info("EsGroupController : searchEsGroupFunction() Method Called : No record found ");
            return EsGroupController.generateSearchResponse(HttpStatus.OK, false, "No record found", null, null);
        }
    }


    @PostMapping("/group/function/create")
    Groups createEsGroupFunctionList(@RequestHeader("Authorization") String token, @RequestBody UpdateRequestEsGroupFunction esGroupFunction) {
        logger.info("EsGroupController : createEsGroupFunctionList() Method Called : ");
        GroupActions action = GroupActions.CREATESGROUPWITHFUNCTIONLIST;
        esSessionActionService.addGroupActions(action, groupActionsService, token);
        Groups _groups1 = esGroupRepository.findByGroupName(esGroupFunction.getName());
        if (_groups1 != null) {
            logger.info("EsGroupController : createEsGroupFunctionList() Method Called : Group already exist.");
            return null;
        }
        Groups _groups = esGroupFunctionService.createEsGroupFunctionList(esGroupFunction);
        if (_groups.getSeq() != 0) {
            logger.info("EsGroupController : createEsGroupFunctionList() Method Called : Successfully ");
            return _groups;
        } else {
            return null;
        }
    }


    @PutMapping("/group/update/{id}")
    Object updateEsGroup(@RequestHeader("Authorization") String token, @RequestBody UpdateRequestEsGroupFunction es_groups, @PathVariable("id") Integer id) {
        logger.info("EsGroupController : updateEsGroup() Method Called : ");
        GroupActions action = GroupActions.UPDATEGROUPBYID;
        esSessionActionService.addGroupActions(action, groupActionsService, token);

        Optional<Groups> stu = esGroupService.findGroupById(id);
        List<Functions> _functionsList = es_groups.getEs_functions();
        if (stu.isPresent()) {
            stu.get().setName(es_groups.getName());
            stu.get().setDescription(es_groups.getDescription());
            esGroupRepository.save(stu.get());

            if (_functionsList != null) {
                List<Group_Functions> esGroupFunctionsList = esGroupFunctionsRepository.findAll();
                List<Functions> _functionsAll = esFunctionsRepository.findAll();
                List<Functions> _functionsListCount = new ArrayList<>();
                for (int i = 0; i < _functionsList.size(); i++) {
                    final int func_seq = _functionsList.get(i).getSeq();
                    final int group_seq = es_groups.getSeq();
                    for (int k = 0; k < esGroupFunctionsList.size(); k++) {
                        if (func_seq == esGroupFunctionsList.get(k).getFunction_seq() && esGroupFunctionsList.get(k).getGroup_seq() == group_seq) {
                            _functionsListCount.add(_functionsList.get(i));
                        } else {
                            continue;
                        }
                    }

                }

                List<Group_Functions> totalGroupFunctionValue = esGroupFunctionsRepository.findAll();

                for (int i = 0; i < totalGroupFunctionValue.size(); i++) {
                    if (totalGroupFunctionValue.get(i).getGroup_seq() == es_groups.getSeq()) {
                        esGroupFunctionsRepository.deleteById(totalGroupFunctionValue.get(i).getId());
                    }
                }

                List<Group_Functions> esGroupFunctionsTotal = esGroupFunctionsRepository.findAll();
                List<Functions> differentObjects = new ArrayList<>();
                differentObjects.addAll(_functionsList.stream()
                        .filter(obj -> !_functionsListCount.contains(obj))
                        .collect(Collectors.toList()));

                for (int i = 0; i < _functionsList.size(); i++) {
                    Group_Functions _group_functions = new Group_Functions();
                    _group_functions.setGroup_seq(stu.get().getGroup_seq());
                    _group_functions.setFunction_seq(_functionsList.get(i).getSeq());
                    _group_functions.setModified_date(currentDate);
                    _group_functions.setModified_by(es_groups.getRole());

                    esGroupFunctionsRepository.save(_group_functions);
                }
                logger.info("EsGroupController : updateEsGroup() Method Called : Successfully");
                return _functionsList;
            }
            logger.info("EsGroupController : updateEsGroup() Method Called : No update in function");
            return null;
        } else {
            logger.info("EsGroupController : updateEsGroup() Method Called : No updates ");
            return null;
        }
    }


    @DeleteMapping("/group/delete/{id}")
    Optional<Groups> deleteEsGroupById(@RequestHeader("Authorization") String token, @PathVariable Integer id) {
        logger.info("EsGroupController : deleteEsGroupById() Method Called : ");
        GroupActions action = GroupActions.DELETEAGROUPBYID;
        esSessionActionService.addGroupActions(action, groupActionsService, token);

        Optional<Groups> es_groups = esGroupService.deleteEsGroupById(id);
        if (!es_groups.isPresent()) {
            logger.info("EsGroupController : deleteEsGroupById() Method Called : Invalid Id ");
            return null;
        }
        List<Group_Functions> findGroupFunctionTest = esGroupFunctionsRepository.findAll();

        List<Group_Functions> filteredGroups = findGroupFunctionTest.stream()
                .filter(group -> group.getGroup_seq() == id)
                .collect(Collectors.toList());
        for (int i = 0; i < filteredGroups.size(); i++) {
            esGroupFunctionsRepository.deleteById(filteredGroups.get(i).getId());
        }

        List<User_Groups> findGroupUserTest = esUserGroupRepository.findAll();
        List<User_Groups> filteredUserGroups = findGroupUserTest.stream()
                .filter(group -> group.getGroup_seq() == id)
                .collect(Collectors.toList());
        for (int i = 0; i < filteredUserGroups.size(); i++) {
            esUserGroupRepository.deleteById(filteredUserGroups.get(i).getId());
        }
        logger.info("EsGroupController : deleteEsGroupById() Method Called : Successfully ");
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

    public static ResponseEntity<Object> generateSearchResponse(HttpStatus status, boolean error, String message, Groups token, Optional<Functions> es_groups_function) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Properties properties = new Properties();
            properties.put("message", message);
            properties.put("Function", token);
            properties.put("Group", es_groups_function);
            map.put("success", true);
            map.put("response", properties);

            return new ResponseEntity<Object>(map, status);
        } catch (Exception e) {
            map.clear();
            Properties properties = new Properties();
            properties.put("message", message);
            properties.put("Function", "");
            properties.put("Group", "");
            map.put("success", false);
            map.put("response", properties);
            return new ResponseEntity<Object>(map, status);
        }
    }

    public static ResponseEntity<Object> generateSearchFunctionResponse(HttpStatus status, boolean error, String message, Groups token, List<Functions> es_user_groups) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Properties properties = new Properties();
            properties.put("message", message);
            properties.put("Group", token);
            properties.put("Function", es_user_groups);
            map.put("success", true);
            map.put("response", properties);

            return new ResponseEntity<Object>(map, status);
        } catch (Exception e) {
            map.clear();
            Properties properties = new Properties();
            properties.put("message", message);
            properties.put("Group", "");
            properties.put("Function", "");
            map.put("success", false);
            map.put("response", properties);
            return new ResponseEntity<Object>(map, status);
        }
    }

    public static ResponseEntity<Object> generateAllGroupResponseWithCount(HttpStatus status, boolean error, String message, List<Groups> token, Integer count) {
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
            properties.put("Group", token);
            map.put("success", true);
            map.put("total_count", count);
            map.put("pages", pageValue);
            map.put("response", properties);

            return new ResponseEntity<Object>(map, status);
        } catch (Exception e) {
            map.clear();
            Properties properties = new Properties();
            properties.put("message", message);
            properties.put("Group", "");
            map.put("success", false);
            map.put("total_count", count);
            map.put("pages", "");
            map.put("response", properties);
            return new ResponseEntity<Object>(map, status);
        }
    }
}