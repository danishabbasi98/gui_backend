package com.vodacom.er.gui.mapper;

import org.springframework.stereotype.Component;

@Component
public class RequestSearchEsUser {

    private int group_seq;
    private String name;

    public RequestSearchEsUser() {
    }

    public RequestSearchEsUser(int group_seq, String name) {
        this.group_seq = group_seq;
        this.name = name;
    }

    public int getGroup_seq() {
        return group_seq;
    }

    public void setGroup_seq(int group_seq) {
        this.group_seq = group_seq;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
