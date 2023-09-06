package com.vodacom.er.gui.controller;

import com.vodacom.er.gui.actions.UserActions;
import com.vodacom.er.gui.actionmessage.UserActionsService;
import com.vodacom.er.gui.entity.*;
import com.vodacom.er.gui.interceptor.LoggingInterceptor;
import com.vodacom.er.gui.mapper.*;
import com.vodacom.er.gui.repository.*;
import com.vodacom.er.gui.service.*;
import com.vodacom.er.gui.util.JwtUtilClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

//@CrossOrigin(origins = "*")
@RestController
public class EsUserController {

//  @Autowired
//  private JwtUtil jwtUtil;
//
//  @Autowired
//  private AuthenticationManager authenticationManager;


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtilClass jwtUtilClass;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private EsUserRepository esUserRepository;

    @Autowired
    private EsUserService esUserService;

    @Autowired
    private EsGroupService esGroupService;

    @Autowired
    private EsUserGroupRepository esUserGroupRepository;

    @Autowired
    private EsUserGroupService esUserGroupService;

    @Autowired
    private EsGroupFunctionsRepository esGroupFunctionsRepository;

    @Autowired
    private EsGroupRepository esGroupRepository;

    @Autowired
    private EsFunctionsRepository esFunctionsRepository;

    @Autowired
    private EsSessionActionService esSessionActionService;

    @Autowired
    private EsSessionService esSessionService;

    private final UserActionsService userActionsService;

    @Autowired
    public EsUserController(UserActionsService userActionsService) {
        this.userActionsService = userActionsService;
    }

    Date currentDate = new Date();

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @GetMapping("/es/")
    public String welcome() {
        logger.info("es user route called");
        return "Welcome to jwt application !!";
    }

    @GetMapping("/es/search/{name}")
    public Users searchByName(@PathVariable String name, @RequestHeader("Authorization") String token) {
        logger.info("EsUserController : searchByName() called :  ");
        UserActions action = UserActions.SEARCHBYNAME;
        esSessionActionService.addActions(action, userActionsService, token);
//        System.out.println("es user searchByName() route called");
        logger.info("es user searchByName() route called");
        Users _users = esUserRepository.findByUserName(name);
        if (_users.getUser_seq() != 0) {
            logger.info("EsUserController : searchByName() called : Successfully ");
            return esUserRepository.findByUserName(name);
        } else {
            logger.info("EsUserController : searchByName() called :  Successfully , No record found");
            return null;
        }
    }

    @GetMapping("/es/logout")
    public ResponseEntity<Object> logout(@RequestHeader("Authorization") String token) {
        logger.info("EsUserController : logout() called :  ");
        Object obj = esSessionService.logoutSessionByUser(token);
        if (obj != null) {
            logger.info("EsUserController : logout() called : Successfully Logout ");
            return EsUserController.generateResponse(HttpStatus.OK, true, "Successfully Logout", obj.toString());
        }
        logger.info("EsUserController : logout() called : Invalid Token ");
        return EsUserController.generateResponse(HttpStatus.UNAUTHORIZED, false, "Invalid token ", token);
    }

    @GetMapping("/es/user/listGroups/all")
    public List<UserListGroups> allGroupWithFunctions(@RequestHeader("Authorization") String token) {
        logger.info("EsUserController : allGroupWithFunctions() called :  ");
        UserActions action = UserActions.READALLNORMALUSERWITHGROUPS;
        esSessionActionService.addActions(action, userActionsService, token);
        List<Groups> _groups = esGroupRepository.findAll();
        List<Users> _users = esUserRepository.findAll();
        List<User_Groups> esUserGroupsList = esUserGroupRepository.findAll();

        List<UserListGroups> allAllowUserGroup = new ArrayList<>();
        for (int i = 0; i < _users.size(); i++) {
            UserListGroups esUserGroupResponse = new UserListGroups();
            esUserGroupResponse.setEs_users(_users.get(i));
            final int user_seq = _users.get(i).getUser_seq();
            if (_users.get(i).getRole().equals("admin")) {
                continue;
            } else {
                List<User_Groups> allGroup = esUserGroupsList.stream()
                        .filter(group -> group.getUser_seq() == user_seq)
                        .collect(Collectors.toList());
                List<Groups> esGroupsArrayList = new ArrayList<>();
                for (int j = 0; j < allGroup.size(); j++) {
                    final int group_seq = allGroup.get(j).getGroup_seq();
                    for (int k = 0; k < _groups.size(); k++) {
                        if (group_seq == _groups.get(k).getSeq()) {
                            esGroupsArrayList.add(_groups.get(k));
                        }
                    }
                }
                esUserGroupResponse.setEs_groups(esGroupsArrayList);
                allAllowUserGroup.add(esUserGroupResponse);
            }

        }
        logger.info("EsUserController : allGroupWithFunctions() called : Successfully ");
        return allAllowUserGroup;
    }


