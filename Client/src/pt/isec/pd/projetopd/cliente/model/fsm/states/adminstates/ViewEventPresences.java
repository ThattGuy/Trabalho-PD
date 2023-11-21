package pt.isec.pd.projetopd.cliente.model.fsm.states.adminstates;

import pt.isec.pd.projetopd.cliente.model.data.Data;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStateAdapter;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;

public class ViewEventPresences extends ClientStateAdapter {
    public ViewEventPresences(ClientContext context, Data data) {
        super(context, data);
        //todo enviar evento
    }

    @Override
    public boolean selOpt(OPTIONS opt, String string) {

        switch (opt){
            case BACK -> changeState(ClientStates.SELECT_OPT_ADMIN);
        }

        return true;
    }

    @Override
    public synchronized boolean onMessageReceived(Object message) {
        return false;
    }

    @Override
    public ClientStates getState() {
        return ClientStates.VIEW_EVENT_PRESENCE;
    }
}