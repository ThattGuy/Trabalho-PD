package pt.isec.pa.projetopd.cliente.fsm;

import pt.isec.pa.projetopd.cliente.data.ClientData;
import pt.isec.pa.projetopd.cliente.data.OPTIONS;

public class ClientStateAdapter implements IClientState {

    protected ClientContext context;
    protected ClientData data;

    protected ClientStateAdapter(ClientContext context, ClientData data) {
        this.context = context;
        this.data = data;
    }

    protected void changeState(ClientStates newState) {
        context.changeState(newState.createState(context,data));
    }

    @Override
    public boolean selOpt(OPTIONS opt) {
        return false;
    }

    @Override
    public ClientStates getState() {
        return null;
    }


}
