package server;

import Client.Client;
import communication.Communication;
import communication.Message;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Date;

public class Server implements Communication {
    private final int port;

    public Server(int port){
        this.port = port;
    }
    public static void main (String[] args){
        try{
            Server server = new Server(5099);
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
    public void register(Client client) throws RemoteException {

    }

    @Override
    public void unregister(Client client) throws RemoteException {

    }

    @Override
    public void sendMessage(String text, int idThread) throws RemoteException {
        System.out.println(new Message(0, new Date(), null,text, null));
    }
}
