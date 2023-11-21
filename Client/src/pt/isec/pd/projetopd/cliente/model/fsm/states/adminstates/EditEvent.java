package pt.isec.pd.projetopd.cliente.model.fsm.states.adminstates;

import pt.isec.pd.projetopd.cliente.model.data.Data;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStateAdapter;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;
import pt.isec.pd.projetopd.communication.classes.Event;

public class EditEvent extends ClientStateAdapter {
    public EditEvent(ClientContext context, Data data) {
        super(context, data);
    }

    @Override
    public boolean selOpt(OPTIONS opt, String string) {

        switch (opt){
            case SUBMIT -> {
                //todo edit
            }
            case BACK -> changeState(ClientStates.SELECT_OPT_ADMIN);
        }

        return true;
    }

    @Override
    public synchronized boolean onMessageReceived(Object message) {
        if (message instanceof Event event) {
            data.addEvents(event);
            return true;
        } else {
            data.setMessage("Error deserializing the Event object");
            return false;
        }
    }

    @Override
    public ClientStates getState() {
        return ClientStates.EDIT_EVENT;
    }
}
