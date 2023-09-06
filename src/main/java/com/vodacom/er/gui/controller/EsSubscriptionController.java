package com.vodacom.er.gui.controller;


import com.vodacom.er.gui.actionmessage.SubscriptionActionsService;
import com.vodacom.er.gui.actions.SubscriptionActions;
import com.vodacom.er.gui.config.ApplicationProperties;
import com.vodacom.er.gui.entity.Subscription;
import com.vodacom.er.gui.interceptor.LoggingInterceptor;
import com.vodacom.er.gui.service.RequestService;
import com.vodacom.er.gui.repository.EsSubscriptionRepository;
import com.vodacom.er.gui.service.EsSessionActionService;
import com.vodacom.er.gui.service.EsSubscriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

//@CrossOrigin(origins = "*")
@RestController
public class EsSubscriptionController {

    @Autowired
    private EsSubscriptionRepository esSubscriptionRepository;

    @Autowired
    private EsSubscriptionService esSubscriptionService;

    @Autowired
    private EsSessionActionService esSessionActionService;

    @Autowired
    private RequestService requestService;

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    Date currentDate = new Date();

    private final SubscriptionActionsService subscriptionActionsService;
    private final ApplicationProperties applicationProperties;

    @Autowired
    public EsSubscriptionController(SubscriptionActionsService subscriptionActionsService , ApplicationProperties applicationProperties) {
        this.subscriptionActionsService = subscriptionActionsService;
        this.applicationProperties = applicationProperties;
    }


    @GetMapping("/subscription/{id}")
    public Optional<Subscription> getEsSubscriptionById(@PathVariable("id") Integer id, @RequestHeader("Authorization") String token) {
        logger.info("EsSubscriptionController : getEsSubscriptionById() Method Called :  ");
        SubscriptionActions action = SubscriptionActions.SUBSCRIPTIONBYID;
        esSessionActionService.addSessionActions(action,subscriptionActionsService,token);
        Optional<Subscription> es_subscription = esSubscriptionService.getSubscriptionById(id);
        if (!es_subscription.isPresent()) {
            logger.info("EsSubscriptionController : getEsSubscriptionById() Method Called :  Invalid Id");
            return null;
        }
        logger.info("EsSubscriptionController : getEsSubscriptionById() Method Called : Successfully ");
        return esSubscriptionService.getSubscriptionById(id);
    }

