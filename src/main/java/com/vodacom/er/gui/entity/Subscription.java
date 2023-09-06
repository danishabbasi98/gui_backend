package com.vodacom.er.gui.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;


//@Getter
//@Setter
@AllArgsConstructor
//@NoArgsConstructor
@Entity
@Table(name = "subscription")
public class Subscription {
    @Id
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    private int seq;

    @Column(name = "msisdn")
    private String msisdn;
    @Column(name = "subscription_id")
    private String subscription_id;
    @Column(name = "csr_id")
    private String csr_id;
    @Column(name = "reason")
    private String reason;
    @Column(name = "status")
    private String status;
    @Column(name = "unsubscribed_by")
    private String unsubscribed_by;
    @Column(name="response_message",length = 1000)
    private String response_message;
    @Column(name = "date_time")
    private Date date_time;

    public Subscription() {
    }

    public Subscription(String msisdn, String subscription_id, String csr_id, String reason, String status, String unsubscribed_by, String response_message, Date date_time) {
        this.msisdn = msisdn;
        this.subscription_id = subscription_id;
        this.csr_id = csr_id;
        this.reason = reason;
        this.status = status;
        this.unsubscribed_by = unsubscribed_by;
        this.response_message = response_message;
        this.date_time = date_time;
    }

    public int getSEQ() {
        return seq;
    }

    public void setSEQ(int seq) {
        this.seq = seq;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getSubscription_id() {
        return subscription_id;
    }

    public void setSubscription_id(String subscription_id) {
        this.subscription_id = subscription_id;
    }

    public String getCsr_id() {
        return csr_id;
    }

    public void setCsr_id(String csr_id) {
        this.csr_id = csr_id;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUnsubscribed_by() {
        return unsubscribed_by;
    }

    public void setUnsubscribed_by(String unsubscribed_by) {
        this.unsubscribed_by = unsubscribed_by;
    }

    public String getResponse_message() {
        return response_message;
    }

    public void setResponse_message(String response_message) {
        this.response_message = response_message;
    }

    public Date getDate_time() {
        return date_time;
    }

    public void setDate_time(Date date_time) {
        this.date_time = date_time;
    }
}