    @GetMapping("/es/user/function/all/{id}")
    public List<Users> allEsUserFunctions(@PathVariable("id") String username, @RequestHeader("Authorization") String token) {
        logger.info("EsUserController : allEsUserFunctions() called :  ");
        UserActions action = UserActions.READALLUSERFUNCTION;
        esSessionActionService.addActions(action, userActionsService, token);
        Users _users = esUserService.findByUserName(username);
        List<User_Groups> _user_groups = esUserGroupRepository.findAll();
        List<Group_Functions> _group_functions = esGroupFunctionsRepository.findAll();

        // Search for IDs from users1 in users2
        List<Integer> matchingIds = _user_groups.stream()
                .map(User_Groups::getGroup_seq)  // Extract the IDs from users1
                .filter(group_seq -> _group_functions.stream().anyMatch(es_group_functions1 -> es_group_functions1.getGroup_seq() == group_seq))  // Check if the ID exists in users2
                .collect(Collectors.toList());

//        System.out.println("Before........");
//        matchingIds.forEach(System.out::println);
//        System.out.println("After......");
        logger.info("EsUserController : allEsUserFunctions() called : Successfully ");
        return esUserService.allUser();
    }

    @PostMapping("/es/user/group/functions/search")
    Object searchEsUserGroupFunction(@RequestBody RequestSearchEsUser requestSearchEsUser, @RequestHeader("Authorization") String token) {
        logger.info("EsUserController : searchEsUserGroupFunction() called :  ");
        UserActions action = UserActions.SEARCHGROUPFUNCTION;
        esSessionActionService.addActions(action, userActionsService, token);
        List<User_Groups> findUserGroupTest = esUserGroupRepository.findAll();
        List<Groups> allGroups = esGroupRepository.findAll();
        Optional<Users> s = Optional.ofNullable(esUserRepository.findByUserName(requestSearchEsUser.getName()));
        if (!s.isPresent()) {
            logger.info("EsUserController : searchEsUserGroupFunction() called : No record found ");
            return null;
        }
        if (s.isPresent()) {
            Users _users = s.get();
            List<User_Groups> filteredGroups = findUserGroupTest.stream()
                    .filter(user -> user.getUser_seq() == s.get().getUser_seq())
                    .collect(Collectors.toList());
            List<Groups> allAllowGroupf = new ArrayList<>();
            for (int i = 0; i < filteredGroups.size(); i++) {
                final int group_seq = filteredGroups.get(i).getGroup_seq();
                List<Groups> allAllowGroup = allGroups.stream()
                        .filter(group -> group.getGroup_seq() == group_seq)
                        .collect(Collectors.toList());
                if (allAllowGroup.size() != 0) {
                    allAllowGroupf.addAll(allAllowGroup);
                }
            }
            List<Group_Functions> esGroupFunctionsList = esGroupFunctionsRepository.findAll();

            List<Group_Functions> allAllowFunction = new ArrayList<>();
            for (int i = 0; i < allAllowGroupf.size(); i++) {
                final int group_seq = allAllowGroupf.get(i).getGroup_seq();
                List<Group_Functions> allAllowGroup = esGroupFunctionsList.stream()
                        .filter(function -> function.getGroup_seq() == group_seq)
                        .collect(Collectors.toList());
                if (allAllowGroup.size() != 0) {
                    allAllowFunction.addAll(allAllowGroup);
                }
            }
            List<Functions> _functions = esFunctionsRepository.findAll();

            List<Functions> esFunctionsArrayList = new ArrayList<>();
            for (int i = 0; i < allAllowFunction.size(); i++) {
                final int function_seq = allAllowFunction.get(i).getFunction_seq();
                List<Functions> allAllowGroup = _functions.stream()
                        .filter(function -> function.getSeq() == function_seq)
                        .collect(Collectors.toList());
                if (allAllowGroup.size() != 0) {
                    esFunctionsArrayList.addAll(allAllowGroup);
                }
            }
            logger.info("EsUserController : searchEsUserGroupFunction() called : Successfully ");
            return EsUserController.generateSearchGroupFunctionResponse(HttpStatus.OK, false, "Success", _users, allAllowGroupf, esFunctionsArrayList);
        } else {
            logger.info("EsUserController : searchEsUserGroupFunction() called : Successfully ");
            return EsUserController.generateSearchGroupFunctionResponse(HttpStatus.OK, false, "Success", null, null, null);
        }
    }


