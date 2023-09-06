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
@Table(name = "functions")
public class Functions {
  @Id
  @Column(name="seq")
  @GeneratedValue(strategy = GenerationType.AUTO,generator="native")
  private int seq;

//  @ManyToMany
//  @JoinTable(
//      name="ES_GROUP_FUNCTIONS",
//      joinColumns = @JoinColumn( name = "seq"),
//      inverseJoinColumns = @JoinColumn( name = "seq")
//  )
//  private Set<Groups> ES_GROUP_FUNCTIONS = new HashSet<>();
  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(
      name="groups_functions",
      joinColumns = @JoinColumn( name = "group_seq"),
      inverseJoinColumns = @JoinColumn( name = "function_seq")
  )
  private Set<Groups> groups_functions = new HashSet<>();

  // One to many relationship with functions
  @JsonIgnore
  @OneToMany(mappedBy = "_functions")
  private Set<Sub_Functions> _sub_functions = new HashSet<>();

//  @JsonIgnore
//  @ManyToMany(mappedBy = "ES_SUB_FUNCTIONS")
//  private Set<Sub_Functions> _sub_functions = new HashSet<>();

  @Column(name="name")
  private String name;
  @Column(name="link")
  private String link;
  @Column(name="description")
  private String description;
  @Column(name="active_yn")
  private String active_yn;
  @Column(name="modified_date")
  private Date modified_date;
  @Column(name="modified_by")
  private String modified_by;

  public Functions(String name, String link , String description, String active_yn, Date modified_date, String modified_by) {
    this.name = name;
    this.link = link;
    this.description = description;
    this.active_yn = active_yn;
    this.modified_date = modified_date;
    this.modified_by = modified_by;
  }

  public Functions(int seq, String name, String link, String description, String active_yn, Date modified_date, String modified_by) {
    this.seq = seq;
    this.name = name;
    this.link = link;
    this.description = description;
    this.active_yn = active_yn;
    this.modified_date = modified_date;
    this.modified_by = modified_by;
  }

  public String getLink() {
    return link;
  }

  public void setLink(String link) {
    this.link = link;
  }

  public int getSeq() {
    return seq;
  }

  public void setSeq(int seq) {
    this.seq = seq;
  }

  public Set<Groups> getES_GROUP_FUNCTIONS() {
    return groups_functions;
  }

  public void setES_GROUP_FUNCTIONS(Set<Groups> ES_GROUP_FUNCTIONS) {
    this.groups_functions = ES_GROUP_FUNCTIONS;
  }

  public Set<Sub_Functions> get_sub_functions() {
    return _sub_functions;
  }

  public void set_sub_functions(Set<Sub_Functions> _sub_functions) {
    this._sub_functions = _sub_functions;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
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

}
