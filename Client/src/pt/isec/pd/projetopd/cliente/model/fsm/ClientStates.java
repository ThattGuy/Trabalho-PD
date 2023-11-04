package pt.isec.pd.projetopd.cliente.model.fsm;

import pt.isec.pd.projetopd.cliente.model.data.ClientData;
import pt.isec.pd.projetopd.cliente.model.fsm.states.*;

public enum ClientStates {
    INITIAL, REG_USER, LOGIN, SELECT_OPT, EDIT_USER_DATA, REG_PRESENCE, VIEW_PRESENCE;

    IClientState createState(ClientContext context, ClientData game) {
        return switch (this) {
            case INITIAL -> new Initial(context,game);
            case REG_USER -> new RegisterUser(context,game);
            case LOGIN -> new Login(context,game);
            case SELECT_OPT -> new SelectOption(context,game);
            case EDIT_USER_DATA -> new EditData(context,game);
            case REG_PRESENCE -> new RegisterPresence(context,game);
            case VIEW_PRESENCE -> new ViewPresence(context,game);
        };
    }
}
