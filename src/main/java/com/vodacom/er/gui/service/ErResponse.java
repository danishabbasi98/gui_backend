package com.vodacom.er.gui.service;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "er-response")
public class ErResponse {
    private String id;
    private Payload payload;

    public String getId() {
        return id;
    }

    @XmlElement
    public void setId(String id) {
        this.id = id;
    }

    public Payload getPayload() {
        return payload;
    }

    @XmlElement
    public void setPayload(Payload payload) {
        this.payload = payload;
    }
}

class Payload {
    private InactivateSubscriptionResponse inactivateSubscriptionResponse;

    public InactivateSubscriptionResponse getInactivateSubscriptionResponse() {
        return inactivateSubscriptionResponse;
    }

    @XmlElement(name = "inactivate-subscription-response")
    public void setInactivateSubscriptionResponse(InactivateSubscriptionResponse inactivateSubscriptionResponse) {
        this.inactivateSubscriptionResponse = inactivateSubscriptionResponse;
    }
}

class InactivateSubscriptionResponse {
    private String success;

    public String getSuccess() {
        return success;
    }

    @XmlElement
    public void setSuccess(String success) {
        this.success = success;
    }
}

