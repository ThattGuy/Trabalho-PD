package pt.isec.pd.projetopd.cliente.model.fsm.states;

import pt.isec.pa.projetopd.cliente.communication.TCPConnection;
import pt.isec.pd.projetopd.cliente.model.data.ClientData;
import pt.isec.pd.projetopd.cliente.model.data.OPTIONS;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientContext;
import pt.isec.pd.projetopd.cliente.model.fsm.ClientStateAdapter;

import java.util.StringTokenizer;

public class Login extends ClientStateAdapter {
    public Login(ClientContext context, ClientData data) {
        super(context, data);
        createConnection();
    }

    @Override
    public boolean selOpt(OPTIONS opt, String string) {

        switch (opt){
            case SUBMIT -> {
                StringTokenizer tokenizer = new StringTokenizer(string, "\n");
            }
            case BACK -> changeState(context.getLastState());
        }

        return true;
    }

    @Override
    public boolean receiveMesage(String msg) {
        return false;
    }
}
