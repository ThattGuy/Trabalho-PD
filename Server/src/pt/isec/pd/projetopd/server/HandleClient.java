package pt.isec.pd.projetopd.server;

import pt.isec.pd.projetopd.server.HeartBeat.SendHBeat;
import pt.isec.pd.projetopd.server.data.Authentication;
import pt.isec.pd.projetopd.server.data.RESPONSE;

import java.io.*;
import java.net.Socket;

public class HandleClient implements Runnable {

    private Socket socket;
    private ServerInfo serverInfo;

    public HandleClient(Socket sock, ServerInfo serverInfo) {
        this.socket = sock;
        this.serverInfo = serverInfo;
    }

    @Override
    public void run() {
        System.out.println("I started");
        do{
            try(ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())){


                Object o = in.readObject();

                if(serverInfo.updateDB(o))
                    out.writeObject(RESPONSE.ACCEPTED);
                 else
                    out.writeObject(RESPONSE.DECLINED);

                out.flush();
                out.reset();

            }catch(ClassNotFoundException | IOException e){
                System.out.println("<" + Thread.currentThread().getName() + ">:\n\t" + e);
            }finally{
                try{
                    if(socket != null) socket.close();
                }catch(IOException e){}
            }
        } while (true);
    }
}
