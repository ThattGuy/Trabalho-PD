package pt.isec.pa.projetopd.cliente.model.fsm.states;

import pt.isec.pa.projetopd.cliente.model.data.ClientData;
import pt.isec.pa.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pa.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pa.projetopd.cliente.model.fsm.ClientStateAdapter;
import pt.isec.pa.projetopd.cliente.model.fsm.ClientStates;

public class RegisterUser extends ClientStateAdapter {
    public RegisterUser(ClientContext context, ClientData data) {
        super(context, data);
        createConnection();
    }

    @Override
    public boolean selOpt(OPTIONS opt, String string) {

        switch (opt){
            case SUBMIT -> {
                return false; //todo wait response from server
            }
            case BACK -> changeState(context.getLastState());
        }

        return true;
    }

    @Override
    public boolean receiveMesage(String msg) {
        return false;
    }
}