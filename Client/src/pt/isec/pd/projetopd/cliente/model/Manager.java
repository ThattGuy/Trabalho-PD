package pt.isec.pd.projetopd.cliente.model;


import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.data.communication.TCPReceive;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Manager implements TCPReceive.MessageReceivedListener {

    private ClientContext fsm;
    private PropertyChangeSupport pcs;

    private TCPReceive receiveThread;

    public Manager(String ip, int port) {
        pcs = new PropertyChangeSupport(this);
        fsm = new ClientContext(ip,port);
        receiveThread = new TCPReceive(fsm.getSocket(),this);
    }
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    @Override
    public void onMessageReceived(Object message) {
        boolean res = fsm.onMessageReceived(message);
        pcs.firePropertyChange(null, null, res);
    }

    public void selectOption(OPTIONS options, String string) {
        boolean res = fsm.selOpt(options,string);
        pcs.firePropertyChange(null,null,res);
    }

    public String getLastMessage(){
        String msg = fsm.getLastMessage();
        return msg;
    }

    public ClientStates getState() {
        return fsm.getState();
    }
}