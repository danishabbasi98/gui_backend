package com.vodacom.er.gui.actions;

public enum NormalUserActions {
    SESSIONWITHFUNCTION("SESSIONWITHFUNCTION");

    private final String action;

    NormalUserActions(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}

