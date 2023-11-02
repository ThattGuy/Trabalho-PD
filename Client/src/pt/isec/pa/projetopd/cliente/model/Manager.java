package pt.isec.pa.projetopd.cliente.model;

import pt.isec.pa.projetopd.cliente.model.data.Communication;
import pt.isec.pa.projetopd.cliente.model.fsm.ClientContext;

import java.beans.PropertyChangeSupport;

public class Manager {

    private ClientContext fsm;
    private PropertyChangeSupport pcs;

    public static final String START = "_start_";
    public static final String HASDATA = "_hasdata_";
    public static final String EVOLVE = "_evolve_";

    Communication receiveMsg;

}
