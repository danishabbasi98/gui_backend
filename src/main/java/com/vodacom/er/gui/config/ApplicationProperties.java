package com.vodacom.er.gui.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationProperties {


    @Value("${com.vodacom.subscriptions}")
    private String erccSubUrl;

    public String getErccSubscriptionsUrl() {
        return erccSubUrl;
    }

}