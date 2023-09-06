package com.vodacom.er.gui.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@AllArgsConstructor
//@NoArgsConstructor
@Entity
@Table(name = "user_groups")
public class User_Groups {

    @Id
    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.AUTO,generator="native")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "user_seq")
    private int user_seq;
    @Column(name = "group_seq")
    private int group_seq;
    @Column(name = "modified_date")
    private Date modified_date;
    @Column(name = "modified_by")
    private String modified_by;

    public User_Groups() {
    }

  public User_Groups(int user_seq, int group_seq, Date modified_date, String modified_by) {
    this.user_seq = user_seq;
    this.group_seq = group_seq;
    this.modified_date = modified_date;
    this.modified_by = modified_by;
  }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSeq() {
        return user_seq;
    }

    public void setSeq(int seq) {
        this.user_seq = user_seq;
    }

    public int getGroup_seq() {
        return group_seq;
    }

    public void setGroup_seq(int group_seq) {
        this.group_seq = group_seq;
    }

    public Date getModified_date() {
        return modified_date;
    }

    public void setModified_date(Date modified_date) {
        this.modified_date = modified_date;
    }

    public String getModified_by() {
        return modified_by;
    }

    public void setModified_by(String modified_by) {
        this.modified_by = modified_by;
    }
}
