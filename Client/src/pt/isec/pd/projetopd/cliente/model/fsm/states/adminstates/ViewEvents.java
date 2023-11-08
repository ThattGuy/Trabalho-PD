package pt.isec.pd.projetopd.cliente.model.fsm.states.adminstates;

import pt.isec.pd.projetopd.cliente.model.data.Data;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.data.communication.Presence;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStateAdapter;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;

import java.io.Serializable;

public class ViewEvents extends ClientStateAdapter {
    protected ViewEvents(ClientContext context, Data data) {
        super(context, data);
    }

    @Override
    public boolean selOpt(OPTIONS opt, String string) {

        switch (opt){
            case SUBMIT -> {

            }
            case BACK -> changeState(context.getLastState());
        }

        return true;
    }

    @Override
    public boolean onMessageReceived(Serializable message) {
        return false;
    }

    @Override
    public ClientStates getState() {
        return ClientStates.VIEW_EVENTS;
    }
}
