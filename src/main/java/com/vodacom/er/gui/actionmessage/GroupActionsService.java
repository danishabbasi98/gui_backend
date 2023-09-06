package com.vodacom.er.gui.actionmessage;

import com.vodacom.er.gui.actions.GroupActions;
import org.springframework.stereotype.Service;

@Service
public class GroupActionsService {

    public String performAction(GroupActions groupActions) {
        switch (groupActions) {
            case GROUPBYID:
                return "PERFORMING SPECIFIC GROUP BY ID";
            case ALLSIMPLEGROUPS:
                return "PERFORMING ALL SIMPLE GROUPS";
            case GROUPWITHLISTFUNCTIONS:
                return "PERFORMING GROUP WITH LIST OF FUNCTIONS";
            case ALLGROUPWITHPAGINATION:
                return "PERFORMING ALL GROUPS WITH PAGINATION";
            case ADDESGROUP:
                return "PERFORMING ADD A GROUP";
            case CREATESGROUP:
                return "PERFORMING CREATE A GROUP";
            case CREATESGROUPWITHAFUNCTION:
                return "PERFORMING CREATE A GROUP WITH A FUNCTION";
            case CREATESGROUPWITHFUNCTIONLIST:
                return "PERFORMING CREATE A GROUP WITH FUNCTION LIST";
            case UPDATEGROUPBYID:
                return "PERFORMING UPDATE A GROUP BY ID";
            case DELETEAGROUPBYID:
                return "PERFORMING DELETE A GROUP BY ID";
            default:
                return "Unknown action";
        }
    }
}