    @GetMapping("/subscription/all/{pageNumber}")
    public ResponseEntity<Object> allEsSubscription(HttpServletRequest request, @PathVariable Integer pageNumber,@RequestHeader("Authorization") String token) {
        logger.info("EsSubscriptionController : allEsSubscription() Method Called :  ");
        SubscriptionActions action = SubscriptionActions.ALLSUBSCRIPTIONWITHPAGINATION;
        esSessionActionService.addSessionActions(action,subscriptionActionsService,token);
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ") && pageNumber != 0) {

            Integer readCount = pageNumber * 10;
            Integer startLoop = readCount - 10;
//            List<Subscription> es_subscriptionList = esSubscriptionRepository.findAllWithLimit(readCount);
            List<Subscription> _subscription = esSubscriptionRepository.findAll();
            List<Subscription> allAllowSubscription = new ArrayList<>();
            for (int i = startLoop; i < readCount; i++) {
                System.out.println(" i = " + i);
                if (_subscription.size() == i) {
                    break;
                }
                if (_subscription.size() >= startLoop) {
                    allAllowSubscription.add(_subscription.get(i));
//                    break;
                } else {
                    break;
                }
            }
            logger.info("EsSubscriptionController : allEsSubscription() Method Called : Successfully ");
            return EsSubscriptionController.generateAllSubscriptionResponseWithCount(HttpStatus.OK, true, "All subscriotion details successfully", allAllowSubscription, _subscription.size());
        } else {
            logger.info("EsSubscriptionController : allEsSubscription() Method Called : Invalid or missing Authorization header ");
            return EsSubscriptionController.generateAllSubscriptionResponseWithCount(HttpStatus.OK, false, "No record found", null, null);
        }
    }

    @PostMapping("/subscription/bulk/create")
    ResponseEntity<Object> createEsSubscriptionBulk(@RequestBody List<Subscription> _subscription, @RequestHeader("Authorization") String token) {
        logger.info("EsSubscriptionController : createEsSubscriptionBulk() Method Called : ");
        SubscriptionActions action = SubscriptionActions.ADDBULKESSUBSCRIPTIONS;
        esSessionActionService.addSessionActions(action,subscriptionActionsService,token);
        String url = applicationProperties.getErccSubscriptionsUrl();
        List<Subscription> _subscriptionList = new ArrayList<>();
        if (_subscription.isEmpty()) {
            logger.info("EsSubscriptionController : createEsSubscriptionBulk() Method Called : Please create proper request");
            return EsSubscriptionController.generateAllSubscriptionResponse(HttpStatus.OK, false, "No record found", null);
        }
        for (int i = 0; i < _subscription.size(); i++) {
            String unSubscriptionAPIResponse = requestService.makePostRequestWithCustomHeaders(_subscription.get(i) , url);
            Subscription _subscription1 = esSubscriptionService.generateSubscription(_subscription.get(i));
            if (unSubscriptionAPIResponse == null) {
                _subscription1.setStatus("false");
                _subscription1.setResponse_message("Error during api call. Exception");
                _subscription1.setDate_time(currentDate);
            } else {
                if (unSubscriptionAPIResponse.contains("true")) {
                    _subscription1.setStatus("true");
                    _subscription1.setDate_time(currentDate);
                    _subscriptionList.add(_subscription1);
                    _subscription1.setResponse_message(unSubscriptionAPIResponse);
                    esSubscriptionRepository.save(_subscription1);
                } else {
                    _subscription1.setStatus("false");
                    _subscription1.setResponse_message(unSubscriptionAPIResponse);
                    _subscription1.setDate_time(currentDate);
                    esSubscriptionRepository.save(_subscription1);
                    continue;
                }
            }
        }
        logger.info("EsSubscriptionController : createEsSubscriptionBulk() Method Called : Successfully");
        return EsSubscriptionController.generateAllSubscriptionResponse(HttpStatus.OK, true, "All subscription details successfully", _subscriptionList);
    }


    @PostMapping("/subscription/create")
    Subscription createEsSubscription(@RequestBody Subscription _subscription1, @RequestHeader("Authorization") String token) {
        logger.info("EsSubscriptionController : createEsSubscription() Method Called : ");
        SubscriptionActions action = SubscriptionActions.CREATESSUBSCRIPTION;
        esSessionActionService.addSessionActions(action,subscriptionActionsService,token);
        String url = applicationProperties.getErccSubscriptionsUrl();
        String unSubscriptionAPIResponse = requestService.makePostRequestWithCustomHeaders(_subscription1,url);
        Subscription _subscription = esSubscriptionService.generateSubscription(_subscription1);
        if (unSubscriptionAPIResponse == null) {
            _subscription.setStatus("false");
            _subscription.setResponse_message("Error during api call. Exception");
            _subscription.setDate_time(currentDate);
        } else {
            if (unSubscriptionAPIResponse.contains("true")) {
                _subscription.setStatus("true");
                _subscription.setResponse_message(unSubscriptionAPIResponse);
                _subscription.setDate_time(currentDate);

            } else {
                _subscription.setStatus("false");
                _subscription.setResponse_message(unSubscriptionAPIResponse);
                _subscription.setDate_time(currentDate);
            }
        }
        logger.info("EsSubscriptionController : createEsSubscription() Method Called : Successfully ");
        return esSubscriptionRepository.save(_subscription);
    }


    @PutMapping("/subscription/update/{id}")
    Object updateEsSubscription(@RequestHeader("Authorization") String token, @RequestBody Subscription _subscription, @PathVariable("id") Integer id) {
        logger.info("EsSubscriptionController : updateEsSubscription() Method Called : ");
        SubscriptionActions action = SubscriptionActions.UPDATESUBSCRIPTIONBYID;
        esSessionActionService.addSessionActions(action,subscriptionActionsService,token);

        Optional<Subscription> stu = esSubscriptionRepository.findById(id);
        if (stu.isPresent()) {
            logger.info("EsSubscriptionController : updateEsSubscription() Method Called : Successfully");
            Optional<Subscription> s = esSubscriptionService.updateEsSubscription(_subscription);
            return s;
        } else {
            logger.info("EsSubscriptionController : updateEsSubscription() Method Called :Not Update ");
            return null;
        }

    }

    @DeleteMapping("/subscription/delete/{id}")
    Optional<Subscription> deleteEsSubscriptionById(@RequestHeader("Authorization") String token, @PathVariable Integer id) {
        logger.info("EsSubscriptionController : deleteEsSubscriptionById() Method Called : ");
        SubscriptionActions action = SubscriptionActions.DELETESUBSCRIPTIONBYID;
        esSessionActionService.addSessionActions(action,subscriptionActionsService,token);

        Optional<Subscription> es_subscription = esSubscriptionRepository.findById(id);
        if (!es_subscription.isPresent()) {
            logger.info("EsSubscriptionController : deleteEsSubscriptionById() Method Called : Invalid Id");
//            return null;
        } else {
            esSubscriptionService.deleteEsSubscriptionById(id);
            logger.info("EsSubscriptionController : deleteEsSubscriptionById() Method Called : Successfully ");
            return es_subscription;
        }
        return es_subscription;
    }

    public static ResponseEntity<Object> generateAllSubscriptionResponse(HttpStatus status, boolean error, String message, List<Subscription> token) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Properties properties = new Properties();
            properties.put("message", message);
            properties.put("Subscription", token);
            map.put("success", true);
            map.put("response", properties);

            return new ResponseEntity<Object>(map, status);
        } catch (Exception e) {
            map.clear();
            Properties properties = new Properties();
            properties.put("message", message);
            properties.put("Subscription", token);
            map.put("success", false);
            map.put("response", properties);
            return new ResponseEntity<Object>(map, status);
        }
    }


    public static ResponseEntity<Object> generateAllSubscriptionResponseWithCount(HttpStatus status, boolean error, String message, List<Subscription> token, Integer count) {
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
            properties.put("Subscription", token);
            map.put("success", true);
            map.put("total_count", count);
            map.put("pages", pageValue);
            map.put("response", properties);

            return new ResponseEntity<Object>(map, status);
        } catch (Exception e) {
            map.clear();
            Properties properties = new Properties();
            properties.put("message", message);
            properties.put("Subscription", token);
            map.put("success", false);
            map.put("total_count", count);
            map.put("pages", "");
            map.put("response", properties);
            return new ResponseEntity<Object>(map, status);
        }
    }
}
