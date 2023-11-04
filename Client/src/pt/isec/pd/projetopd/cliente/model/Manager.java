package pt.isec.pd.projetopd.cliente.model;

<<<<<<< HEAD
=======
import pt.isec.pd.projetopd.cliente.model.data.communication.TCPReceive;
>>>>>>> 79a006ae360aa63feefc0b8aa1ec64fd5208b69d
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