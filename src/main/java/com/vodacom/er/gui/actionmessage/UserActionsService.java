package com.vodacom.er.gui.actionmessage;

import com.vodacom.er.gui.actions.UserActions;
import org.springframework.stereotype.Service;

@Service
public class UserActionsService {

    public String performAction(UserActions userAction) {
        switch (userAction) {
            case LOGIN:
                return "PERFORMING LOGIN ACTION";
            case LOGOUT:
                return "PERFORMING LOGOUT ACTION";
            case SEARCHBYNAME:
                return "SEARCHING BY USERNAME";
            case READALLNORMALUSERWITHGROUPS:
                return "VIEW ALL NORMAL USER RECORD WITH GROUPS LIST";
            case READALLUSERFUNCTION:
                return "VIEW ALL USER RECORD FUNCTION";
            case SEARCHGROUPFUNCTION:
                return "ALL NORMAL USER RECORD WITH GROUP AND ITS FUNCTIONS";
            case SEARCHAUSER:
                return "SEARCH A SPECIFIC USER WITH GROUP ID";
            case USERBYID:
                return "GET A SPECIFIC USER";
            case ALLUSER:
                return "GET ALL USERS";
            case ALLUSERWITHPAGINATION:
                return "ALL USERS WITH PAGINATION";
            case CREATEUSERWITHGROUPLIST:
                return "CREATE USER WITH GROUP LIST";
            case CREATEUSER:
                return "CREATE SIMPLE USER";
            case CREATEUSERWITHSINGLEGROUP:
                return "CREATE USER WITH SINGLE GROUP";
            case UPDATEUSERBYID:
                return "UPDATE SIMPLE USER";
            case UPDATEUSERBYGROUPLIST:
                return "UPDATE USER BY GROUP LIST";
            case DELETEBYUSER:
                return "DELETE USER BY ID";
            case DELETEUSERBYGROUP:
                return "DELETE USER WITH GROUPS";
            default:
                return "Unknown action";
        }
    }
}
