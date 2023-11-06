package pt.isec.pd.projetopd.cliente.model.fsm.states.sharedstates;

import pt.isec.pd.projetopd.cliente.model.data.Data;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.data.communication.User;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStateAdapter;

import java.io.Serializable;

public class RegisterUser extends ClientStateAdapter {
    public RegisterUser(ClientContext context, Data data) {
        super(context, data);
    }

    @Override
    public boolean selOpt(OPTIONS opt, String string) {

        switch (opt) {
            case SUBMIT -> {
                String[] splitString = string.split("\n");

                if (splitString.length >= 7) {
                    try {
                        int studentNumber = Integer.parseInt(splitString[3]);
                        int nif = Integer.parseInt(splitString[4]);

                        data.sendToServer(new User(
                                splitString[0],
                                splitString[1],
                                splitString[2],
                                studentNumber,
                                nif,
                                splitString[5],
                                splitString[6]));
                    } catch (NumberFormatException e) {
                        return false;
                    }
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