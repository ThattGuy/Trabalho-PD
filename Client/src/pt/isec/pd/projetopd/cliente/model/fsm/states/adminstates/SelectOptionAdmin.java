package pt.isec.pd.projetopd.cliente.model.fsm.states.adminstates;

import pt.isec.pd.projetopd.cliente.model.data.Data;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStateAdapter;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;

public class SelectOptionAdmin extends ClientStateAdapter {
    public SelectOptionAdmin(ClientContext context, Data data) {
        super(context, data);
        System.out.println("SELECT_OPT_ADMIN STATE");
    }

    @Override
    public boolean selOpt(OPTIONS opt, String string) {

        switch (opt){
            case CREATE_EVENT -> changeState(ClientStates.CREATE_EVENT);
            case EDIT_DATA -> changeState(ClientStates.EDIT_USER_DATA);
            case VIEW_EVENTS -> changeState(ClientStates.VIEW_EVENTS);
            case LOGOUT -> {
                data.logout();
                changeState(ClientStates.INITIAL);
            }
        }

        return true;
    }

    @Override
    public synchronized boolean onMessageReceived(Object message) {


        if(message instanceof String response){
            data.setMessage(response);
            return true;
        }

        return false;
    }

    @Override
    public ClientStates getState() {
        return ClientStates.SELECT_OPT_ADMIN;
    }
}
