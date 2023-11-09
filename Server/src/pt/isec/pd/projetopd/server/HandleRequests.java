package pt.isec.pd.projetopd.server;

import pt.isec.pd.projetopd.communication.classes.*;
import pt.isec.pd.projetopd.server.data.DataBase.DataBase;


//nesta classe vou interpretar os pedidos do cliente

//apos interpretar o pedido utilizando a db
//tenho de enviar a resposta ao cliente:
    //->para isso tenho de ir ao mapa de clientes e procurar o socket do cliente que fez o pedido
//para isso tenho de criar um outputstream para o socket)
//
public class HandleRequests {

    private DataBase ManDB;

    public HandleRequests(String path){
        this.ManDB = new DataBase(path);
    }
    public Object receive(Object o){

        if(o instanceof REQUESTS){
            if (o.equals(REQUESTS.PRESENCE)) {
                System.out.println("Received Presence");
            } else if (o.equals(REQUESTS.USER_DATA)) {
                System.out.println("Received User Data");
            } else {
                System.out.println("Received unknown object");
            }
        }

        else if(o instanceof Authentication) {
            Authentication auth = (Authentication) o;

            if(ManDB.CheckLogin(auth.getUsername(), auth.getPassword())) {
                System.out.println("Login successful");
                return RESPONSE.ACCEPTED;
            }
        }
        else
            if(o instanceof User) {
                User clientInfo = (User) o;
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

        return RESPONSE.DECLINED;
    }
}
