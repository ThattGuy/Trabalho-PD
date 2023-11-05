package pt.isec.pd.projetopd.cliente.model.fsm.states.sharedstates;

import pt.isec.pd.projetopd.cliente.model.data.Data;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.data.communication.ClientInfo;
import pt.isec.pd.projetopd.cliente.model.data.communication.Presence;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStateAdapter;

import java.io.Serializable;

public class EditData extends ClientStateAdapter {
    public EditData(ClientContext context, Data data) {
        super(context, data);
        data.sendToServer("PRESENCE");
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
    public boolean onMessageReceived(Serializable message) {
        if (message instanceof ClientInfo) {
            try {
                ClientInfo clientInfo = (ClientInfo) message;
                data.setClientInfo(clientInfo);
                return true;
            } catch (ClassCastException e) {
                System.err.println("Error deserializing the ClientInfo object: " + e);
                return false;
            }
        }
        return false;
    }

}
