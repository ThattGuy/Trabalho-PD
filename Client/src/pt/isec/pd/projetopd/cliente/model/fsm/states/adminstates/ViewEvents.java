package pt.isec.pd.projetopd.cliente.model.fsm.states.adminstates;

import pt.isec.pd.projetopd.cliente.model.data.Data;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStateAdapter;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;
import pt.isec.pd.projetopd.communication.classes.Event;
import pt.isec.pd.projetopd.communication.classes.EventList;
import pt.isec.pd.projetopd.communication.classes.REQUESTS;
import pt.isec.pd.projetopd.communication.classes.User;

import java.util.List;

public class ViewEvents extends ClientStateAdapter {
    public ViewEvents(ClientContext context, Data data) {
        super(context, data);
        data.sendToServer(REQUESTS.EVENTS);
    }

    @Override
    public boolean selOpt(OPTIONS opt, String string) {

        switch (opt){
            case CSV -> data.sendToServer(REQUESTS.CSV_EVENT_PRESENCE);
            case EDIT_EVENT -> {

                    try {
                        int index = Integer.parseInt(string);
                        if(index >= 0 ){
                            data.setEventToEdit(index);
                            changeState(ClientStates.EDIT_EVENT);
                        }

                    } catch (NumberFormatException e) {
                        data.setMessage("Index can't be a string");//todo FIX CONCURRENCE
                        return false;
                    }
            }
            case VIEW_PRESENCE -> changeState(ClientStates.VIEW_EVENT_PRESENCE);
            case CREATE_EVENT -> changeState(ClientStates.CREATE_EVENT);
            case BACK -> changeState(ClientStates.SELECT_OPT_ADMIN);
        }

        return true;
    }

    @Override
    public synchronized boolean onMessageReceived(Object message) {

        if(message instanceof EventList events){
            data.addEvents(events);
        }

        return false;
    }

    @Override
    public ClientStates getState() {
        return ClientStates.VIEW_EVENTS;
    }
}
