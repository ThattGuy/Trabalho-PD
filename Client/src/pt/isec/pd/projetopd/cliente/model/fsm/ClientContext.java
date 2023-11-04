package pt.isec.pd.projetopd.cliente.model.fsm;
import pt.isec.pd.projetopd.cliente.model.data.ClientData;

public class ClientContext {

    private final ClientData data;
    private transient IClientState state;
    protected ClientStates lastState;

    public ClientContext(String ip, int port){
        data = new ClientData(ip,port);
        state = ClientStates.INITIAL.createState(this, data);
    }

    void changeState(IClientState newState) {
        this.lastState = this.getState();
        this.state = newState;
    }

    public boolean onMessageReceived(String message) {
        return false;
    }

    public ClientStates getLastState() {
        return lastState;
    }

    public ClientStates getState() {
        return state.getState();
    }

}