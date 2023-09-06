package com.vodacom.er.gui.mapper;

import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class RequestEsGroupFunction {

    private int seq;
    private int function_seq;
    private String name;
    private String description;
    private String active_yn;
    private Date modified_date;
    private String modified_by;


    public RequestEsGroupFunction() {
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public int getFunction_seq() {
        return function_seq;
    }

    public void setFunction_seq(int function_seq) {
        this.function_seq = function_seq;
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
