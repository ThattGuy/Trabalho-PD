package pt.isec.pa.projetopd.cliente.model.fsm;

import pt.isec.pa.projetopd.cliente.communication.TCPConnection;
import pt.isec.pa.projetopd.cliente.model.data.ClientData;
import pt.isec.pa.projetopd.cliente.model.data.OPTIONS;

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

    protected void createConnection() {
        context.createConnection();
    }

    @Override
    public boolean selOpt(OPTIONS opt, String string) {
        return false;
    }

    @Override
    public boolean receiveMesage(String msg) {
        return false;
    }

    @Override
    public ClientStates getState() {
        return null;
    }


}
