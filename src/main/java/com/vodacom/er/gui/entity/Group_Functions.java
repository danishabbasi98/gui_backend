package com.vodacom.er.gui.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;


@Data
@AllArgsConstructor
//@NoArgsConstructor
@Entity
@Table(name = "group_functions")
public class Group_Functions {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "group_seq")
    private int group_seq;

    @Column(name = "function_seq")
    private int function_seq;
    @Column(name = "modified_date")
    private Date modified_date;
    @Column(name = "modified_by")
    private String modified_by;

    public Group_Functions() {
    }

    public Group_Functions(int function_seq, Date modified_date, String modified_by) {
        this.function_seq = function_seq;
        this.modified_date = modified_date;
        this.modified_by = modified_by;
    }

    public Group_Functions(int group_seq, int function_seq, Date modified_date, String modified_by) {
        this.group_seq = group_seq;
        this.function_seq = function_seq;
        this.modified_date = modified_date;
        this.modified_by = modified_by;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGroup_seq() {
        return group_seq;
    }

    public void setGroup_seq(int group_seq) {
        this.group_seq = group_seq;
    }

    public int getFunction_seq() {
        return function_seq;
    }

    public void setFunction_seq(int function_seq) {
        this.function_seq = function_seq;
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
