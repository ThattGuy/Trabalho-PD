package pt.isec.pa.projetopd.cliente.model.fsm;

import pt.isec.pa.projetopd.cliente.model.data.ClientData;

public class ClientContext {

    private final ClientData data;
    private transient IClientState state;
    protected ClientStates lastState;

    public ClientContext() {
        data = new ClientData();
        state = ClientStates.INITIAL.createState(this, data);
    }

    void changeState(IClientState newState) {
        this.lastState = this.getState();
        this.state = newState;
    }

    public ClientStates getLastState() {
        return lastState;
    }

    public ClientStates getState() {
        return state.getState();
    }


}
