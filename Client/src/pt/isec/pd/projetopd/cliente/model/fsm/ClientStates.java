package pt.isec.pd.projetopd.cliente.model.fsm;

import pt.isec.pd.projetopd.cliente.model.data.Data;
import pt.isec.pd.projetopd.cliente.model.fsm.states.adminstates.CreateEvent;
import pt.isec.pd.projetopd.cliente.model.fsm.states.adminstates.EditEvent;
import pt.isec.pd.projetopd.cliente.model.fsm.states.adminstates.ViewEventPresences;
import pt.isec.pd.projetopd.cliente.model.fsm.states.adminstates.ViewEvents;
import pt.isec.pd.projetopd.cliente.model.fsm.states.sharedstates.*;

public enum ClientStates {
    INITIAL, REG_USER, LOGIN, SELECT_OPT, EDIT_USER_DATA, REG_PRESENCE, VIEW_PRESENCE, EDIT_EVENT, CREATE_EVENT, VIEW_EVENT_PRESENCE_, VIEW_EVENTS;

    IClientState createState(ClientContext context, Data game) {
        return switch (this) {
            case INITIAL -> new Initial(context,game);
            case REG_USER -> new RegisterUser(context,game);
            case LOGIN -> new Login(context,game);
            case SELECT_OPT -> new SelectOption(context,game);
            case EDIT_USER_DATA -> new EditData(context,game);
            case REG_PRESENCE -> new RegisterPresence(context,game);
            case VIEW_PRESENCE -> new ViewPresence(context,game);
            case EDIT_EVENT -> new EditEvent(context,game);
            case CREATE_EVENT -> new CreateEvent(context,game);
            case VIEW_EVENT_PRESENCE_ -> new ViewEventPresences(context,game);
            case VIEW_EVENTS -> new ViewEvents(context,game);;
        };
    }
}
