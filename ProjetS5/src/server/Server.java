package server;

import object.*;
import client.ClientInterface;
import object.Thread;

import java.net.Inet4Address;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.RemoteServer;
import java.rmi.server.UnicastRemoteObject;
import java.security.NoSuchAlgorithmException;
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
    public void sendMessage(Message message) throws RemoteException {
        Thread thread = message.getThread();

        /// Création d'un groupe virtuel afin d'envoyer le message à tout les users du thread
        Group virtualGroup = thread.getGroup();
        virtualGroup.addUser(thread.getOwner());

        //TODO méthode pour replace le thread dans la bdd

        for(User user : virtualGroup.getUserSet()){
            if(connectedUsersMap.containsKey(user)){
                try {
                    connectedUsersMap.get(user).inCommingMessage(message);
                    message.incrementNumberOfReceptions();
                }
                catch (Exception e){
                    System.err.println("Envoi du message impossible à un client" + e);
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void hasRead(User user, int idMessage) throws RemoteException {
        Message message = database.getMessage(idMessage);
        message.incrementNumberOfReads();
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
    public void newThread(Thread thread) throws RemoteException {
        //TODO update Bdd
    }

    @Override
    public void closeThread(int idThread) throws RemoteException {
        Thread thread = database.getThread(idThread);
        thread.closeThread();
        //TODO update bdd
    }

    @Override
    public String createAccount(String firstName, String lastName, String password1, String password2) throws RemoteException {
        //Generation du pseudo
        char[] twoChars = new char[2];
        lastName.getChars(0,2, twoChars, 0);
        String username = firstName + twoChars[0] + twoChars[1];

        // TODO envoi des elements dans la bdd


        return username;
    }

    @Override
    public void deleteAccount(String userName, String password) throws RemoteException {
        //TODO suppression dans la bdd
    }

    @Override
    public User logIn(String username, String password) throws RemoteException {
        try{
            return database.logIn(username, password);
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException){
            System.err.println("Erreur lors du hashage du mot de passe : " + noSuchAlgorithmException);
            noSuchAlgorithmException.printStackTrace();
        }
        catch (ConnexionRefusedException connexionRefusedException){
            System.err.println("Mot de passe et/ou username incorrect : " + connexionRefusedException);
        }
        return null;
    }

    @Override
    public void logOut(User user) throws RemoteException{
        connectedUsersMap.remove(user);
    }

    @Override
    public void addToGroup(User user, Group group) throws RemoteException {
        group.addUser(user);
        //TODO update du groupe dans la bdd
    }

    @Override
    public void removeFromGroup(User user, Group group) throws RemoteException {
        group.removeUser(user);
        //TODO update du groupe dans la bdd
    }
}
