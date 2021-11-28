package server;

import communication.Communication;
import communication.Message;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

public class Server implements Communication {
    public Server(){}


    public static void main (String args[]){
        try{
            Server server = new Server();
            Registry registry = LocateRegistry.createRegistry(5099);
            Communication stub = (Communication) UnicastRemoteObject.exportObject(server,5099);
            registry.bind("Message", stub);

            System.err.println("Serveur prêt !");
        } catch (Exception e){
            System.err.println("Erreur lancement serveur :" + e.toString());
            e.printStackTrace();
        }
    }


    @Override
    public Message sendMessage(String text, int idThread) throws RemoteException {
        return new Message(0, new Date(), 0,text);
    }
}
