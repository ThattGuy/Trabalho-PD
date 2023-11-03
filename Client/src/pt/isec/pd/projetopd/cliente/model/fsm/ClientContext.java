package pt.isec.pd.projetopd.cliente.model.fsm;

import pt.isec.pa.projetopd.cliente.communication.TCPConnection;
import pt.isec.pd.projetopd.cliente.model.data.ClientData;

public class ClientContext {

    private final ClientData data;

    private TCPConnection tcpConnection;
    private transient IClientState state;
    protected ClientStates lastState;

    public ClientContext(String ip, int port) {
        data = new ClientData(ip,port);
        state = ClientStates.INITIAL.createState(this, data);
    }

    void createConnection(){
        tcpConnection = new TCPConnection();
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
