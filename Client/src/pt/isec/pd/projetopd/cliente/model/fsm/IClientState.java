package pt.isec.pd.projetopd.cliente.model.fsm;

import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;

import java.io.Serializable;

public interface IClientState {

    boolean selOpt(OPTIONS opt, String string);
    boolean onMessageReceived(Serializable message);
    ClientStates getState();
}
