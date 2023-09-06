package com.vodacom.er.gui.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "sessions")
public class Sessions {
  @Id
  @Column(name="seq")
  @GeneratedValue(strategy = GenerationType.IDENTITY,generator="native")
  private int seq;
  @Column(name="user_seq")
  private int user_seq;
  @Column(name="session_id",unique = true)
  private String session_id;
  @Column(name="start_date")
  private Date start_date;
  @Column(name="end_date")
  private Date end_date;
  @Column(name="end_reason")
  private String end_reason;
  @Column(name="active_yn")
  private String active_yn;
  @Column(name="modified_date")
  private Date modified_date;

  public Sessions(int user_seq, String session_id, Date start_date, Date end_date, String end_reason, String active_yn, Date modified_date) {

    this.session_id = session_id;
    this.start_date = start_date;
    this.end_date = end_date;
    this.end_reason = end_reason;
    this.active_yn = active_yn;
    this.modified_date = modified_date;
  }

  public int getSeq() {
    return seq;
  }

  public void setSeq(int seq) {
    this.seq = seq;
  }

  public int getUser_seq() {
    return user_seq;
  }

  public void setUser_seq(int user_seq) {
    this.user_seq = user_seq;
  }

  public String getSession_id() {
    return session_id;
  }

  public void setSession_id(String session_id) {
    this.session_id = session_id;
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

  public String getEnd_reason() {
    return end_reason;
  }

  public void setEnd_reason(String end_reason) {
    this.end_reason = end_reason;
  }

  public String getActive_yn() {
    return active_yn;
  }

  public void setActive_yn(String active_yn) {
    this.active_yn = active_yn;
  }

  public Date getModified_date() {
    return modified_date;
  }

  public void setModified_date(Date modified_date) {
    this.modified_date = modified_date;
  }

}