    @PostMapping("/es/search")
    Object searchEsUser(@RequestBody RequestSearchEsUser requestSearchEsUser, @RequestHeader("Authorization") String token) {

        logger.info("EsUserController : searchEsUser() called :  ");
        UserActions action = UserActions.SEARCHAUSER;
        esSessionActionService.addActions(action, userActionsService, token);
        Users _users = esUserService.findByUserName(requestSearchEsUser.getName());
        Optional<Groups> es_groups = esGroupService.findGroupById(requestSearchEsUser.getGroup_seq());
        List<Groups> allGroups = esGroupRepository.findAll();

        if (_users == null) {
            logger.info("EsUserController : searchEsUser() called : Successfully , No record found ");
            return EsUserController.generateSearchResponse(HttpStatus.OK, false, "No record found", null, null);
        }
        if (es_groups.isPresent()) {
            Optional<Users> s = Optional.ofNullable(esUserRepository.findByUserName(requestSearchEsUser.getName()));

            if (s.isPresent()) {
                List<Groups> allAllowGroups = esUserGroupService.getActualUserGroupBySeqId(requestSearchEsUser, s.get().getUser_seq());
                logger.info("EsUserController : searchEsUser() called : Successfully ");
                return EsUserController.generateSearchGroupResponse(HttpStatus.OK, false, "Success", _users, allAllowGroups);
            }
        } else if (requestSearchEsUser.getGroup_seq() == 0) {

            List<User_Groups> findUserGroupTest = esUserGroupRepository.findAll();
            Optional<Users> s = Optional.ofNullable(esUserRepository.findByUserName(requestSearchEsUser.getName()));
            List<User_Groups> filteredGroups = findUserGroupTest.stream()
                    .filter(user -> user.getUser_seq() == s.get().getUser_seq())
                    .collect(Collectors.toList());
            List<Groups> allAllowGroupf = new ArrayList<>();
            for (int i = 0; i < filteredGroups.size(); i++) {
                final int group_seq = filteredGroups.get(i).getGroup_seq();
                List<Groups> allAllowGroup = allGroups.stream()
                        .filter(group -> group.getSeq() == group_seq)
                        .collect(Collectors.toList());
                if (allAllowGroup.size() != 0) {
                    allAllowGroupf.addAll(allAllowGroup);
                }
            }
//            System.out.println("final values is");
//            allAllowGroupf.forEach(System.out::println);
            logger.info("EsUserController : searchEsUser() called : Successfully ");
            return EsUserController.generateSearchGroupResponse(HttpStatus.OK, false, "Success", _users, allAllowGroupf);
        } else {
            logger.info("EsUserController : searchEsUser() called : Successfully , No record found ");
            return EsUserController.generateSearchResponse(HttpStatus.OK, false, "No record found", null, null);
        }
        logger.info("EsUserController : searchEsUser() called : Successfully , No record found ");
        return EsUserController.generateSearchResponse(HttpStatus.OK, false, "No record found", null, null);
    }

    @GetMapping("/es/{id}")
    public Optional<Users> getEsUserById(@PathVariable("id") Integer id, @RequestHeader("Authorization") String token) {
        UserActions action = UserActions.USERBYID;
        esSessionActionService.addActions(action, userActionsService, token);
        System.out.println("es user route called");
        Optional<Users> es_users = esUserService.getActualUserById(id);
        if (!es_users.isPresent()) {
            System.out.println("Invalid Id: ");
            logger.info("EsUserController : getEsUserById() called : Invalid Id ");
            return null;
        }
        logger.info("EsUserController : getEsUserById() called : Successfully ");
        return esUserService.getActualUserById(id);
    }

