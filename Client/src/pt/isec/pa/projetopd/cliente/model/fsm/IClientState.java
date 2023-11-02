package pt.isec.pa.projetopd.cliente.model.fsm;

import pt.isec.pa.projetopd.cliente.model.data.OPTIONS;

public interface IClientState {

    boolean selOpt(OPTIONS opt);
    ClientStates getState();
}
