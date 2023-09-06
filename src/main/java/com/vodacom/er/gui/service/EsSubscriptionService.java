package com.vodacom.er.gui.service;

import com.vodacom.er.gui.entity.Subscription;
import com.vodacom.er.gui.interceptor.LoggingInterceptor;
import com.vodacom.er.gui.repository.EsSubscriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class EsSubscriptionService {
    @Autowired
    private EsSubscriptionRepository esSubscriptionRepository;

    Date currentDate = new Date();

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    public Subscription saveSubscription(Subscription subscription) {
        logger.info("EsSubscriptionService : saveSubscription() Method Called : Successfully ");
         return esSubscriptionRepository.save(subscription);
    }

    public Subscription generateSubscription(Subscription subscription) {
        logger.info("EsSubscriptionService : generateSubscription() Method Called : ");
        Subscription _subscription = new Subscription();
        _subscription.setMsisdn(subscription.getMsisdn());
        _subscription.setCsr_id(subscription.getCsr_id());
        _subscription.setSubscription_id(subscription.getSubscription_id());
        _subscription.setResponse_message("");
        _subscription.setStatus("");
        _subscription.setUnsubscribed_by(subscription.getUnsubscribed_by());
        _subscription.setReason(subscription.getReason());
        _subscription.setDate_time(currentDate);
        logger.info("EsSubscriptionService : generateSubscription() Method Called : Successfully ");
        return _subscription;
    }

    public List<Subscription> allSubscription() {
        logger.info("EsSubscriptionService : allSubscription() Method Called : Successfully ");
        return esSubscriptionRepository.findAll();
    }

    public Optional<Subscription> getSubscriptionById(Integer id) {
        logger.info("EsSubscriptionService : getSubscriptionById() Method Called : Successfully ");
        return esSubscriptionRepository.findById(id);
    }
    public Optional<Subscription> getActualSubscriptionById(Integer id) {
        logger.info("EsSubscriptionService : getActualSubscriptionById() Method Called : Successfully ");
        return esSubscriptionRepository.findById(id);
    }


    public Optional<Subscription> updateEsSubscription(Subscription subscription) {
        logger.info("EsSubscriptionService : updateEsSubscription() Method Called :  ");
        Optional<Subscription> stu = esSubscriptionRepository.findById(subscription.getSEQ());
        Subscription s = stu.get();
        esSubscriptionRepository.save(s);
        logger.info("EsSubscriptionService : updateEsSubscription() Method Called : Successfully ");
        return esSubscriptionRepository.findById(subscription.getSEQ());
    }

    public Optional<Subscription> deleteEsSubscriptionById(Integer id) {
        logger.info("EsSubscriptionService : deleteEsSubscriptionById() Method Called :  ");
        Optional<Subscription> es_subscription = esSubscriptionRepository.findById(id);
        if(!es_subscription.isPresent()){
            logger.info("EsSubscriptionService : deleteEsSubscriptionById() Method Called : Invalid Id ");
        }
        esSubscriptionRepository.deleteById(id);
        logger.info("EsSubscriptionService : deleteEsSubscriptionById() Method Called : Successfully ");
        return es_subscription;
    }

}
