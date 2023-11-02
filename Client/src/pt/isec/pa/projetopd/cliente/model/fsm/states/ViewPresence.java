package pt.isec.pa.projetopd.cliente.model.fsm.states;

import pt.isec.pa.projetopd.cliente.model.data.ClientData;
import pt.isec.pa.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pa.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pa.projetopd.cliente.model.fsm.ClientStateAdapter;

public class ViewPresence extends ClientStateAdapter {
    public ViewPresence(ClientContext context, ClientData data) {
        super(context, data);
    }

    @Override
    public boolean selOpt(OPTIONS opt, String string) {

        switch (opt){
            case BACK -> changeState(context.getLastState());
        }

        return true;
    }
}
