package com.vodacom.er.gui.actions;

public enum SubscriptionActions {
    SUBSCRIPTIONBYID("SUBSCRIPTIONBYID"),
    ALLSUBSCRIPTIONWITHPAGINATION("ALLSUBSCRIPTIONWITHPAGINATION"),
    ADDBULKESSUBSCRIPTIONS("ADDBULKESSUBSCRIPTIONS"),
    CREATESSUBSCRIPTION("CREATESSUBSCRIPTION"),
    UPDATESUBSCRIPTIONBYID("UPDATESUBSCRIPTIONBYID"),
    DELETESUBSCRIPTIONBYID("DELETESUBSCRIPTIONBYID");

    private final String action;

    SubscriptionActions(String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}

