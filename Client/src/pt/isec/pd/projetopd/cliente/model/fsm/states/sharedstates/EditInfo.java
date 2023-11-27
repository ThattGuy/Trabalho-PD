package pt.isec.pd.projetopd.cliente.model.fsm.states.sharedstates;

import pt.isec.pd.projetopd.cliente.model.data.Data;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.communication.classes.*;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStateAdapter;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;

public class EditInfo extends ClientStateAdapter {
    public EditInfo(ClientContext context, Data data) {
        super(context, data);
        data.sendToServer(REQUESTS.USER_DATA);
    }


    @Override
    public boolean selOpt(OPTIONS opt, String string) {

        switch (opt) {
            case SUBMIT -> {
                String[] splitString = string.split("\n");

                if (splitString.length >= 5) {
                    try {
                        int studentNumber = Integer.parseInt(splitString[2]);
                        int nif = Integer.parseInt(splitString[3]);

                        data.sendToServer(new EditUser(new User(
                                data.getUserName(),
                                splitString[0],
                                splitString[1],
                                studentNumber,
                                nif,
                                data.getID(),
                                splitString[4]
                        )));
                    } catch (NumberFormatException e) {
                        data.setMessage("Student Number and ID need to be numbers");
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
    public synchronized boolean onMessageReceived(Object message) {

        if(message instanceof String response){
            data.setMessage(response);
            return true;
        }

        if (message instanceof User) {
            data.setUserInfo((User) message);
            return true;
        }
        return false;
    }

    @Override
    public ClientStates getState() {
        return ClientStates.EDIT_USER_DATA;
    }
}
