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
@Table(name = "sub_functions")
public class Sub_Functions {
  @Id
  @Column(name="seq")
  @GeneratedValue(strategy = GenerationType.AUTO,generator="native")
  private int seq;

  // One to many relationship with functions
  @ManyToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "function_seq", referencedColumnName = "seq")
  private Functions _functions;

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

  public Sub_Functions(int seq, String name, String description, String active_yn, Date modified_date, String modified_by) {
    this.seq = seq;
    this.name = name;
    this.description = description;
    this.active_yn = active_yn;
    this.modified_date = modified_date;
    this.modified_by = modified_by;
  }
  public Sub_Functions(String name, String description, String active_yn, Date modified_date, String modified_by) {
    this.name = name;
    this.description = description;
    this.active_yn = active_yn;
    this.modified_date = modified_date;
    this.modified_by = modified_by;
  }

  public int getSeq() {
    return seq;
  }

  public void setSeq(int seq) {
    this.seq = seq;
  }

  public Functions get_functions() {
    return _functions;
  }

  public void set_functions(Functions _functions) {
    this._functions = _functions;
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
