package pt.isec.pd.projetopd.cliente.model.fsm.states.sharedstates;

import pt.isec.pd.projetopd.cliente.model.data.Data;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.communication.classes.*;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStateAdapter;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;

public class EditInfo extends ClientStateAdapter {
    public EditInfo(ClientContext context, Data data) {
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
        if (message instanceof User) {
            data.setClientInfo((User) message);
            return true;
        } else {
            data.setMessage("Error deserializing the User object");
            return false;
        }
    }

    @Override
    public ClientStates getState() {
        return ClientStates.EDIT_USER_DATA;
    }
}
