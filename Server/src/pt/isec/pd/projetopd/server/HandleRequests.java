package pt.isec.pd.projetopd.server;

import pt.isec.pd.projetopd.communication.classes.*;
import pt.isec.pd.projetopd.server.data.DataBase.DataBase;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.UUID;


//nesta classe vou interpretar os pedidos do cliente

//apos interpretar o pedido utilizando a db
//tenho de enviar a resposta ao cliente:
    //->para isso tenho de ir ao mapa de clientes e procurar o socket do cliente que fez o pedido
//para isso tenho de criar um outputstream para o socket)
//
public class HandleRequests {

    private DataBase manDB;
    private ServerInfo serverInfo;

    public HandleRequests(String path, ServerInfo serverInfo){
        this.manDB = new DataBase(path);
        this.serverInfo = serverInfo;
    }

    private Serializable InterpretClientMessage(Object request, String ClientMail) {
        System.out.println(request.toString());

        Boolean isReturn = null;
        Serializable dbresponse;
        switch (request) {
            case REQUESTS requests -> {
                return this.InterpretRequest(requests, ClientMail);
            }
            case Authentication auth -> {
                return manDB.CheckLogin(auth.getUsername(), auth.getPassword());
            }
            case User clientInfo -> {
                return manDB.register(clientInfo.getUsername(), clientInfo.getPassword(), clientInfo.getName(), clientInfo.getStudentNumber(), clientInfo.getNIF(), clientInfo.getId(), clientInfo.getAddress(), false);
            }
            case Event event -> {

                 manDB.registerEvent(event.getName(), event.getLocation(), event.getDate(), event.getBeginning(), event.getEndTime(),event.getPresenceCodes().get(0), ClientMail);
            }
            case EventPresence eventPresence -> {
                dbresponse =  (Serializable) manDB.getEventPresence(eventPresence.getEvent().getName());
                this.serverInfo.sendNotification(dbresponse);
                return dbresponse;
            }
            case CreateCode eventCode-> {
                dbresponse =   manDB.createCode(eventCode.getEventName(),eventCode.getEventCode().getCode(),eventCode.getEventCode().getExpirationTime());
                this.serverInfo.sendNotification(dbresponse);
                return dbresponse;
            }
            case UUID code -> {
                return manDB.registerPresence(code, ClientMail);
            }
            case EditedEvent editedEvent-> {
                dbresponse =    manDB.editEvent(editedEvent.getEvent(), editedEvent.getOldName());
                this.serverInfo.sendNotification(dbresponse);
                return dbresponse;
            }

            default -> {
                return RESPONSE.DECLINED;
            }
        }
        return RESPONSE.DECLINED;
    }

    public Serializable receive(Object request, ObjectOutputStream Clientout){
        String mail =this.serverInfo.getClientMail(Clientout);
        Serializable dbResponse = this.InterpretClientMessage(request, mail);

        if(request instanceof Authentication && dbResponse instanceof User ) //Check if new client connecting
        {
            this.serverInfo.addClient(((Authentication) request).getUsername(), Clientout);
            //return dbResponse;
            mail =this.serverInfo.getClientMail(Clientout);
        }
        else
            if(dbResponse instanceof RESPONSE && dbResponse.equals(RESPONSE.DECLINED)) //Client operation declined
                return dbResponse;

        return dbResponse;
    }


    private Serializable InterpretRequest(REQUESTS request, String clientMail)
    {
        switch (request){
            case PRESENCE -> {
                return manDB.getPresenceForUser(clientMail);//Enviar id do evento com o qual quer ver as presenças
            }
            case CSV_PRESENCE -> {
                manDB.generateCSV(clientMail,"csvgenerated.csv");//Enviar id do user com o qual quer imprimir as presenças em csv
            }
            //todo event presence
            case USER_DATA -> {
                return manDB.getUserData(clientMail);
            }
            case EVENTS -> {
                return manDB.getAllEvents();
            }
            case LOGOUT -> {
                this.serverInfo.removeClient(clientMail);
            }
        }

        return RESPONSE.DECLINED;
    }



}
