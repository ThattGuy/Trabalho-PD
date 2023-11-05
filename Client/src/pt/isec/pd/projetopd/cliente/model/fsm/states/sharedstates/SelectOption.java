package pt.isec.pd.projetopd.cliente.model.fsm.states.sharedstates;

import pt.isec.pd.projetopd.cliente.model.data.Data;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStateAdapter;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;

import java.io.Serializable;

public class SelectOption extends ClientStateAdapter {
    public SelectOption(ClientContext context, Data data) {
        super(context, data);
    }

    @Override
    public boolean selOpt(OPTIONS opt, String string) {

        switch (opt){
            case EDIT_DATA -> changeState(ClientStates.EDIT_USER_DATA);
            case REG_PRESENCE -> changeState(ClientStates.REG_PRESENCE);
            case VIEW_PRESENCE -> changeState(ClientStates.VIEW_PRESENCE);
            case LOGOUT -> changeState(ClientStates.INITIAL);//todo
        }

        return true;
    }

    @Override
    public boolean onMessageReceived(Serializable message) {
        return false;
    }

}
