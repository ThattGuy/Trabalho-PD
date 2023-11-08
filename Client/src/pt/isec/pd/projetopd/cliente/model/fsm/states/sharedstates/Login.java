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
                } else {
                    return false;
                }
            }
            case BACK -> changeState(context.getLastState());
        }

        return true;
    }

    @Override
    public boolean onMessageReceived(Object message) {
        if(message instanceof RESPONSE response){
            if(response == RESPONSE.PROBLEM_WTIH_NAME){
                data.setErrorMessage("Username incorrect");
                return false;
            }
            if(response == RESPONSE.PROBLEM_WTIH_PASSWORD){
                data.setErrorMessage("Password incorrect");
                return false;
            }
        }

        if(message instanceof User user){
            data.setClientInfo(user);
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
