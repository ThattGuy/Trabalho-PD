package pt.isec.pd.projetopd.cliente.model.fsm.states.sharedstates;

import pt.isec.pd.projetopd.cliente.model.data.Data;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.communication.classes.*;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStateAdapter;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;

public class RegisterUser extends ClientStateAdapter {
    public RegisterUser(ClientContext context, Data data) {
        super(context, data);
        System.out.println("REGISTER_USER STATE");
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
                        data.setMessage("Student Number and ID need to be numbers");//todo FIX CONCURRENCE
                        return false;
                    }
                } else {
                    return false;
                }
            }
            case BACK -> changeState(ClientStates.INITIAL);
        }
        return true;
    }

    @Override
    public synchronized boolean onMessageReceived(Object message) {

        if(message instanceof RESPONSE response){
            if(response == RESPONSE.ACCEPTED){
                changeState(ClientStates.SELECT_OPT);
                return true;
            }
            if(response == RESPONSE.PROBLEM_WITH_NAME){
                data.setMessage("Username already in use");
            }
        }

        return false;
    }

    @Override
    public ClientStates getState() {
        return ClientStates.REG_USER;
    }
}