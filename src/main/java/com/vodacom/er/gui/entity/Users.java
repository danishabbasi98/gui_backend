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
@Table(name = "users")
public class Users {
    @Id
    @Column(name = "user_seq")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    private int user_seq;

    @JsonIgnore
    @ManyToMany(mappedBy = "es_user_groups")
    private Set<Groups> groups = new HashSet<>();


    //  @Column(name="user_name")
    @Column(name = "user_name", unique = false)
    private String user_name;

    @Column(name = "password")
    private String password;

    @Column(name = "creation_date")
    private Date creation_date;
    @Column(name = "last_login_date")
    private Date last_login_date;
    @Column(name = "no_of_attempts")
    private int no_of_attempts;
    @Column(name = "locked_yn")
    private String locked_yn;
    @Column(name = "first_name")
    private String first_name;
    @Column(name = "last_name")
    private String last_name;
    @Column(name = "contact_number")
    private String contact_number;
    @Column(name = "role")
    private String role;
    @Column(name = "email_address")
    private String email_address;
    @Column(name = "active_yn")
    private String active_yn;
    @Column(name = "modified_date")
    private Date modified_date;
    @Column(name = "modified_by")
    private String modified_by;

    public Users(int user_seq,String password, String user_name,  Date creation_date, Date last_login_date, int no_of_attempts, String locked_yn, String first_name, String last_name, String contact_number, String role, String email_address, String active_yn, Date modified_date, String modified_by) {
        this.user_seq = user_seq;
        this.user_name = user_name;
        this.password = password;
        this.creation_date = creation_date;
        this.last_login_date = last_login_date;
        this.no_of_attempts = no_of_attempts;
        this.locked_yn = locked_yn;
        this.first_name = first_name;
        this.last_name = last_name;
        this.contact_number = contact_number;
        this.role = role;
        this.email_address = email_address;
        this.active_yn = active_yn;
        this.modified_date = modified_date;
        this.modified_by = modified_by;
    }

    public Users(String user_name, String password, Date creation_date, Date last_login_date, int no_of_attempts, String locked_yn, String first_name, String last_name, String contact_number, String role, String email_address, String active_yn, Date modified_date, String modified_by) {
        this.user_name = user_name;
        this.password = password;
        this.creation_date = creation_date;
        this.last_login_date = last_login_date;
        this.no_of_attempts = no_of_attempts;
        this.locked_yn = locked_yn;
        this.first_name = first_name;
        this.last_name = last_name;
        this.contact_number = contact_number;
        this.role = role;
        this.email_address = email_address;
        this.active_yn = active_yn;
        this.modified_date = modified_date;
        this.modified_by = modified_by;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setLocked_yn(String locked_yn) {
        this.locked_yn = locked_yn;
    }

    public int getSeq() {
        return user_seq;
    }

    public void setSeq(int user_seq) {
        this.user_seq = user_seq;
    }

    public Set<Groups> getGroups() {
        return groups;
    }

    public void setGroups(Set<Groups> groups) {
        this.groups = groups;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public Date getCreation_date() {
        return creation_date;
    }

    public void setCreation_date(Date creation_date) {
        this.creation_date = creation_date;
    }

    public Date getLast_login_date() {
        return last_login_date;
    }

    public void setLast_login_date(Date last_login_date) {
        this.last_login_date = last_login_date;
    }

    public int getNo_of_attempts() {
        return no_of_attempts;
    }

    public void setNo_of_attempts(int no_of_attempts) {
        this.no_of_attempts = no_of_attempts;
    }

    public String getLocked_yn() {
        return locked_yn;
    }

    public void setLocke_yn(String locked_yn) {
        this.locked_yn = locked_yn;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getContact_number() {
        return contact_number;
    }

    public void setContact_number(String contact_number) {
        this.contact_number = contact_number;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail_address() {
        return email_address;
    }

    public void setEmail_address(String email_address) {
        this.email_address = email_address;
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

    @Override
    public String toString() {
        return "Users{" + "user_seq=" + user_seq + ", groups=" + groups + ", user_name='" + user_name + '\''  + ", creation_date=" + creation_date + ", last_login_date=" + last_login_date + ", no_of_attempts=" + no_of_attempts + ", locked_yn='" + locked_yn + '\'' + ", first_name='" + first_name + '\'' + ", last_name='" + last_name + '\'' + ", contact_number='" + contact_number + '\'' + ", role='" + role + '\'' + ", email_address='" + email_address + '\'' + ", active_yn='" + active_yn + '\'' + ", modified_date=" + modified_date + ", modified_by='" + modified_by + '\'' + '}';
    }
    //  @Override
//  public String toString() {
//    return "Student{" + "seq=" + seq + ", first_name='" + first_name + '\'' + ", groups=" + groups + '}';
//  }
}