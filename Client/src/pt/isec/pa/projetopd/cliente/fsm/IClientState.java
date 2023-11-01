package pt.isec.pa.projetopd.cliente.fsm;

import pt.isec.pa.projetopd.cliente.data.OPTIONS;

public interface IClientState {

    boolean selOpt(OPTIONS opt);
    ClientStates getState();
}
