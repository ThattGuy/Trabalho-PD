package pt.isec.pa.projetopd.cliente.fsm.states;

import pt.isec.pa.projetopd.cliente.data.ClientData;
import pt.isec.pa.projetopd.cliente.fsm.ClientContext;
import pt.isec.pa.projetopd.cliente.fsm.ClientStateAdapter;

public class RegisterUser extends ClientStateAdapter {
    public RegisterUser(ClientContext context, ClientData data) {
        super(context, data);
    }
}
