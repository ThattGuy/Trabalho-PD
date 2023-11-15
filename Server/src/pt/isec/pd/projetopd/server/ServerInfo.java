package pt.isec.pd.projetopd.server;

import pt.isec.pd.projetopd.communication.classes.Authentication;
import pt.isec.pd.projetopd.communication.classes.RESPONSE;
import pt.isec.pd.projetopd.communication.classes.User;
import pt.isec.pd.projetopd.server.HeartBeat.SendHBeat;

import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ServerInfo {
    private Map<String, ObjectOutputStream> clientsList;
    private int nTCPConnections;
    private int databaseVersion;
    private SendHBeat sendHBeat;
    private NotificationThread notifications;


    public ServerInfo(SendHBeat sendHBeat, NotificationThread notificationThread){
        this.clientsList = new HashMap<>();
        this.nTCPConnections = 0;
        this.sendHBeat = sendHBeat;
        this.notifications = notificationThread;
    }

    public int getnTCPConnections() {
        return nTCPConnections;
    }
    public int getDBVersion() {
        return this.databaseVersion;
    }

    public void addClient(String mail, ObjectOutputStream out) {
        this.clientsList.put(mail,out);
        this.nTCPConnections++;
        //this.sendHBeat.SendHeartBeat(databaseVersion);
    }
    public void sendNotification(Object data, ObjectOutputStream out){
        this.notifications.sendNotifications(data);
    }

  /*  public Serializable updateDB(Object o, ObjectOutputStream out) {
        Serializable aux = handleRequests.receive(o);


        if(o instanceof Authentication && aux instanceof User  ||
           o instanceof User && aux instanceof RESPONSE
        ) //Check if new client connecting
        {
            addClient(((Authentication) o).getUsername(), out);
        }

        else if(aux instanceof RESPONSE)
            if(aux.equals(RESPONSE.DECLINED))
                return aux;
        else
        {
            String mail = getClientMail(out);
            this.notifications.sendNotifications(mail, aux);
        }

        return aux;
    }

   */

    public String getClientMail(ObjectOutputStream out) {
        for (Map.Entry<String, ObjectOutputStream> entry : clientsList.entrySet()) {
            if(entry.getValue() == out)
                return entry.getKey();
        }
        return null;
    }

}
