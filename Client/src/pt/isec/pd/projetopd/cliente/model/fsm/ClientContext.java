package pt.isec.pd.projetopd.cliente.model.fsm;
import pt.isec.pd.projetopd.cliente.model.data.Data;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;

import java.net.Socket;
import java.util.List;

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
        return state.onMessageReceived(message);
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
        String msg = data.getMessage();
        data.setMessage(null);
        return msg;
    }

    public Socket getSocket() {
        return data.getSocket();
    }

    public String getUserName() {
        return data.getUserName();
    }

    public int getStudentNumber() {
        return data.getStudentNumber();
    }

    public int getNIF() {
        return data.getNIF();
    }

    public String getID() {
        return data.getID();
    }

    public String getAdress() {
        return data.getAddress();
    }

    public List<String> getEvents() {
        return data.getEventsString();
    }

    public String getPresences() {
        return data.getPresenceString();
    }


    public List<String> getEventVariables() {
        if(data.getEventToEdit() == null)
            return null;
        return data.getEventToEdit().getVariables();
    }

    public String getName() {
        return data.getName();
    }

    public String getEventCodes() {
        if(data.getEventToEdit() == null)
            return null;
        return data.getEventToEdit().getPresenceCodes().toString();
    }
}