package pt.isec.pd.projetopd.cliente.model.fsm.states.sharedstates;

import pt.isec.pd.projetopd.cliente.model.data.Data;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.data.communication.Authentication;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStateAdapter;

import java.io.Serializable;

public class Login extends ClientStateAdapter {
    public Login(ClientContext context, Data data) {
        super(context, data);
        data.startTcpSend();
    }

    @Override
    public boolean selOpt(OPTIONS opt, String string) {

        switch (opt){
            case SUBMIT -> {
                String[] splitString = string.split("\n");
                if (splitString.length >= 2) {
                    data.sendToServer(new Authentication(splitString[0], splitString[1]));
                } else {
                    return false;
                }
            }
            case BACK -> changeState(context.getLastState());
        }

        return true;
    }

    @Override
    public boolean onMessageReceived(Serializable message) {

        return false;
    }
}
