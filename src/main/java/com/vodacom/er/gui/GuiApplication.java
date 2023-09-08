package com.vodacom.er.gui;

import com.vodacom.er.gui.entity.*;
import com.vodacom.er.gui.repository.*;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
@SecurityScheme(name = "authorization", scheme = "bearer", type = SecuritySchemeType.HTTP, in = SecuritySchemeIn.HEADER)

public class GuiApplication {

  // passwordEncoder enable  for without ldap testing
  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private EsUserRepository esUserRepository;

  @Autowired
  private EsGroupRepository esGroupRepository;

  @Autowired
  private EsFunctionsRepository esFunctionsRepository;

  @Autowired
  private EsSessionsRepository esSessionsRepository;

  @Autowired
  private EsSessionActionsRepository esSessionActionsRepository;

  @Autowired
  private EsSubFunctionsRepository esSubFunctionsRepository;

  @Autowired
  private EsUserGroupRepository esUserGroupRepository;


  @Autowired
  private EsGroupFunctionsRepository esGroupFunctionsRepository;

  @Autowired
  private EsSubscriptionRepository esSubscriptionRepository;
  Date currentDate = new Date();

  // for hard coded data

  @PostConstruct
  public void initUsers() {

      String adminUserName1 = "buttm001";
      String adminUserName2 = "user1";
      String adminUserName3 = "user2";

      if (esUserRepository.findByUserName(adminUserName1.toString()) == null && esUserRepository.findByUserName(adminUserName2.toString()) == null && esUserRepository.findByUserName(adminUserName3.toString()) == null) {
        List<Users> es_users = Stream.of(
            new Users(101, passwordEncoder.encode("password"),  adminUserName1, currentDate, currentDate, 1, "N", "name", "lastuser", "0000", "admin", "user1@gmail.com", "Y", currentDate, "admin"),
            new Users(102, passwordEncoder.encode("password"),  adminUserName2, currentDate, currentDate, 1, "Y", "name", "lastuser", "0000", "normal", "user2@gmail.com", "Y", currentDate, "admin"),
            new Users(103, passwordEncoder.encode("password"),  adminUserName3, currentDate, currentDate, 1, "N", "name", "lastuser", "0000", "normal", "user3@gmail.com", "Y", currentDate, "admin")
        ).collect(Collectors.toList());
        esUserRepository.saveAll(es_users);
      }

    //		List<Es_User_Groups> es_user_groups = Stream.of(
    //				new Es_User_Groups(111,1,4,currentDate,"admin")
    //		).collect(Collectors.toList());
    //		esUserGroupRepository.saveAll(es_user_groups);

    //		List<Es_Group_Functions> es_group_functions = Stream.of(
    //				new Es_Group_Functions(4,7,currentDate,"admin")
    //		).collect(Collectors.toList());
    //		esGroupFunctionsRepository.saveAll(es_group_functions);

    // passwordEncoder use for without ldap testing
//    List<Users> es_users = Stream.of(new Users(101, passwordEncoder.encode("password"), "buttm001", currentDate, currentDate, 1, "N", "name", "lastuser", "31054542580", "admin", "danish@gmail.com", "Y", currentDate, "modified_by"), new Users(102, passwordEncoder.encode("password"), "user2", currentDate, currentDate, 1, "Y", "name", "lastuser", "31054542580", "normal", "danish@gmail.com", "Y", currentDate, "modified_by"), new Users(103, passwordEncoder.encode("password"), "user3", currentDate, currentDate, 1, "N", "name", "lastuser", "31054542580", "normal", "danish@gmail.com", "Y", currentDate, "modified_by"), new Users(103, passwordEncoder.encode("password"), "buttm002", currentDate, currentDate, 1, "N", "name", "lastuser", "31054542580", "normal", "danish@gmail.com", "Y", currentDate, "modified_by")).collect(Collectors.toList());
//    esUserRepository.saveAll(es_users);

    ////     for actual groups
    List<Groups> es_groups = Stream.of(new Groups("System Operations", "System Operations Team", "Y", currentDate, "admin"), new Groups("Business Analyst", "Business Analyst Team", "Y", currentDate, "admin"), new Groups("Customer Care", "Customer Care Team", "Y", currentDate, "admin")).collect(Collectors.toList());
    esGroupRepository.saveAll(es_groups);

    //         for actual functions
    List<Functions> es_functions = Stream.of(new Functions("home", "/homes", "testing function1", "Y", currentDate, "admin"), new Functions("ERCC Tool", "http://ercc.vodacom.co.za/ercc", "testing function2", "Y", currentDate, "admin"), new Functions("Bulk Refund", "http://10.0.211.174:7000/#/home", "testing function3", "Y", currentDate, "admin"), new Functions("ER Pricing tool", "http://perg101zatcrh.vodacom.corp:9198/pricingtool/ChoosePricePlanVersion.do", "testing function3", "Y", currentDate, "admin"), new Functions("Insufficient Funds", "http://10.0.211.174:8284/transaction/history/", "testing function3", "Y", currentDate, "admin"), new Functions("Demo tool", "http://10.0.211.174:8082/demo/login.jsp", "testing function3", "Y", currentDate, "admin"), new Functions("Deactivations", "", "testing function3", "Y", currentDate, "admin")).collect(Collectors.toList());
    esFunctionsRepository.saveAll(es_functions);

    List<User_Groups> es_user_groups = Stream.of(new User_Groups(1, 5, currentDate, "admin"), new User_Groups(2, 5, currentDate, "admin"), new User_Groups(2, 6, currentDate, "admin"), new User_Groups(2, 7, currentDate, "admin"), new User_Groups(3, 6, currentDate, "admin"), new User_Groups(3, 7, currentDate, "admin"), new User_Groups(4, 5, currentDate, "admin")).collect(Collectors.toList());
    esUserGroupRepository.saveAll(es_user_groups);

    //		List<Es_Users> es_users = Stream.of(
    //				new Es_Users(101, "buttm001","Blutech@0011", currentDate, currentDate, 1, "N", "name", "lastuser", "31054542580", "admin", "danish@gmail.com", "Y", currentDate, "modified_by"),
    //				new Es_Users(102, "user2", "password", currentDate, currentDate, 1, "Y", "name", "lastuser", "31054542580", "staff", "danish@gmail.com", "Y", currentDate, "modified_by"),
    //				new Es_Users(103, "user3", "password", currentDate, currentDate, 1, "N", "name", "lastuser", "31054542580", "user", "danish@gmail.com", "Y", currentDate, "modified_by"),
    //                new Es_Users(103, "buttm002", "password", currentDate, currentDate, 1, "N", "name", "lastuser", "31054542580", "normal", "danish@gmail.com", "Y", currentDate, "modified_by")
    //		).collect(Collectors.toList());
    //		esUserRepository.saveAll(es_users);

    //		List<Es_Groups> es_groups = Stream.of(
    //				new Es_Groups(101, "group1","this is test group1", "Y", currentDate, "admin"),
    //				new Es_Groups(102, "group2","this is test group2", "Y", currentDate, "admin"),
    //				new Es_Groups(103, "group3", "this is test group3","Y", currentDate, "admin")
    //		).collect(Collectors.toList());
    //		esGroupRepository.saveAll(es_groups);

    // for actual groups
    //    List<Es_Groups> es_groups = Stream.of(
    //        new Es_Groups(101, "QA", "this is test group1", "Y", currentDate, "admin"),
    //        new Es_Groups(102, "Development", "this is test group2", "Y", currentDate, "admin"),
    //        new Es_Groups(103, "Marketing", "this is test group3", "Y", currentDate, "admin")
    //    ).collect(Collectors.toList());
    //    esGroupRepository.saveAll(es_groups);

    //		List<Es_Functions> es_functions = Stream.of(
    //				new Es_Functions(101, "function1", "testing function1", "Y", currentDate, "admin"),
    //				new Es_Functions(102, "function2", "testing function2", "Y", currentDate, "admin"),
    //				new Es_Functions(103, "function3", "testing function3", "Y", currentDate, "admin")
    //		).collect(Collectors.toList());
    //		esFunctionsRepository.saveAll(es_functions);

    // for actual functions
    //    List<Es_Functions> es_functions = Stream.of(
    //        new Es_Functions(101,"home", "/homes", "testing function1", "Y", currentDate, "admin"),
    //        new Es_Functions(102,"ERCC Tool", "http://ercc.vodacom.co.za/ercc", "testing function2", "Y", currentDate, "admin"),
    //        new Es_Functions(103,"Bulk Refund","http://10.0.211.174:7000/#/home",  "testing function3", "Y", currentDate, "admin"),
    //        new Es_Functions(104,"ER Pricing tool","http://perg101zatcrh.vodacom.corp:9198/pricingtool/ChoosePricePlanVersion.do",  "testing function3", "Y", currentDate, "admin"),
    //        new Es_Functions(105,"Insufficient Funds","http://10.0.211.174:8284/transaction/history/",  "testing function3", "Y", currentDate, "admin"),
    //        new Es_Functions(106,"Demo tool", "http://10.0.211.174:8082/demo/login.jsp", "testing function3", "Y", currentDate, "admin"),
    //        new Es_Functions(107,  "Deactivations","", "testing function3", "Y", currentDate, "admin")
    //    ).collect(Collectors.toList());
    //    esFunctionsRepository.saveAll(es_functions);

    //    List<Es_User_Groups> es_user_groups = Stream.of(
    //        new Es_User_Groups( 111,1, 5, currentDate, "admin"),
    //        new Es_User_Groups(111, 2, 5, currentDate, "admin"),
    //        new Es_User_Groups(111, 2, 6, currentDate, "admin"),
    //        new Es_User_Groups(111, 2, 7, currentDate, "admin"),
    //        new Es_User_Groups(111, 3, 6, currentDate, "admin"),
    //        new Es_User_Groups(111, 3, 7, currentDate, "admin"),
    //        new Es_User_Groups(111, 4, 5, currentDate, "admin")
    //    ).collect(Collectors.toList());
    //    esUserGroupRepository.saveAll(es_user_groups);

    //    List<Es_Group_Functions> es_group_functions = Stream.of(
    //        //                 new Es_Group_Functions(111,4, 7, currentDate, "admin"),
    //        new Es_Group_Functions(111,5, 8, currentDate, "admin"),
    //        new Es_Group_Functions(111,5, 9, currentDate, "admin"),
    //        new Es_Group_Functions(111,5, 10, currentDate, "admin"),
    //        new Es_Group_Functions(111,5, 11, currentDate, "admin"),
    //        new Es_Group_Functions(111,5, 12, currentDate, "admin"),
    //        new Es_Group_Functions(111,5, 13, currentDate, "admin"),
    //        new Es_Group_Functions(111,5, 14, currentDate, "admin"),
    //        //                new Es_Group_Functions(111,4, 8, currentDate, "admin"),
    //        new Es_Group_Functions(111,6, 9, currentDate, "admin"),
    //        new Es_Group_Functions(111,6, 10, currentDate, "admin"),
    //
    //        new Es_Group_Functions(111,7, 7, currentDate, "admin"),
    //        new Es_Group_Functions(111,7, 8, currentDate, "admin"),
    //        new Es_Group_Functions(111,7, 9, currentDate, "admin"),
    //        new Es_Group_Functions(111,7, 10, currentDate, "admin")
    //    ).collect(Collectors.toList());
    //    esGroupFunctionsRepository.saveAll(es_group_functions);
    //
    //        List<Es_Group_Functions> es_group_functions = Stream.of(
    //                new Es_Group_Functions(4, 7, currentDate, "admin"),
    //                new Es_Group_Functions(4, 8, currentDate, "admin"),
    //                new Es_Group_Functions(4, 9, currentDate, "admin"),
    //                new Es_Group_Functions(5, 7, currentDate, "admin")
    //        ).collect(Collectors.toList());
    //        esGroupFunctionsRepository.saveAll(es_group_functions);

    //    List<Es_Sessions> es_sessions = Stream.of(
    //        new Es_Sessions(101, 1, "111", currentDate, currentDate, "end reason", "Y", currentDate),
    //        new Es_Sessions(102, 2, "222", currentDate, currentDate, "end reason", "Y", currentDate),
    //        new Es_Sessions(101, 3, "333", currentDate, currentDate, "end reason", "Y", currentDate)
    //    ).collect(Collectors.toList());
    //    esSessionsRepository.saveAll(es_sessions);

    //    List<Es_Session_Actions> es_session_actions = Stream.of(
    //        new Es_Session_Actions(101, "", "111", "actions details", 1, currentDate, currentDate, "end reason", "failure message", currentDate),
    //        new Es_Session_Actions(102, "", "222", "actions details", 2, currentDate, currentDate, "end reason", "failure message", currentDate),
    //        new Es_Session_Actions(103, "", "333", "actions details", 3, currentDate, currentDate, "end reason", "failure message", currentDate)
    //    ).collect(Collectors.toList());
    //    esSessionActionsRepository.saveAll(es_session_actions);

    //    List<Es_Sub_Functions> es_sub_functions = Stream.of(
    //        new Es_Sub_Functions(101, "function1", "testing function1", "Y", currentDate, "admin"),
    //        new Es_Sub_Functions(102, "function2", "testing function2", "Y", currentDate, "admin"),
    //        new Es_Sub_Functions(103, "function3", "testing function3", "Y", currentDate, "admin")
    //
    //    ).collect(Collectors.toList());
    //    esSubFunctionsRepository.saveAll(es_sub_functions);

    //    List<Es_Subscription> es_subscriptions = Stream.of(
    //        new Es_Subscription( "111", "22", "33","try","true","admin","test" ,currentDate),
    //        new Es_Subscription( "11122", "1122", "1133","try","false","admin","test" ,currentDate),
    //        new Es_Subscription( "11133", "3322", "2233","try","success","admin","test" ,currentDate)
    //
    //    ).collect(Collectors.toList());
    //    esSubscriptionRepository.saveAll(es_subscriptions);

  }

  public static void main(String[] args) {
    SpringApplication.run(GuiApplication.class, args);
  }

}