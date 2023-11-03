package pt.isec.pa.projetopd.cliente.model.fsm.states;

import pt.isec.pa.projetopd.cliente.model.data.ClientData;
import pt.isec.pa.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pa.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pa.projetopd.cliente.model.fsm.ClientStateAdapter;

public class EditData extends ClientStateAdapter {
    public EditData(ClientContext context, ClientData data) {
        super(context, data);
    }


    @Override
    public boolean selOpt(OPTIONS opt, String string) {
        //todo edit user data

        switch (opt){
            case BACK -> changeState(context.getLastState());
        }

        return true;
    }

    @Override
    public boolean receiveMesage(String msg) {
        return false;
    }
}
