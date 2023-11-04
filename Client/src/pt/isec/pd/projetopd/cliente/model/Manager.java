package pt.isec.pd.projetopd.cliente.model;

import pt.isec.pd.projetopd.cliente.model.data.communication.TCPReceive;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class Manager implements TCPReceive.MessageReceivedListener {

    private ClientContext fsm;
    private PropertyChangeSupport pcs;

    private TCPReceive receiveThread;

    public Manager(String ip, int port) {
        pcs = new PropertyChangeSupport(this);
        fsm = new ClientContext(ip,port);
        receiveThread = new TCPReceive(ip,port,this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        pcs.addPropertyChangeListener(listener);
    }

    @Override
    public void onMessageReceived(String message) {
        boolean res = fsm.onMessageReceived(message);
        pcs.firePropertyChange(null, null, res);
    }

}