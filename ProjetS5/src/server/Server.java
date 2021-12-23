package server;

import Client.User;
import communication.ServerInterface;
import communication.Message;
import Client.ClientInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Server implements ServerInterface {
    private final int port;
    private Map<User, ClientInterface> userMap = new HashMap<>();

    public Server(int port){
        this.port = port;
    }

    public static void main (String[] args){
        try{
            Server server = new Server(5099);
            Registry registry = LocateRegistry.createRegistry(5099);
            ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject(server,5099);
            registry.bind("Message", stub);
            System.err.println("Serveur prêt !");
        } catch (Exception e){
            System.err.println("Erreur lancement serveur :" + e.toString());
            e.printStackTrace();
        }
    }


    @Override
    public void register(User user) throws RemoteException {
        try{
            Registry registry = LocateRegistry.getRegistry(user.getIpAddress(), 5098);
            ClientInterface stubClient = (ClientInterface) registry.lookup("ClientInterface");
            userMap.put(user, stubClient);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void unregister(User user) throws RemoteException {
        userMap.remove(user);
    }

    @Override
    public void sendMessage(String text, int idThread) throws RemoteException {
        System.out.println(new Message(0, new Date(), null,text, null));
    }

    @Override
    public void hasRead(User user, int idMessage) throws RemoteException {

    }

    @Override
    public void pong() throws RemoteException {
        System.out.println("Client est toujours la");
    }

    @Override
    public void hasReceived(User user, int idMessage) throws RemoteException {

    }
}
