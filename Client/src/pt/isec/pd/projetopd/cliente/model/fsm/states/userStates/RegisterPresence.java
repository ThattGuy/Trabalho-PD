package pt.isec.pd.projetopd.cliente.model.fsm.states.userStates;

import pt.isec.pd.projetopd.cliente.model.data.Data;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.communication.classes.*;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStateAdapter;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;

import java.util.UUID;

public class RegisterPresence extends ClientStateAdapter {
    public RegisterPresence(ClientContext context, Data data) {
        super(context, data);
        System.out.println("REGISTER_PRESENCE STATE");
    }


    @Override
    public boolean selOpt(OPTIONS opt, String string) {
        switch (opt){
            case SUBMIT -> {
                UUID uuid = null;
                try {
                    uuid = UUID.fromString(string);
                } catch (IllegalArgumentException e) {
                    data.setMessage("Wrong format");
                }

                if (uuid != null) {
                    data.sendToServer(uuid);
                }
            }
            case BACK -> changeState(ClientStates.SELECT_OPT);
        }

        return true;
    }

    @Override
    public synchronized boolean onMessageReceived(Object message) {

        if(message instanceof String response){
            data.setMessage(response);
        }
        return false;
    }

    @Override
    public ClientStates getState() {
        return ClientStates.REG_PRESENCE;
    }
}