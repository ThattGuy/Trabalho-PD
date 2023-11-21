package pt.isec.pd.projetopd.cliente.model.fsm;

import pt.isec.pd.projetopd.cliente.model.data.Data;
import pt.isec.pd.projetopd.cliente.model.fsm.states.adminstates.*;
import pt.isec.pd.projetopd.cliente.model.fsm.states.sharedstates.*;
import pt.isec.pd.projetopd.cliente.model.fsm.states.userStates.RegisterPresence;
import pt.isec.pd.projetopd.cliente.model.fsm.states.userStates.SelectOptionUser;
import pt.isec.pd.projetopd.cliente.model.fsm.states.userStates.ViewPresence;

public enum ClientStates {
    INITIAL, REG_USER, LOGIN, SELECT_OPT, EDIT_USER_DATA, REG_PRESENCE, VIEW_PRESENCE, EDIT_EVENT, CREATE_EVENT, VIEW_EVENT_PRESENCE, VIEW_EVENTS, SELECT_OPT_ADMIN;

    IClientState createState(ClientContext context, Data data) {
        return switch (this) {
            case INITIAL -> new Initial(context,data);
            case REG_USER -> new RegisterUser(context,data);
            case LOGIN -> new Login(context,data);
            case SELECT_OPT -> new SelectOptionUser(context,data);
            case EDIT_USER_DATA -> new EditInfo(context,data);
            case REG_PRESENCE -> new RegisterPresence(context,data);
            case VIEW_PRESENCE -> new ViewPresence(context,data);
            case EDIT_EVENT -> new EditEvent(context,data);
            case CREATE_EVENT -> new CreateEvent(context,data);
            case VIEW_EVENT_PRESENCE -> new ViewEventPresences(context,data);
            case VIEW_EVENTS -> new ViewEvents(context,data);
            case SELECT_OPT_ADMIN -> new SelectOptionAdmin(context,data);
        };
    }
}
