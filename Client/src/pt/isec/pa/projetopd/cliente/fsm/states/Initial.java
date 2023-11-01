package pt.isec.pa.projetopd.cliente.fsm.states;

import pt.isec.pa.projetopd.cliente.data.ClientData;
import pt.isec.pa.projetopd.cliente.data.OPTIONS;
import pt.isec.pa.projetopd.cliente.fsm.ClientContext;
import pt.isec.pa.projetopd.cliente.fsm.ClientStateAdapter;
import pt.isec.pa.projetopd.cliente.fsm.ClientStates;

public class Initial extends ClientStateAdapter {
    public Initial(ClientContext context, ClientData data) {
        super(context, data);
    }

    @Override
    public boolean selOpt(OPTIONS opt) {

        if(opt == OPTIONS.REG){
            changeState(ClientStates.REG_USER);
        }

        switch (opt){
            case REG -> changeState(ClientStates.REG_USER);
            case BACK -> changeState(context.getState());
        }

        return false;
    }
}
