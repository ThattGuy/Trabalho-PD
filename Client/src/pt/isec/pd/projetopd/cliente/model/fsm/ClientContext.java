package pt.isec.pd.projetopd.cliente.model.fsm;
import pt.isec.pd.projetopd.cliente.model.data.Data;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;

import java.net.Socket;

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

    public String getName() {
        return data.getName();
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

    public String getEvents() {
        return data.getEventsString();
    }
}