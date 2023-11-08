package pt.isec.pd.projetopd.cliente.model.fsm;
import pt.isec.pd.projetopd.cliente.model.data.Data;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;

public class ClientContext {

    private final Data data;
    private transient IClientState state;
    protected ClientStates lastState;

    public ClientContext(String ip, int port){
        data = new Data(ip,port);
        state = ClientStates.INITIAL.createState(this, data);
    }

    void changeState(IClientState newState) {
        this.lastState = this.getState();
        this.state = newState;
    }

    public boolean onMessageReceived(Object message) {
        return false;
    }

    public boolean selOpt(OPTIONS opt, String string){
        return state.selOpt(opt,string);
    }

    public ClientStates getLastState() {
        return lastState;
    }

    public ClientStates getState() {
        return state.getState();
    }

    public String getLastMessage() {
        String msg = data.getErrorMessage();
        data.setErrorMessage(null);
        return msg;
    }

}