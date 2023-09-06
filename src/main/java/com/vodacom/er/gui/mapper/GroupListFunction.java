package com.vodacom.er.gui.mapper;

import com.vodacom.er.gui.entity.Functions;
import com.vodacom.er.gui.entity.Groups;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GroupListFunction {
    private Groups _groups;
    private List<Functions> esFunctions;

    public GroupListFunction() {
    }

    public Groups getEs_groups() {
        return _groups;
    }

    public void setEs_groups(Groups _groups) {
        this._groups = _groups;
    }

    public List<Functions> getEsFunctions() {
        return esFunctions;
    }

    public void setEsFunctions(List<Functions> esFunctions) {
        this.esFunctions = esFunctions;
    }
}
