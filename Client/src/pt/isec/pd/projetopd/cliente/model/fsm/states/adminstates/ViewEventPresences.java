package pt.isec.pd.projetopd.cliente.model.fsm.states.adminstates;

import pt.isec.pd.projetopd.cliente.model.data.Data;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStateAdapter;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;
import pt.isec.pd.projetopd.communication.classes.*;

import java.util.ArrayList;

public class ViewEventPresences extends ClientStateAdapter {
    public ViewEventPresences(ClientContext context, Data data) {
        super(context, data);
        System.out.println("VIEW_PRESENCE_STATE STATE");
        data.sendToServer(new EventPresence(data.getEventToEdit()));
    }

    @Override
    public boolean selOpt(OPTIONS opt, String string) {

        switch (opt){
            case SUBMIT -> {
                String[] splitString = string.split("\n");
                if (splitString.length == 2) {
                    try {
                        data.sendToServer(new RemovePresence(splitString[0], splitString[1]));
                    } catch (NumberFormatException e) {
                        data.setMessage("Wrong format");
                        return false;
                    }

                }
            }
            case CSV -> data.sendToServer(new CSVEventPresence(data.getEventToEdit()));
            case BACK -> changeState(ClientStates.VIEW_EVENTS);
        }

        return true;
    }

    @Override
    public synchronized boolean onMessageReceived(Object message) {
        if(message instanceof String response){
            data.setMessage(response);
            return true;
        }

        if(message instanceof PresencesList presencesList){
            if(data.getUserName().equals( presencesList.getUsername())){
                data.addPresences(presencesList);
                return true;
            }

        }

        return false;
    }

    @Override
    public ClientStates getState() {
        return ClientStates.VIEW_EVENT_PRESENCE;
    }
}