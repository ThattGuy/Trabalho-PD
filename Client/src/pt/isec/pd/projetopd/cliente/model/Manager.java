package pt.isec.pd.projetopd.cliente.model;

import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;

import java.beans.PropertyChangeSupport;

public class Manager {

    private ClientContext fsm;
    private PropertyChangeSupport pcs;

    public Manager(String ip, int port) {
        pcs = new PropertyChangeSupport(this);
        fsm = new ClientContext(ip,port);
    }


}
