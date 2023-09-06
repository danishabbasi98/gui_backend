package com.vodacom.er.gui.mapper;

import com.vodacom.er.gui.entity.Functions;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component
public class UpdateRequestEsGroupFunction {

    private int seq;
    private List<Functions> _functions;
    private String name;
    private String role;
    private String description;
    private String active_yn;
    private Date modified_date;
    private String modified_by;


    public UpdateRequestEsGroupFunction() {
    }

    public int getSeq() {
        return seq;
    }

    public void setSeq(int seq) {
        this.seq = seq;
    }

    public List<Functions> getEs_functions() {
        return _functions;
    }

    public void setEs_functions(List<Functions> _functions) {
        this._functions = _functions;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
