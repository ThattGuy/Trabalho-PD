package pt.isec.pa.projetopd.cliente.fsm;

import pt.isec.pa.projetopd.cliente.data.ClientData;
import pt.isec.pa.projetopd.cliente.fsm.states.Initial;

public enum ClientStates {
    INITIAL, REG_USER, LOGIN, SELECT_OPT, EDIT_USER_DATA, REG_PRESENCE, VIEW_PRESENCE;

    IClientState createState(ClientContext context, ClientData game) {
        return switch (this) {
            case INITIAL -> new Initial(context,game);
            case REG_USER -> null;
            case LOGIN -> null;
            case SELECT_OPT -> null;
            case EDIT_USER_DATA -> null;
            case REG_PRESENCE -> null;
            case VIEW_PRESENCE -> null;
        };
    }
}
