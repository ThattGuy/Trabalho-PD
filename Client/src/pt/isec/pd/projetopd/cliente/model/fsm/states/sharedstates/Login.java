package pt.isec.pd.projetopd.cliente.model.fsm.states.sharedstates;

import pt.isec.pd.projetopd.cliente.model.data.Data;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStateAdapter;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStates;
import pt.isec.pd.projetopd.communication.classes.Authentication;
import pt.isec.pd.projetopd.communication.classes.RESPONSE;
import pt.isec.pd.projetopd.communication.classes.User;

public class Login extends ClientStateAdapter {
    public Login(ClientContext context, Data data) {
        super(context, data);
        System.out.println("LOGIN STATE");
    }

    @Override
    public boolean selOpt(OPTIONS opt, String string) {

        switch (opt){
            case LOGIN -> {
                String[] splitString = string.split("\n");
                if (splitString.length >= 2) {
                    data.sendToServer(new Authentication(splitString[0], splitString[1]));
                    changeState(ClientStates.REG_PRESENCE);
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
        if(message instanceof RESPONSE response){
            switch (response){
                case PROBLEM_WITH_NAME -> data.setMessage("Username incorrect");
                case PROBLEM_WITH_PASSWORD -> data.setMessage("Password incorrect");
            }
        }

        if(message instanceof User user){
            data.setUserInfo(user);
            if(data.isUserAdmin()){
                changeState(ClientStates.SELECT_OPT_ADMIN);
                return true;
            }else{
                changeState(ClientStates.SELECT_OPT);
                return true;
            }
        }
        return false;
    }

    @Override
    public ClientStates getState() {
        return ClientStates.LOGIN;
    }
}
