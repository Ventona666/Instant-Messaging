package server;

import object.Group;
import object.User;
import object.Message;
import object.Thread;
import client.ClientInterface;

import java.net.Inet4Address;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteServer;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

public class Server implements ServerInterface {
    private Map<User, ClientInterface> connectedUsersMap = new HashMap<>();
    private DatabaseInteraction database = new DatabaseInteraction();

    public static void main(String[] args) {
        try {
            int port = 5099;
            Registry registry = LocateRegistry.createRegistry(port);
            ServerInterface stub = (ServerInterface) UnicastRemoteObject.exportObject(new Server(), port);
            registry.bind("ServerInterface", stub);
            System.err.println("Serveur lancé\n\tAdresse IP : " + Inet4Address.getLocalHost().getHostAddress() +
                    "\n\tPort : 5099\n");
        } catch (Exception e) {
            System.err.println("Erreur lancement serveur : " + e);
            e.printStackTrace();
        }
    }

    @Override
    public void register(User user) throws RemoteException {
        try {

            String host = RemoteServer.getClientHost();
            Registry registry = LocateRegistry.getRegistry(host, 5098);
            ClientInterface stubClient = (ClientInterface) registry.lookup("ClientInterface");

            stubClient.ping();
            connectedUsersMap.put(user, stubClient);

            //NavigableSet<Group> groupList = database.getUser(user.getId()).getGroupList();
            //stubClient.update(groupList);

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void unregister(User user) throws RemoteException {
        connectedUsersMap.remove(user);
        System.err.println("Un client s'est déconnecté\n");
        try{
            String clientIp = RemoteServer.getClientHost();
            System.err.println("\tAdresse IP : " + clientIp);
        }
        catch(Exception e){
            System.err.println("\tAdresse IP : " + e);
        }

    }

    @Override
    public void sendMessage(User sender, String text, int idThread) throws RemoteException {
        Thread thread = database.getThread(idThread);
        Message message = new Message(0, new Date(), sender, text, thread);
        System.out.println(new Message(0, new Date(), null, text, null));
    }

    @Override
    public void hasRead(User user, int idMessage) throws RemoteException {
        User sender = database.getMessage(idMessage).getSender();
        ClientInterface stubSender = connectedUsersMap.get(sender);
        stubSender.messageReceive();
    }

    @Override
    public void pong() throws RemoteException {
        try{
            String clientIp = RemoteServer.getClientHost();
            System.err.println("Un nouveau client est correctement connecté\n\tAdresse IP : " + clientIp);
        }
        catch (Exception e){
            System.err.println("Impossible d'obtenir l'ip du client : " + e);
            e.printStackTrace();
        }
    }

    @Override
    public void addThread(String title, Group group) throws RemoteException {

    }

    @Override
    public void deleteThread(int idThread) throws RemoteException {

    }

    @Override
    public String createAccount(String firstName, String lastName, String password1, String password2) throws RemoteException {
        return null;
    }

    @Override
    public void deleteAccount(String userName, String password) throws RemoteException {

    }

    @Override
    public void addToGroup(User user, Group group) throws RemoteException {

    }

    @Override
    public void removeFromGroup(User user, Group group) throws RemoteException {

    }
}
