package pt.isec.pd.projetopd.cliente.model.fsm.states.sharedstates;

import pt.isec.pd.projetopd.cliente.model.data.Data;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStateAdapter;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;
import pt.isec.pd.projetopd.communication.classes.Presence;
import pt.isec.pd.projetopd.communication.classes.REQUESTS;

public class ViewPresence extends ClientStateAdapter {
    public ViewPresence(ClientContext context, Data data) {
        super(context, data);
        data.sendToServer(REQUESTS.PRESENCE);
        System.out.println("VIEW_PRESENCE STATE");
    }

    @Override
    public boolean selOpt(OPTIONS opt, String string) {

        switch (opt){
            case CSV -> data.sendToServer(REQUESTS.CSV);
            case BACK -> changeState(context.getLastState());
        }

        return true;
    }

    @Override
    public boolean onMessageReceived(Object message) {
        //todo null message. Array or presence?

        return false;
    }

    @Override
    public ClientStates getState() {
        return ClientStates.VIEW_PRESENCE;
    }
}
