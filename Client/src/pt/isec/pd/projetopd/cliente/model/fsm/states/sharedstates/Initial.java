package pt.isec.pd.projetopd.cliente.model.fsm.states.sharedstates;

import pt.isec.pd.projetopd.cliente.model.data.Data;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStateAdapter;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;

public class Initial extends ClientStateAdapter {
    public Initial(ClientContext context, Data data) {
        super(context, data);
        System.out.println("INITIAL STATE");
    }

    @Override
    public synchronized boolean selOpt(OPTIONS opt, String string) {

        switch (opt){
            case REG_USER -> changeState(ClientStates.REG_USER);
            case LOGIN -> changeState(ClientStates.LOGIN);
        }

        return true;
    }

    @Override
    public ClientStates getState() {
        return ClientStates.INITIAL;
    }
}