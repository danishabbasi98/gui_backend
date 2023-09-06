package com.vodacom.er.gui.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Data
@AllArgsConstructor
//@NoArgsConstructor
@Entity
@Table(name = "session_actions")
public class Session_Actions {
    @Id
    @Column(name = "seq")
    @GeneratedValue(strategy = GenerationType.IDENTITY,generator="native")
    private int seq;
    @Column(name = "session_seq")
    private String session_seq;
    @Column(name = "action_id")
    private String action_id;
    @Column(name = "action_details")
    private String action_details;
    @Column(name = "user_id")
    private Integer user_id;
    @Column(name = "start_date")
    private Date start_date;
    @Column(name = "end_date")
    private Date end_date;
    @Column(name = "status")
    private String status;
    @Column(name = "failure_message")
    private String failure_message;
    @Column(name = "modified_date")
    private Date modified_date;

  public Session_Actions() {
  }

  public Session_Actions(String session_seq, String action_id, String action_details, Integer user_id, Date start_date, Date end_date, String status, String failure_message, Date modified_date) {
    this.session_seq = session_seq;
    this.action_id = action_id;
    this.action_details = action_details;
    this.user_id = user_id;
    this.start_date = start_date;
    this.end_date = end_date;
    this.status = status;
    this.failure_message = failure_message;
    this.modified_date = modified_date;
  }

  public Session_Actions(String action_id, String action_details, Integer user_id, Date start_date, Date end_date, String status, String failure_message, Date modified_date) {
    this.action_id = action_id;
    this.action_details = action_details;
    this.user_id = user_id;
    this.start_date = start_date;
    this.end_date = end_date;
    this.status = status;
    this.failure_message = failure_message;
    this.modified_date = modified_date;
  }

  public int getSeq() {
    return seq;
  }

  public void setSeq(int seq) {
    this.seq = seq;
  }

  public String getSession_seq() {
    return session_seq;
  }

  public void setSession_seq(String session_seq) {
    this.session_seq = session_seq;
  }

  public String getAction_id() {
    return action_id;
  }

  public void setAction_id(String action_id) {
    this.action_id = action_id;
  }

  public String getAction_details() {
    return action_details;
  }

  public void setAction_details(String action_details) {
    this.action_details = action_details;
  }

  public Integer getUser_id() {
    return user_id;
  }

  public void setUser_id(Integer user_id) {
    this.user_id = user_id;
  }

  public Date getStart_date() {
    return start_date;
  }

  public void setStart_date(Date start_date) {
    this.start_date = start_date;
  }

  public Date getEnd_date() {
    return end_date;
  }

  public void setEnd_date(Date end_date) {
    this.end_date = end_date;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getFailure_message() {
    return failure_message;
  }

  public void setFailure_message(String failure_message) {
    this.failure_message = failure_message;
  }

  public Date getModified_date() {
    return modified_date;
  }

  public void setModified_date(Date modified_date) {
    this.modified_date = modified_date;
  }
}

