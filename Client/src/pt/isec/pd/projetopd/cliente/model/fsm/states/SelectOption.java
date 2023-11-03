package pt.isec.pd.projetopd.cliente.model.fsm.states;

import pt.isec.pd.projetopd.cliente.model.data.ClientData;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStateAdapter;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;

public class SelectOption extends ClientStateAdapter {
    public SelectOption(ClientContext context, ClientData data) {
        super(context, data);
    }

    @Override
    public boolean selOpt(OPTIONS opt, String string) {

        switch (opt){
            case EDIT_DATA -> changeState(ClientStates.EDIT_USER_DATA);
            case REG_PRESENCE -> changeState(ClientStates.REG_PRESENCE);
            case VIEW_PRESENCE -> changeState(ClientStates.VIEW_PRESENCE);
        }

        return true;
    }

    @Override
    public boolean receiveMesage(String msg) {
        return false;
    }

}
