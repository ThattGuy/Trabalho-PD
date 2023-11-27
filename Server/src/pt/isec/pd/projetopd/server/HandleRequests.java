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

    private synchronized Serializable InterpretClientMessage(Object request, String clientMail) {
        System.out.println(request.toString());

        Boolean isReturn = null;
        Serializable dbresponse = null;
        switch (request) {
            case REQUESTS requests -> {
                return this.InterpretRequest(requests, clientMail);
            }
            case Authentication auth -> {
                //nao
                return manDB.CheckLogin(auth.getUsername(), auth.getPassword());
            }
            case User clientInfo -> {
                //nao
                 return manDB.register(clientInfo.getUsername(), clientInfo.getPassword(), clientInfo.getName(), clientInfo.getStudentNumber(), clientInfo.getNIF(), clientInfo.getId(), clientInfo.getAddress(), false);
            }
            case EditUser editUser -> {
                //nao
                User clientInfo = editUser.getUser();
                return manDB.editDataUser(clientInfo.getUsername(), clientInfo.getPassword(), clientInfo.getName(), clientInfo.getStudentNumber(), clientInfo.getNIF(), clientInfo.getId(), clientInfo.getAddress());
            }

            case Event event -> {

                dbresponse =  manDB.registerEvent(event.getName(), event.getLocation(), event.getDate(), event.getBeginning(), event.getEndTime(),event.getPresenceCodes().get(0), clientMail);
                this.serverInfo.sendNotification(dbresponse);
                return dbresponse;
            }
            case EventPresenceRequest eventPresenceRequest -> {
                //sim
                dbresponse =  manDB.getEventPresence(eventPresenceRequest.getEvent().getName());
                this.serverInfo.sendNotification(dbresponse);
                return dbresponse;
            }
            case CreateCode eventCode-> {
                //sim
                dbresponse = manDB.createCode(eventCode.getEventName(),eventCode.getEventCode().getCode(),eventCode.getEventCode().getExpirationTime());
                this.serverInfo.sendNotification(dbresponse);
                return dbresponse;
            }
            case UUID code -> {
                //nao
                dbresponse = manDB.registerPresence(code, clientMail);//TODO mudar para o mail do cliente pois está a receber null
                this.serverInfo.sendNotification(dbresponse);
                return dbresponse;
            }
            case EditedEvent editedEvent-> {
                //sim
                dbresponse = manDB.editEvent(editedEvent.getEvent(), editedEvent.getOldName());
                this.serverInfo.sendNotification(dbresponse);
                return dbresponse;
            }
            case CSVEventPresence eventPresence-> {
                //nao
                return manDB.generateEventCSV(eventPresence.getEvent().getName(), "csveventgenerated.csv");


            }
            default -> {
                return RESPONSE.DECLINED;
            }
        }
    }

    public synchronized Serializable receive(Object request, ObjectOutputStream Clientout){
        String mail =this.serverInfo.getClientMail(Clientout);
        Serializable dbResponse = this.InterpretClientMessage(request, mail);


        //Check if new client connecting or registring
        if(request instanceof Authentication && dbResponse instanceof User) {
            this.serverInfo.addClient(((Authentication) request).getUsername(), Clientout);
            return dbResponse;
        }

          else
              if( request instanceof User && dbResponse instanceof User)
                    this.serverInfo.addClient(((User) request).getUsername(), Clientout);

        else
            if(dbResponse instanceof RESPONSE && dbResponse.equals(RESPONSE.DECLINED)
              || request instanceof REQUESTS || request instanceof CSVEventPresence) //Client operation declined
                return dbResponse;

        else serverInfo.updateBackup(dbResponse);

        System.out.println("This is my version: " + this.serverInfo.getDBVersion());

        return dbResponse;
    }


    private synchronized Serializable InterpretRequest(REQUESTS request, String clientMail)
    {
        switch (request){
            case PRESENCE -> {
                return manDB.getPresenceForUser(clientMail);//Enviar id do evento com o qual quer ver as presenças
            }
            case CSV_PRESENCE -> {
                return manDB.generateCSV(clientMail,"csvgenerated.csv");//Enviar id do user com o qual quer imprimir as presenças em csv
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
