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
                return manDB.registerEvent(event.getName(), event.getLocation(), event.getDate(), event.getBeginning(), event.getEndTime(), ClientMail);
            }
            case EventPresence eventPresence -> {
                return (Serializable) manDB.getEventPresence(eventPresence.getEvent().getName());
            }
            case CreateCode eventCode-> {
                //TODO FRANCISCO obter o último código do evento, verificar se ele já existe, se não existir adicioná-lo ao banco de dados, retornar erros se houver, caso não haja erros adicionar o UUID e retornar o evento
                return manDB.createCode(eventCode.getEventName(),eventCode.getEventCode().getCode(),eventCode.getEventCode().getExpirationTime());
            }
            case UUID code -> {
                return manDB.registerPresence(code, ClientMail);
            }
            case EditedEvent editedEvent-> {
                return null;//manDB.editEvent(editedEvent.getEvent(), editedEvent.getOldName()); //TODO FRANCISCO verificar se o evento existe, se existir editar e retornar o evento, se não existir retornar erro
            }

            default -> {
                return RESPONSE.DECLINED;
            }
        }

    }

    public Serializable receive(Object request, ObjectOutputStream Clientout){
        String mail =this.serverInfo.getClientMail(Clientout);
        Serializable dbResponse = this.InterpretClientMessage(request, mail);

        if(request instanceof Authentication && dbResponse instanceof User ) //Check if new client connecting
        {
            this.serverInfo.addClient(((Authentication) request).getUsername(), Clientout);
        }
        else
            if(dbResponse instanceof RESPONSE && dbResponse.equals(RESPONSE.DECLINED)) //Client operation declined
                return dbResponse;

            else  if(dbResponse instanceof Event)//TODO: Perceber em que casos é que se deve enviar notificações
            {
                this.serverInfo.sendNotification(dbResponse, Clientout);
            }

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
        }

        return RESPONSE.DECLINED;
    }
}
