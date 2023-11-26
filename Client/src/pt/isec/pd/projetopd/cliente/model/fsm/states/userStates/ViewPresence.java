package pt.isec.pd.projetopd.cliente.model.fsm.states.userStates;

import pt.isec.pd.projetopd.cliente.model.data.Data;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStateAdapter;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;
import pt.isec.pd.projetopd.communication.classes.REQUESTS;
import pt.isec.pd.projetopd.communication.classes.PresencesList;

public class ViewPresence extends ClientStateAdapter {
    public ViewPresence(ClientContext context, Data data) {
        super(context, data);
        data.sendToServer(REQUESTS.PRESENCE);
        System.out.println("VIEW_PRESENCE STATE");
    }

    @Override
    public boolean selOpt(OPTIONS opt, String string) {

        switch (opt){
            case CSV -> data.sendToServer(REQUESTS.CSV_PRESENCE);
            case BACK -> changeState(ClientStates.SELECT_OPT);
        }

        return true;
    }

    @Override
    public synchronized boolean onMessageReceived(Object message) {

        if(message instanceof PresencesList presencesList){
            if(data.getUserName() == presencesList.getUsername())
                data.addPresences(presencesList);
            return true;
        }


        return false;
    }

    @Override
    public ClientStates getState() {
        return ClientStates.VIEW_PRESENCE;
    }

}
