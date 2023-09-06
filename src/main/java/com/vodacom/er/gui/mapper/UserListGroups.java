package com.vodacom.er.gui.mapper;

import com.vodacom.er.gui.entity.Groups;
import com.vodacom.er.gui.entity.Users;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserListGroups {
    private Users _users;
    private List<Groups> _groups;

    public UserListGroups() {
    }

    public Users getEs_users() {
        return _users;
    }

    public void setEs_users(Users _users) {
        this._users = _users;
    }

    public List<Groups> getEs_groups() {
        return _groups;
    }

    public void setEs_groups(List<Groups> _groups) {
        this._groups = _groups;
    }
}
