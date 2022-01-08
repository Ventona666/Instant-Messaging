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
    private Map<Long, ClientInterface> connectedUsersMap = new HashMap<>();
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
    public void register(long idUser) throws RemoteException {
        try {
            String host = RemoteServer.getClientHost();
            Registry registry = LocateRegistry.getRegistry(host, 5098);
            ClientInterface stubClient = (ClientInterface) registry.lookup("ClientInterface");

            stubClient.ping();
            connectedUsersMap.put(idUser, stubClient);

            User user = database.getUser(idUser);
            TreeMap<Group, TreeSet<Thread>> groupTreeSetTreeMap = user.getAllThread();

            for(Group group : groupTreeSetTreeMap.keySet()){
                for(Thread thread : groupTreeSetTreeMap.get(group)){
                    Message lastMessageReceived = database.getReceive(idUser, thread.getId());

                    for(Message message : thread.getMessageList().tailSet(lastMessageReceived, false)){
                        message.incrementNumberOfReceptions(group.getNumberOfMember());
                    }
                }
            }

        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void unregister(long idUser) throws RemoteException {
        connectedUsersMap.remove(idUser);
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
        long idThread = message.getIdThread();
        Thread thread = database.getThread(idThread);

        /// Création d'un groupe virtuel afin d'envoyer le message à tout les users du thread
        long idGroup = thread.getIdGroup();
        Group virtualGroup = database.getGroup(idGroup);
        virtualGroup.addUser(thread.getOwner());

        //TODO méthode pour replace le thread dans la bdd

        for(User user : virtualGroup.getUserSet()){
            if(connectedUsersMap.containsKey(user)){
                try {
                    connectedUsersMap.get(user).inCommingMessage(message);
                    message.incrementNumberOfReceptions(virtualGroup.getNumberOfMember());
                    if(message.getNumberOfReceptions() == virtualGroup.getNumberOfMember()){
                        for(ClientInterface stubClient : connectedUsersMap.values()){
                            stubClient.messageSendToAllUsers(message);
                        }
                    }
                }
                catch (Exception e){
                    System.err.println("Envoi du message impossible à un client" + e);
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void hasRead(long userId, long messageId) throws RemoteException {
        Message message = database.getMessage(messageId);
        Thread thread = database.getThread(message.getIdThread());
        Group group = database.getGroup(thread.getIdGroup());

        message.incrementNumberOfReads(group.getNumberOfMember());
        database.updateRead(userId, messageId);
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
        database.newThread(thread);
        long idGroup = thread.getIdGroup();
        Group virtualGroup = database.getGroup(idGroup);
        virtualGroup.addUser(thread.getOwner());
        for(User user : virtualGroup.getUserSet()){
            if(connectedUsersMap.containsKey(user)){
                try {
                    connectedUsersMap.get(user).newThreadCreated(thread);
                }
                catch (Exception e){
                    System.err.println("Impossible d'informer le client du nouveau thread" + e);
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void closeThread(int idThread) throws RemoteException {
        Thread thread = database.getThread(idThread);
        thread.closeThread();
        //TODO update bdd
    }

    @Override
    public String createAccount(String firstName, String lastName, String password1, String password2, boolean isStaff) throws RemoteException {
        //Generation du pseudo
        User user;
        if(isStaff){
            user = new StaffUser(firstName, lastName);
        }
        else {
            user = new CampusUser(firstName, lastName);
        }

        if(password1.equals(password2)) {
            try {
                database.newUser(user, password1);
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException){
                System.err.println("Erreur lors du hashage du mot de passe : " + noSuchAlgorithmException);
                noSuchAlgorithmException.printStackTrace();
            }
        }

        return user.getUsername();
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
    public Group getGroup(long idGroup) throws RemoteException {
        return database.getGroup(idGroup);
    }

    @Override
    public void createGroup(Group group) throws RemoteException {
        database.newGroup(group);
    }

    @Override
    public void addToGroup(User user, Group group) throws RemoteException {
        group.addUser(user);
        database.newMember(user, group);
        ClientInterface stubClient = connectedUsersMap.get(user);

        stubClient.addToANewGroup(group);
    }

    @Override
    public void removeFromGroup(User user, Group group) throws RemoteException {
        group.removeUser(user);
        //TODO update du groupe dans la bdd
    }

    @Override
    public NavigableSet<Group> getAllGroup() throws RemoteException {
        return database.getAllGroup();
    }

    @Override
    public NavigableSet<User> getAllUser() throws RemoteException {
        return database.getAllUser();
    }
}
