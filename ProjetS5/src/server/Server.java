package server;

import Client.Group;
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
    private Map<User, ClientInterface> connectedUsersMap = new HashMap<>();

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
            connectedUsersMap.put(user, stubClient);
            NavigableSet<Group> groupList = getGroupList(user);
            stubClient.update(groupList);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void unregister(User user) throws RemoteException {
        connectedUsersMap.remove(user);
    }

    @Override
    public void sendMessage(User sender, String text, int idThread) throws RemoteException {
        Message message = new Message(0, new Date(), sender, text, null);
        System.out.println(new Message(0, new Date(), null,text, null));
    }

    @Override
    public void hasRead(User user, int idMessage) throws RemoteException {
        User sender = getMessage(idMessage).getSender();
        ClientInterface stubSender = connectedUsersMap.get(sender);
        stubSender.messageReceive();
    }

    @Override
    public void pong() throws RemoteException {
        System.out.println("Client est toujours la");
    }

    @Override
    public void hasReceived(User user, int idMessage) throws RemoteException {
        User sender = getMessage(idMessage).getSender();
        ClientInterface stubSender = connectedUsersMap.get(sender);
        stubSender.messageReceive();
    }

    public Thread getThread(int idThread){
        // méthode qui cherche l'information dans la bdd
        return null;
    }

    public Message getMessage(int idMessage){
        // méthode qui cherche l'information dans la bdd
        return null;
    }

    public NavigableSet<Group> getGroupList(User user){
        // méthode qui cherche l'information dans la bdd
        return null;
    }

}
