package pt.isec.pd.projetopd.cliente.model.fsm.states.adminstates;

import pt.isec.pd.projetopd.cliente.model.data.Data;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStateAdapter;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;
import pt.isec.pd.projetopd.communication.classes.*;

import java.util.ArrayList;

public class EditEvent extends ClientStateAdapter {
    public EditEvent(ClientContext context, Data data) {
        super(context, data);
        System.out.println("EDIT_EVENT STATE");
    }

    @Override
    public boolean selOpt(OPTIONS opt, String string) {

        switch (opt) {
            case SUBMIT -> {
                String[] splitString = string.split("\n");
                if (splitString.length == 5) {
                    try {
                        Event event = new Event(
                                splitString[0],
                                splitString[1],
                                splitString[2],
                                splitString[3],
                                splitString[4],
                                data.getEventToEdit().getPresenceCodes()
                        );

                        data.sendToServer(new EditedEvent(event, data.getEventToEdit().getName()));
                    } catch (NumberFormatException e) {
                        data.setMessage("Wrong format");
                        return false;
                    }
                }
            }
            case NEW_CODE -> {
                try {
                    int timeInMinutes = Integer.parseInt(string);
                    data.addEventCode(new RegisterCode(timeInMinutes));
                    data.sendToServer(new CreateCode(data.getEventToEdit().getName(), data.getLastEventCode()));
                } catch (NumberFormatException e) {
                    data.setMessage("Need to enter time in minutes");
                    return false;
                }
            }
            case DELETE -> {
                data.sendToServer(new DeleteEvent(data.getEventToEdit().getName()));
            }

            case BACK -> {
                data.setEventIndexEdit(-1);
                changeState(ClientStates.VIEW_EVENTS);
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
            if(event.getName().equals(data.getEventToEdit().getName())){
                data.modifyEditEvent(event);
                data.setMessage("Event edited successfully");
                return true;
            }
        }
        return false;
    }

    @Override
    public ClientStates getState() {
        return ClientStates.EDIT_EVENT;
    }
}