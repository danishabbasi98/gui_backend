package com.vodacom.er.gui.actions;

public enum FunctionActions {
    FUNCTIONBYID("FUNCTIONBYID"),
    ALLSIMPLEFUNCTIONS("ALLSIMPLEFUNCTIONS"),
    SEARCHBYNAME("SEARCHBYNAME"),
    ALLFUNCTIONWITHPAGINATION("ALLFUNCTIONWITHPAGINATION"),
    CREATESFUNCTION("CREATESFUNCTION"),
    UPDATEFUNCTION("UPDATEFUNCTION"),
    DELETEAFUNCTIONBYID("DELETEAFUNCTIONBYID");

    private final String action;

    FunctionActions(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}

