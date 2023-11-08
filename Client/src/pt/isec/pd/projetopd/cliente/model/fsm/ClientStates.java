package pt.isec.pd.projetopd.cliente.model.fsm;

import pt.isec.pd.projetopd.cliente.model.data.Data;
import pt.isec.pd.projetopd.cliente.model.fsm.states.adminstates.*;
import pt.isec.pd.projetopd.cliente.model.fsm.states.sharedstates.*;

public enum ClientStates {
    INITIAL, REG_USER, LOGIN, SELECT_OPT, EDIT_USER_DATA, REG_PRESENCE, VIEW_PRESENCE, EDIT_EVENT, CREATE_EVENT, VIEW_EVENT_PRESENCE_, VIEW_EVENTS, SELECT_OPT_ADMIN;

    IClientState createState(ClientContext context, Data data) {
        return switch (this) {
            case INITIAL -> new Initial(context,data);
            case REG_USER -> new RegisterUser(context,data);
            case LOGIN -> new Login(context,data);
            case SELECT_OPT -> new SelectOptionUser(context,data);
            case EDIT_USER_DATA -> new EditData(context,data);
            case REG_PRESENCE -> new RegisterPresence(context,data);
            case VIEW_PRESENCE -> new ViewPresence(context,data);
            case EDIT_EVENT -> new EditEvent(context,data);
            case CREATE_EVENT -> new CreateEvent(context,data);
            case VIEW_EVENT_PRESENCE_ -> new ViewEventPresences(context,data);
            case VIEW_EVENTS -> new ViewEvents(context,data);
            case SELECT_OPT_ADMIN -> new SelectOptionAdmin(context,data);
        };
    }
}
