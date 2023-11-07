package pt.isec.pd.projetopd.cliente.model.fsm.states.sharedstates;

import pt.isec.pd.projetopd.cliente.model.data.Data;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.data.communication.Presence;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStateAdapter;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;

import java.io.Serializable;

public class RegisterPresence extends ClientStateAdapter {
    public RegisterPresence(ClientContext context, Data data) {
        super(context, data);
        System.out.println("REGISTER_PRESENCE STATE");
    }


    @Override
    public boolean selOpt(OPTIONS opt, String string) {

        switch (opt){
            case SUBMIT -> {
                data.sendToServer(new Presence(Integer.parseInt(string)));
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
        return ClientStates.REG_PRESENCE;
    }
}