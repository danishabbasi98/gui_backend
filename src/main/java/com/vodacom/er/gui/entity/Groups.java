package com.vodacom.er.gui.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "groups")
public class Groups {
  @Id
  @Column(name="group_seq")
  @GeneratedValue(strategy = GenerationType.AUTO,generator="native")
  private int group_seq;

  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(
          name="users_groups",
          joinColumns = @JoinColumn( name = "user_seq"),
          inverseJoinColumns = @JoinColumn( name = "group_seq")
  )
  private Set<Users> es_user_groups = new HashSet<>();

  @JsonIgnore
  @ManyToMany(mappedBy = "groups_functions")
  private Set<Functions> es_group_functions = new HashSet<>();

  @Column(name="name")
  private String name;
  @Column(name="description")
  private String description;
  @Column(name="active_yn")
  private String active_yn;
  @Column(name="modified_date")
  private Date modified_date;
  @Column(name="modified_by")
  private String modified_by;

  public Groups(int group_seq, String name, String description, String active_yn, Date modified_date, String modified_by) {
    this.group_seq = group_seq;
    this.name = name;
    this.description = description;
    this.active_yn = active_yn;
    this.modified_date = modified_date;
    this.modified_by = modified_by;
  }

  public Groups(String name, String description, String active_yn, Date modified_date, String modified_by) {
    this.name = name;
    this.description = description;
    this.active_yn = active_yn;
    this.modified_date = modified_date;
    this.modified_by = modified_by;
  }

  public int getSeq() {
    return group_seq;
  }

  public void setSeq(int group_seq) {
    this.group_seq = group_seq;
  }

  public int getGroup_seq() {
    return group_seq;
  }

  public void setGroup_seq(int group_seq) {
    this.group_seq = group_seq;
  }

  public Set<Users> getUser_groups() {
    return es_user_groups;
  }

  public void setUser_groups(Set<Users> es_user_groups) {
    this.es_user_groups = es_user_groups;
  }

  public Set<Functions> getEs_group_functions() {
    return es_group_functions;
  }

  public void setEs_group_functions(Set<Functions> es_group_functions) {
    this.es_group_functions = es_group_functions;
  }
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
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

  public String getModified_by() {
    return modified_by;
  }

  public void setModified_by(String modified_by) {
    this.modified_by = modified_by;
  }

//  @Override
//  public String toString() {
//    return "Groups{" + "seq=" + seq + ", es_user_groups=" + es_user_groups + ", es_group_functions=" + es_group_functions + ", name='" + name + '\'' + ", active_yn='" + active_yn + '\'' + ", modified_date=" + modified_date + ", modified_by='" + modified_by + '\'' + '}';
//  }
  //  @Override
//  public String toString() {
//    return "Groups{" + "seq=" + seq + ", es_user_groups=" + es_user_groups + ", name='" + name +  '}';
//  }
}


