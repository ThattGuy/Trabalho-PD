package pt.isec.pd.projetopd.cliente.model.fsm.states.adminstates;

import pt.isec.pd.projetopd.cliente.model.data.Data;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStateAdapter;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;
import pt.isec.pd.projetopd.communication.classes.Event;
import pt.isec.pd.projetopd.communication.classes.EventCode;
import pt.isec.pd.projetopd.communication.classes.PresenceCode;

public class EditEvent extends ClientStateAdapter {
    public EditEvent(ClientContext context, Data data) {
        super(context, data);
        System.out.printf("EDIT_EVENT STATE");
    }

    @Override
    public boolean selOpt(OPTIONS opt, String string) {

        switch (opt){
            case SUBMIT -> {//todo

            }
            case NEW_CODE -> {
                try {
                    int timeInMinutes = Integer.parseInt(string);
                    data.addEventCode(new PresenceCode(timeInMinutes));
                    data.sendToServer(new EventCode(data.getEventToEdit().getName(), data.getLastEventCode()));
                } catch (NumberFormatException e) {
                    data.setMessage("Time must be in minutes");
                    return false;
                }
            }
            case BACK -> {
                data.setEventIndexEdit(-1);
                changeState(ClientStates.SELECT_OPT_ADMIN);
            }
        }

        return true;
    }

    @Override
    public synchronized boolean onMessageReceived(Object message) {
        if (message instanceof String response) {
            data.setMessage(response);
            return true;
        }

        if (message instanceof Event event) {
            data.addEvent(event);
            return true;
        } else {
            data.setMessage("Error deserializing the Event object");
            return false;
        }
    }

    @Override
    public ClientStates getState() {
        return ClientStates.EDIT_EVENT;
    }
}