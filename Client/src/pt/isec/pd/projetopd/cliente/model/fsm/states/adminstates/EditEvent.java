package pt.isec.pd.projetopd.cliente.model.fsm.states.adminstates;

import pt.isec.pd.projetopd.cliente.model.data.Data;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStateAdapter;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;
import pt.isec.pd.projetopd.communication.classes.EditedEvent;
import pt.isec.pd.projetopd.communication.classes.Event;
import pt.isec.pd.projetopd.communication.classes.CreateCode;
import pt.isec.pd.projetopd.communication.classes.RegisterCode;

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
                        ArrayList<RegisterCode> registerCodes = new ArrayList<>();
                        registerCodes.add(new RegisterCode(Integer.parseInt(splitString[5])));
                        Event event = new Event(
                                splitString[0],
                                splitString[1],
                                splitString[2],
                                splitString[3],
                                splitString[4],
                                registerCodes
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
            data.modifyEditEvent(event);
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