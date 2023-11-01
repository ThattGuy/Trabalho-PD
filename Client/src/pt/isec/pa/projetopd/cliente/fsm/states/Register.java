package pt.isec.pa.projetopd.cliente.fsm.states;

import pt.isec.pa.projetopd.cliente.data.ClientData;
import pt.isec.pa.projetopd.cliente.data.OPTIONS;
import pt.isec.pa.projetopd.cliente.fsm.ClientContext;
import pt.isec.pa.projetopd.cliente.fsm.ClientStateAdapter;
import pt.isec.pa.projetopd.cliente.fsm.ClientStates;
import pt.isec.pa.projetopd.cliente.fsm.IClientState;

public class Register extends ClientStateAdapter {

    protected Register(ClientContext context, ClientData data) {
        super(context, data);
    }

    @Override
    public ClientStates getState() {
        return null;
    }
}
