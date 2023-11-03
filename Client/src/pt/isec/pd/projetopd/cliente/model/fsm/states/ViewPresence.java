package pt.isec.pd.projetopd.cliente.model.fsm.states;

import pt.isec.pd.projetopd.cliente.model.data.ClientData;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStateAdapter;

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

    @Override
    public boolean receiveMesage(String msg) {
        return false;
    }
}
