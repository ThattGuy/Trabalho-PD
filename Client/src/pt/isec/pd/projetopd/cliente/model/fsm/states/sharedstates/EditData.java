package pt.isec.pd.projetopd.cliente.model.fsm.states.sharedstates;

import pt.isec.pd.projetopd.cliente.model.data.Data;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.data.communication.User;
import pt.isec.pd.projetopd.cliente.model.data.communication.REQUESTS;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStateAdapter;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;

public class EditData extends ClientStateAdapter {
    public EditData(ClientContext context, Data data) {
        super(context, data);
        data.sendToServer(REQUESTS.USER_DATA);
    }


    @Override
    public boolean selOpt(OPTIONS opt, String string) {

        switch (opt) {
            case SUBMIT -> {
                return false;
            }
            case BACK -> changeState(context.getLastState());
        }

        return true;
    }

    @Override
    public boolean onMessageReceived(Object message) {

        if(message instanceof User){
            data.setClientInfo((User) message);
        }

        //TODO show message
        if (message instanceof User) {
            try {
                User clientInfo = (User) message;
                data.setClientInfo(clientInfo);
                return true;
            } catch (ClassCastException e) {
                System.err.println("Error deserializing the ClientInfo object: " + e);
                return false;
            }
        }
        return false;
    }

    @Override
    public ClientStates getState() {
        return ClientStates.EDIT_USER_DATA;
    }
}
