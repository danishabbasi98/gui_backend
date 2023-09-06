package com.vodacom.er.gui.actionmessage;

import com.vodacom.er.gui.actions.FunctionActions;
import org.springframework.stereotype.Service;

@Service
public class FunctionActionsService {

    public String performAction(FunctionActions functionActions) {
        switch (functionActions) {
            case FUNCTIONBYID:
                return "PERFORMING SPECIFIC FUNCTION BY ID";
            case ALLSIMPLEFUNCTIONS:
                return "PERFORMING ALL SIMPLE FUNCTIONS";
            case SEARCHBYNAME:
                return "PERFORMING SEARCH FUNCTION BY NAME";
            case ALLFUNCTIONWITHPAGINATION:
                return "PERFORMING ALL FUNCTIONS WITH PAGINATION";
            case CREATESFUNCTION:
                return "PERFORMING CREATE A FUNCTION";
            case UPDATEFUNCTION:
                return "PERFORMING UPDATE A FUNCTION";
            case DELETEAFUNCTIONBYID:
                return "PERFORMING DELETE A FUNCTION BY ID";
            default:
                return "Unknown action";
        }
    }
}