    @GetMapping("/es/all")
    public List<Users> allEsUser(@RequestHeader("Authorization") String token) {
        UserActions action = UserActions.ALLUSER;
        logger.info("EsUserController : allEsUser() called : ");
        esSessionActionService.addActions(action, userActionsService, token);
        logger.info("EsUserController : allEsUser() called : Successfully");
        return esUserService.allUser();
    }

    @GetMapping("/es/all/{pageNumber}")
    public ResponseEntity<Object> allEsUserWithPagination(@PathVariable Integer pageNumber, @RequestHeader("Authorization") String token) {
        logger.info("EsUserController : allEsUserWithPagination() called : ");
        UserActions action = UserActions.ALLUSERWITHPAGINATION;
        esSessionActionService.addActions(action, userActionsService, token);
        Integer readCount = pageNumber * 10;
        Integer startLoop = readCount - 10;
        List<Users> _users = esUserRepository.findAll();
        if (_users.size() < startLoop) {
            logger.info("EsUserController : allEsUserWithPagination() called : Successfully ");
            return EsUserController.generateAllUserResponseWithCount(HttpStatus.OK, true, "Get All User Successfully", null, null);
        }
        List<Users> allAllowSubscription = new ArrayList<>();
        for (int i = startLoop; i < readCount; i++) {
            if (_users.size() == i) {
                break;
            }
            if (_users.size() >= startLoop) {
                allAllowSubscription.add(_users.get(i));
            } else {
                break;
            }
        }
        logger.info("EsUserController : allEsUserWithPagination() called : Successfully");
        return EsUserController.generateAllUserResponseWithCount(HttpStatus.OK, true, "Get All User Successfully", allAllowSubscription, _users.size());
    }


    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody AuthRequest authRequest) throws Exception {
        logger.info("EsUserController : login() called : ");
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUser_name(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            logger.info("EsUserController : login() called : Invalid Ldap User ");
            return EsUserController.generateResponse(HttpStatus.UNAUTHORIZED, false, "Invalid Ldap credentials", "");
        }
        String token = jwtUtilClass.generateToken(authRequest.getUser_name());
        if (token != null) {
            logger.info("EsUserController : login() called : Successfully Login");
            return EsUserController.generateResponse(HttpStatus.OK, true, "Successfully Login", token);
        }
        logger.info("EsUserController : login() called : Invalid DB credentials");
        return EsUserController.generateResponse(HttpStatus.FORBIDDEN, false, "Invalid DB credentials", "");
    }

    public boolean isCSVFormat(String input) {
        logger.info("EsUserController : isCSVFormat() called :");
        String csvPattern = "^[a-zA-Z0-9\\s,]*$";
        return input.matches(csvPattern);
    }

    @PostMapping("/es/user/group/create")
    Object createEsUserGroup(@RequestBody RequestEsUserGroup esUserGroup, @RequestHeader("Authorization") String token) {
        logger.info("EsUserController : createEsUserGroup() called :");
        UserActions action = UserActions.CREATEUSERWITHSINGLEGROUP;
        esSessionActionService.addActions(action, userActionsService, token);
        boolean isCSV = isCSVFormat(esUserGroup.getUser_name());
        if (isCSV) {
            List<Users> _usersList = esUserService.readCsvFormat(esUserGroup.getUser_name());

            if (_usersList.isEmpty()) {
                logger.info("EsUserController : createEsUserGroup() called :Please create proper Csv request user names");
                return EsUserController.generateCreateGroupUserResponse(HttpStatus.OK, true, "Please create proper Csv request user names", null, null);
            }
            if (esUserGroup.getSeq() == 0) {
                logger.info("EsUserController : createEsUserGroup() called :Please create proper request");
                return EsUserController.generateCreateGroupUserResponse(HttpStatus.OK, true, "Please create proper request", null, null);
            }
//        Users es_users = esUserGroupService.createEsUserGroup(esUserGroup);
            List<Users> _usersList1 = esUserGroupService.createListEsUserGroup(_usersList, esUserGroup);
            if (_usersList1 != null) {
                Optional<Groups> es_groups = esGroupRepository.findById(esUserGroup.getGroup_seq());
                logger.info("EsUserController : createEsUserGroup() called : User created successfully");
                return EsUserController.generateCreateListGroupUserResponse(HttpStatus.OK, true, "User created successfully", _usersList1, es_groups);
            } else {
                logger.info("EsUserController : createEsUserGroup() called : User already exist");
                return EsUserController.generateCreateGroupUserResponse(HttpStatus.OK, false, "User already exist", null, null);
            }
        } else {
            logger.info("EsUserController : createEsUserGroup() called : The string is not in CSV format.");
            return EsUserController.generateCreateGroupUserResponse(HttpStatus.OK, false, "The string is not in CSV format.", null, null);
        }
    }

    @PostMapping("/es/admin/user/group/create")
    Object createEsAdminUserGroup(@RequestBody RequestEsUserGroup esUserGroup, @RequestHeader("Authorization") String token) {
        logger.info("EsUserController : createEsAdminUserGroup() called : ");
        UserActions action = UserActions.CREATEUSERWITHSINGLEGROUP;
        esSessionActionService.addActions(action, userActionsService, token);

        boolean isCSV = isCSVFormat(esUserGroup.getUser_name());
        if (isCSV) {
            List<Users> _usersList = esUserService.readCsvFormat(esUserGroup.getUser_name());

            if (_usersList.isEmpty()) {
                logger.info("EsUserController : createEsAdminUserGroup() called : Please create proper Csv request user names");
                return EsUserController.generateCreateGroupUserResponse(HttpStatus.OK, true, "Please create proper Csv request user names", null, null);
            }

            List<Users> _usersList1 = esUserGroupService.createListEsAdminUserGroup(_usersList, esUserGroup);
            if (_usersList1 != null) {
                logger.info("EsUserController : createEsAdminUserGroup() called : User created successfully");
                Optional<Groups> es_groups = esGroupRepository.findById(esUserGroup.getGroup_seq());
                return EsUserController.generateCreateListGroupUserResponse(HttpStatus.OK, true, "User created successfully", _usersList1, es_groups);
            } else {
                logger.info("EsUserController : createEsAdminUserGroup() called : User already exist");
                return EsUserController.generateCreateGroupUserResponse(HttpStatus.OK, false, "User already exist", null, null);
            }
        } else {
            logger.info("EsUserController : createEsAdminUserGroup() called : The string is not in CSV format.");
            return EsUserController.generateCreateGroupUserResponse(HttpStatus.OK, false, "The string is not in CSV format.", null, null);
        }
    }


    @PostMapping("/es/create")
    Users createEsUser(@RequestBody Users _users, @RequestHeader("Authorization") String token) {
        logger.info("EsUserController : createEsUser() called : ");
        UserActions action = UserActions.CREATEUSER;
        esSessionActionService.addActions(action, userActionsService, token);

        if (_users.getSeq() == 0) {
            logger.info("EsUserController : createEsUser() called : No record found");
            return null;
        }

        Optional<Users> stu = esUserRepository.findById(_users.getSeq());
        if (stu.isPresent()) {
            logger.info("EsUserController : createEsUser() called : Already User Exit");
            return null;
        } else {
            logger.info("EsUserController : createEsUser() called : Successfully");
            return esUserService.addEsUser(_users);
        }
    }

    @PostMapping("/es/groups/create")
    Users createEsUserGroupList(@RequestBody UpdateRequestEsUserGroupList es_users, @RequestHeader("Authorization") String token) {
        logger.info("EsUserController : createEsUserGroupList() called : ");
        UserActions action = UserActions.CREATEUSERWITHGROUPLIST;
        esSessionActionService.addActions(action, userActionsService, token);

        Optional<Users> stu = esUserRepository.findById(es_users.getSeq());
        if (stu.isPresent()) {
            logger.info("EsUserController : createEsUserGroupList() called : Already User Exit");
            return null;
        } else {
            Users _users1 = esUserService.createEsUserGroupList(es_users);
            logger.info("EsUserController : createEsUserGroupList() called : Successfully");
            return esUserService.addEsUser(_users1);
        }
    }

    @PutMapping("/es/update/{id}")
    Object updateEsUser(@RequestBody UpdateRequestEsUserGroup es_users, @PathVariable("id") Integer id, @RequestHeader("Authorization") String token) {
        logger.info("EsUserController : updateEsUser() called : ");
        UserActions action = UserActions.UPDATEUSERBYID;
        esSessionActionService.addActions(action, userActionsService, token);

        Optional<Users> stu = esUserRepository.findById(id);
        if (!stu.isPresent()) {
            logger.info("EsUserController : updateEsUser() called : Invalid User");
            return null;
        } else {
            Users _users1 = esUserGroupService.updateActualEsUserGroup(es_users);
            logger.info("EsUserController : updateEsUser() called : Successfully");
            return _users1;
        }

    }

    @PutMapping("/es/group/update/{id}")
    Object updateEsUserGroupList(@RequestBody UpdateRequestEsUserGroupList es_users, @PathVariable("id") Integer id, @RequestHeader("Authorization") String token) {
        logger.info("EsUserController : updateEsUserGroupList() called : ");
        UserActions action = UserActions.UPDATEUSERBYGROUPLIST;
        esSessionActionService.addActions(action, userActionsService, token);

        Optional<Users> stu = esUserRepository.findById(id);
        if (!stu.isPresent()) {
            logger.info("EsUserController : updateEsUserGroupList() called : Invalid User");
            return null;
        } else {
            List<Groups> es_users1 = esUserGroupService.updateActualEsUserGroupList(es_users);
            logger.info("EsUserController : updateEsUserGroupList() called : Successfully");
            return es_users1;
        }

    }

    @DeleteMapping("/es/delete/{id}")
    Optional<Users> deleteEsUserById(@PathVariable Integer id, @RequestHeader("Authorization") String token) {
        logger.info("EsUserController : deleteEsUserById() called : ");
        UserActions action = UserActions.DELETEBYUSER;
        esSessionActionService.addActions(action, userActionsService, token);

        Optional<Users> es_users = customUserDetailsService.deleteEsUserById(id);
        if (!es_users.isPresent()) {
            logger.info("EsUserController : deleteEsUserById() called : Invalid ID.");
            return null;
        }
        List<User_Groups> findUserGroupTest = esUserGroupRepository.findAll();
        List<User_Groups> filteredUsers = findUserGroupTest.stream()
                .filter(user -> user.getUser_seq() == id)
                .collect(Collectors.toList());
        for (int i = 0; i < filteredUsers.size(); i++) {
            System.out.println(" i = " + i);
            esUserGroupRepository.deleteById(filteredUsers.get(i).getId());
        }
//        return customUserDetailsService.deleteEsUserById(id);
        logger.info("EsUserController : deleteEsUserById() called : Successfully");
        return es_users;
    }

    @PostMapping("/es/group/delete/{id}")
    Optional<Users> deleteAEsUserWithInGroupsById(@RequestBody RequestEsUserGroup requestEsUserGroup, @RequestHeader("Authorization") String token) {
        logger.info("EsUserController : deleteAEsUserWithInGroupsById() called : ");
        UserActions action = UserActions.DELETEUSERBYGROUP;
        esSessionActionService.addActions(action, userActionsService, token);

        Optional<Users> es_users = customUserDetailsService.deleteEsUserById(requestEsUserGroup.getSeq());
        if (!es_users.isPresent()) {
            logger.info("EsUserController : deleteAEsUserWithInGroupsById() called : Invalid ID.");
            return null;
        }
        List<User_Groups> findUserGroupTest = esUserGroupRepository.findAll();
        Integer id = requestEsUserGroup.getSeq();

        List<User_Groups> filteredUsers = findUserGroupTest.stream()
                .filter(user -> user.getUser_seq() == id)
                .collect(Collectors.toList());
        for (int i = 0; i < filteredUsers.size(); i++) {
            System.out.println(" i = " + i);
            esUserGroupRepository.deleteById(filteredUsers.get(i).getId());
        }
        logger.info("EsUserController : deleteAEsUserWithInGroupsById() called : Successfully");
        return es_users;
    }

    public static ResponseEntity<Object> generateResponse(HttpStatus status, boolean tag, String message, String token) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Properties properties = new Properties();
            properties.put("token", token);
            map.put("success", tag);
            map.put("message", message);
            map.put("response", properties);

            return new ResponseEntity<Object>(map, status);
        } catch (Exception e) {
            map.clear();
            Properties properties = new Properties();
            properties.put("token", token);
            map.put("success", false);
            map.put("message", "Error");
            map.put("response", properties);
            return new ResponseEntity<Object>(map, status);
        }
    }

    public static ResponseEntity<Object> generateSearchResponse(HttpStatus status, boolean error, String message, Users token, Optional<Groups> es_user_groups) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Properties properties = new Properties();
            properties.put("message", message);
            properties.put("User", token);
            properties.put("Group", es_user_groups);
            map.put("success", true);
            map.put("response", properties);

            return new ResponseEntity<Object>(map, status);
        } catch (Exception e) {
            map.clear();
            Properties properties = new Properties();
            properties.put("message", message);
            properties.put("User", "");
            properties.put("Group", "");
            map.put("success", false);
            map.put("response", properties);
            return new ResponseEntity<Object>(map, status);
        }
    }

    public static ResponseEntity<Object> generateCreateGroupUserResponse(HttpStatus status, boolean error, String message, Users token, Set<Groups> _groups) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Properties properties = new Properties();
            properties.put("message", message);
            properties.put("Groups", _groups);
            properties.put("User", token);
            map.put("success", true);
            map.put("response", properties);

            return new ResponseEntity<Object>(map, status);
        } catch (Exception e) {
            map.clear();
            Properties properties = new Properties();
            properties.put("message", message);
            properties.put("Groups", "");
            properties.put("User", "");
            map.put("success", false);
            map.put("response", properties);
            return new ResponseEntity<Object>(map, status);
        }
    }

    public static ResponseEntity<Object> generateCreateListGroupUserResponse(HttpStatus status, boolean error, String message, List<Users> token, Optional<Groups> es_groups) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Properties properties = new Properties();
            properties.put("message", message);
            properties.put("Groups", es_groups);
            properties.put("User", token);
            map.put("success", true);
            map.put("response", properties);

            return new ResponseEntity<Object>(map, status);
        } catch (Exception e) {
            map.clear();
            Properties properties = new Properties();
            properties.put("message", message);
            properties.put("Groups", "");
            properties.put("User", "");
            map.put("success", false);
            map.put("response", properties);
            return new ResponseEntity<Object>(map, status);
        }
    }

    public static ResponseEntity<Object> generateSearchGroupResponse(HttpStatus status, boolean error, String message, Users token, List<Groups> es_user_groups) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Properties properties = new Properties();
            properties.put("message", message);
            properties.put("User", token);
            properties.put("Group", es_user_groups);
            map.put("success", true);
            map.put("response", properties);

            return new ResponseEntity<Object>(map, status);
        } catch (Exception e) {
            map.clear();
            Properties properties = new Properties();
            properties.put("message", message);
            properties.put("User", "");
            properties.put("Group", "");
            map.put("success", false);
            map.put("response", properties);
            return new ResponseEntity<Object>(map, status);
        }
    }

    public static ResponseEntity<Object> generateSearchGroupFunctionResponse(HttpStatus status, boolean error, String message, Users token, List<Groups> groups, List<Functions> _functions) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Properties properties = new Properties();
            properties.put("message", message);
            properties.put("User", token);
            properties.put("Groups", groups);
            properties.put("Functions", _functions);
            map.put("success", true);
            map.put("response", properties);

            return new ResponseEntity<Object>(map, status);
        } catch (Exception e) {
            map.clear();
            Properties properties = new Properties();
            properties.put("message", message);
            properties.put("User", "");
            properties.put("Groups", "");
            properties.put("Functions", _functions);
            map.put("success", false);
            map.put("response", properties);
            return new ResponseEntity<Object>(map, status);
        }
    }

    public static ResponseEntity<Object> generateAllUserResponseWithCount(HttpStatus status, boolean error, String message, List<Users> token, Integer count) {
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
            properties.put("User", token);
            map.put("success", true);
            map.put("total_count", count);
            map.put("pages", pageValue);
            map.put("response", properties);

            return new ResponseEntity<Object>(map, status);
        } catch (Exception e) {
            map.clear();
            Properties properties = new Properties();
            properties.put("message", message);
            properties.put("User", "");
            map.put("success", false);
            map.put("total_count", count);
            map.put("pages", "");
            map.put("response", properties);
            return new ResponseEntity<Object>(map, status);
        }
    }
}
