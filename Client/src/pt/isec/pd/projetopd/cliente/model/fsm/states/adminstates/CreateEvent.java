package pt.isec.pd.projetopd.cliente.model.fsm.states.adminstates;

import pt.isec.pd.projetopd.cliente.model.data.Data;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStateAdapter;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;
import pt.isec.pd.projetopd.communication.classes.Event;
import pt.isec.pd.projetopd.communication.classes.RESPONSE;
import pt.isec.pd.projetopd.communication.classes.RegisterCode;
import pt.isec.pd.projetopd.communication.classes.User;
import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class CreateEvent extends ClientStateAdapter {
    public CreateEvent(ClientContext context, Data data) {
        super(context, data);
        System.out.printf("CREATE_EVENT STATE");
    }

    @Override
    public boolean selOpt(OPTIONS opt, String string) {

        switch (opt){
            case SUBMIT -> {
                String[] splitString = string.split("\n");

                if (splitString.length == 6) {
                    try {
                        ArrayList<RegisterCode> registerCodes = new ArrayList<>();
                        registerCodes.add(new RegisterCode(Integer.parseInt(splitString[5])));
                        data.sendToServer(new Event(
                                splitString[0],
                                splitString[1],
                                splitString[2],
                                splitString[3],
                                splitString[4],
                                registerCodes
                                ));
                    } catch (NumberFormatException e) {
                        data.setMessage("Wrong format");
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

        if (message instanceof String response) {
            data.setMessage(response);
            return true;
        }

        if(message instanceof Event event){
            data.setMessage("Event Created register codes" + event.getPresenceCodes().get(0).toString());
        }

        return false;
    }

    @Override
    public ClientStates getState() {
        return ClientStates.CREATE_EVENT;
    }
}
