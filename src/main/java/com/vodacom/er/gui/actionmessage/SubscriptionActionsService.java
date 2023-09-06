package com.vodacom.er.gui.actionmessage;

import com.vodacom.er.gui.actions.SubscriptionActions;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionActionsService {

    public String performAction(SubscriptionActions subscriptionActions) {
        switch (subscriptionActions) {
            case SUBSCRIPTIONBYID:
                return "PERFORMING SPECIFIC SUBSCRIPTION BY ID";
            case ALLSUBSCRIPTIONWITHPAGINATION:
                return "PERFORMING ALL SUBSCRIPTION WITH PAGINATION";
            case ADDBULKESSUBSCRIPTIONS:
                return "PERFORMING ADD BULK INSERTION SUBSCRIPTIONS";
            case CREATESSUBSCRIPTION:
                return "PERFORMING CREATE A SUBSCRIPTION";
            case UPDATESUBSCRIPTIONBYID:
                return "PERFORMING UPDATE A SUBSCRIPTION BY ID";
            case DELETESUBSCRIPTIONBYID:
                return "PERFORMING DELETE A SUBSCRIPTION BY ID";
            default:
                return "Unknown action";
        }
    }
}
