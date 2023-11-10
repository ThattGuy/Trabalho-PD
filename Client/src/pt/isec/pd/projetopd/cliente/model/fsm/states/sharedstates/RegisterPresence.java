package pt.isec.pd.projetopd.cliente.model.fsm.states.sharedstates;

import pt.isec.pd.projetopd.cliente.model.data.Data;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.communication.classes.*;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStateAdapter;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;

public class RegisterPresence extends ClientStateAdapter {
    public RegisterPresence(ClientContext context, Data data) {
        super(context, data);
        System.out.println("REGISTER_PRESENCE STATE");
    }


    @Override
    public boolean selOpt(OPTIONS opt, String string) {

        switch (opt){
            case SUBMIT -> {
                data.sendToServer(new Presence(Integer.parseInt(string)));
            }
            case BACK -> changeState(context.getLastState());
        }

        return true;
    }

    @Override
    public synchronized boolean onMessageReceived(Object message) {

        if(message instanceof RESPONSE response){
            switch (response){
                case ACCEPTED -> data.setMessage("Presence submitted");
                case DECLINED -> data.setMessage("Presence not submitted.\n Verify code and make sure you're not registered in another class");
            }
        }
        return false;
    }

    @Override
    public ClientStates getState() {
        return ClientStates.REG_PRESENCE;
    }
}