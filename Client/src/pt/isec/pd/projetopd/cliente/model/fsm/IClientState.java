package pt.isec.pd.projetopd.cliente.model.fsm;

import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;

public interface IClientState {

    boolean selOpt(OPTIONS opt, String string);
    boolean onMessageReceived(Object message);
    ClientStates getState();
}
