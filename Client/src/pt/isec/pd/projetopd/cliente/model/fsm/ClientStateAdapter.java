package pt.isec.pd.projetopd.cliente.model.fsm;

import pt.isec.pd.projetopd.cliente.model.data.Data;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;

import java.io.Serializable;

public class ClientStateAdapter implements IClientState {

    protected ClientContext context;
    protected Data data;

    protected ClientStateAdapter(ClientContext context, Data data) {
        this.context = context;
        this.data = data;
    }

    protected void changeState(ClientStates newState) {
        context.changeState(newState.createState(context,data));
    }

    @Override
    public boolean selOpt(OPTIONS opt, String string) {
        return false;
    }

    @Override
    public boolean onMessageReceived(Serializable message) {
        return false;
    }

    @Override
    public ClientStates getState() {
        return null;
    }


}
