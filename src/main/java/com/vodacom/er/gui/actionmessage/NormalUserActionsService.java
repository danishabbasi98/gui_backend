package com.vodacom.er.gui.actionmessage;

import com.vodacom.er.gui.actions.NormalUserActions;
import org.springframework.stereotype.Service;

@Service
public class NormalUserActionsService {

    public String performAction(NormalUserActions normalUserActions) {
        switch (normalUserActions) {
            case SESSIONWITHFUNCTION:
                return "PERFORMING SPECIFIC APPLICATION ID : ";
            default:
                return "Unknown action";
        }
    }
}
