package com.vodacom.er.gui.mapper;

import com.vodacom.er.gui.entity.Groups;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class RequestEsUserGroup {

    private int seq;
    private int group_seq;
    private List<Groups> groups;
    private String user_name;
    private String password;
    private Date creation_date;
    private Date last_login_date;
    private int no_of_attempts;
    private String locked_yn;
    private String first_name;
    private String last_name;
    private String contact_number;
    private String role;
    private String email_address;
    private String active_yn;
    private Date modified_date;
    private String modified_by;

    public RequestEsUserGroup() {
    }

    public List<Groups> getGroups() {
        return groups;
    }

    public void setGroups(List<Groups> groups) {
        this.groups = groups;
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getGroup_seq() {
        return group_seq;
    }

    public void setGroup_seq(int group_seq) {
        this.group_seq = group_seq;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public void setLocked_yn(String locked_yn) {
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
}
