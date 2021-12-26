package server;

import object.Group;
import object.User;
import object.Message;
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
            System.err.println("Erreur lancement serveur :" + e);
            e.printStackTrace();
        }
    }

    @Override
    public void register(User user) throws RemoteException {
        try {
            String host = RemoteServer.getClientHost();
            Registry registry = LocateRegistry.getRegistry(host, 5098);
            ClientInterface stubClient = (ClientInterface) registry.lookup("ClientInterface");
            connectedUsersMap.put(user, stubClient);
            stubClient.ping(); // TODO à supprimer une fois la phase de test terminée
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
    }

    @Override
    public void sendMessage(User sender, String text, int idThread) throws RemoteException {
        Message message = new Message(0, new Date(), sender, text, null);
        System.out.println(new Message(0, new Date(), null, text, null));
    }

    /* UNUSED
    @Override
    public void hasReceived(User user, int idMessage) throws RemoteException {
        User sender = database.getMessage(idMessage).getSender();
        ClientInterface stubSender = connectedUsersMap.get(sender);
        stubSender.messageReceive();
    } */

    @Override
    public void hasRead(User user, int idMessage) throws RemoteException {
        User sender = database.getMessage(idMessage).getSender();
        ClientInterface stubSender = connectedUsersMap.get(sender);
        stubSender.messageReceive();
    }

    @Override
    public void pong() throws RemoteException {
        System.out.println("Client est toujours la");
    }



}
