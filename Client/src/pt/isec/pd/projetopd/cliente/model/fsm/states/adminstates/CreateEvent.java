package pt.isec.pd.projetopd.cliente.model.fsm.states.adminstates;

import pt.isec.pd.projetopd.cliente.model.data.Data;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStateAdapter;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;
import pt.isec.pd.projetopd.communication.classes.Event;
import pt.isec.pd.projetopd.communication.classes.User;
import java.text.SimpleDateFormat;


public class CreateEvent extends ClientStateAdapter {
    public CreateEvent(ClientContext context, Data data) {
        super(context, data);
    }

    @Override
    public boolean selOpt(OPTIONS opt, String string) {

        switch (opt){
            case SUBMIT -> {
                String[] splitString = string.split("\n");

                if (splitString.length >= 7) {
                    try {
                        data.sendToServer(new Event(
                                splitString[0],
                                splitString[1],
                                splitString[2],
                                splitString[3],
                                splitString[4]));
                    } catch (NumberFormatException e) {
                        data.setMessage("Wrong format");//todo FIX CONCURRENCE
                        return false;
                    }

                }
            }
            case BACK -> changeState(ClientStates.SELECT_OPT_ADMIN);
        }

        return true;
    }

    @Override
    public synchronized boolean onMessageReceived(Object message) {
        //todo handle repsonse

        return false;
    }

    @Override
    public ClientStates getState() {
        return ClientStates.CREATE_EVENT;
    }
}
