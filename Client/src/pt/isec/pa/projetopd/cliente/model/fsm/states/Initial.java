package pt.isec.pa.projetopd.cliente.model.fsm.states;

import pt.isec.pa.projetopd.cliente.model.data.ClientData;
import pt.isec.pa.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pa.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pa.projetopd.cliente.model.fsm.ClientStateAdapter;
import pt.isec.pa.projetopd.cliente.model.fsm.ClientStates;

public class Initial extends ClientStateAdapter {
    public Initial(ClientContext context, ClientData data) {
        super(context, data);
    }

    @Override
    public boolean selOpt(OPTIONS opt, String string) {

        switch (opt){
            case REG_USER -> changeState(ClientStates.REG_USER);
            case LOGIN -> changeState(ClientStates.LOGIN);
        }

        return true;
    }
}