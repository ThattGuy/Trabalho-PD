package pt.isec.pd.projetopd.server;

import pt.isec.pd.projetopd.server.data.Authentication;
import pt.isec.pd.projetopd.server.data.ClientInfo;
import pt.isec.pd.projetopd.server.data.Presence;

public class HandleRequests {

    public void receive(Object o){
        ObserveDatabase db = new ObserveDatabase();
        if(o instanceof Authentication) {
            Authentication auth = (Authentication) o;
            System.out.println("Received Authentication");
            if(db.CheckLogin(auth.getUsername(), auth.getPassword())) {
                System.out.println("Login successful");


            }


        } else
            if(o instanceof ClientInfo) {
                ClientInfo clientInfo = (ClientInfo) o;
                System.out.println("Received ClientInfo");
                System.out.println( clientInfo.getUsername() + ": " + clientInfo.getId() + ": " + clientInfo.getStudentNumber());
            }
            else
                if(o instanceof Presence){
                    Presence presence = (Presence) o;
                    System.out.println("Received Presence");
                    System.out.println( "Code: " + presence.getcode());
                }
                else {
                    System.out.println("Received unknown object");
                }
    }
}
