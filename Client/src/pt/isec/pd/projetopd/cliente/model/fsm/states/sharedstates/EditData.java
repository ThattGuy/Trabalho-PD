package pt.isec.pd.projetopd.cliente.model.fsm.states.sharedstates;

import pt.isec.pd.projetopd.cliente.model.data.Data;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.data.communication.Presence;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStateAdapter;

public class EditData extends ClientStateAdapter {
    public EditData(ClientContext context, Data data) {
        super(context, data);
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
    public boolean onMessageReceived() {

        data.setClientInfo();

        return false;
    }
}
