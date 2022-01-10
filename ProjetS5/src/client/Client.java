package client;

import clientGUI.ClientGUI;
import object.*;
import object.Thread;
import server.ConnexionRefusedException;
import server.ServerInterface;

import java.net.Inet4Address;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.NavigableSet;

public class Client implements ClientInterface{
    private User user = null;
    private ClientGUI clientGUI = new ClientGUI(this);
    private static final int clientPort = 5098;
    private static final int serverPort = 5099;
    private static final String serverIp = "192.168.68.101"; // A changer selon le serveur utilisé
    private static ServerInterface stubServer;

    public void connectingToServer(){
        try {
            // Connexion au serveur
            Registry registry = LocateRegistry.getRegistry(serverIp, serverPort);
            stubServer = (ServerInterface) registry.lookup("ServerInterface");
            System.err.println("Client connecté au serveur avec succès" +
                    "\n\tAdresse Ip client : " + Inet4Address.getLocalHost().getHostAddress() +
                    "\n\tAdresse Ip serveur : " + serverIp +
                    "\n\tPort d'entrée client : " + clientPort +
                    "\n\tPort d'entrée serveur : " + serverPort);

            // Connexion au compte de l'utilisateur
        }
        catch (Exception e){
            System.err.println("Client exception: " + e);
            e.printStackTrace();
        }
    }

    public void bootingClientRegistry(){
        try{
            Registry registryClient = LocateRegistry.createRegistry(clientPort);
            ClientInterface stubClient = (ClientInterface) UnicastRemoteObject.exportObject(new Client(), clientPort);
            registryClient.bind("ClientInterface", stubClient);
            System.err.println("Interface client activée");
        }
        catch (Exception e){
            System.err.println("Client exception: " + e);
            e.printStackTrace();
        }
    }

    public void logIn(String username, String password) throws ConnexionRefusedException{

        try {
            user = stubServer.logIn(username, password);
            user.setStubServer(stubServer);
            System.err.println("Connexion au compte de l'utilisateur réussi");
        }
        catch (RemoteException remoteException){
            System.err.println("Erreur lors de la tentative de connexion : " + remoteException);
            remoteException.printStackTrace();
        }

        clientGUI.build();
    }

    public void logOut(){
        try {
            stubServer.logOut(user);
        }
        catch (Exception e){
            System.err.println("Erreur lors de la deconnexion au serveur : " + e);
            e.printStackTrace();
        }

        user = null;
    }

    public NavigableSet<Group> getAllGroup(){
        NavigableSet<Group> groupSet = null;
        try{
            groupSet = stubServer.getAllGroup();
            System.err.println("Tout les groupes ont été obtenus");
        }
        catch(Exception e){
            System.err.println("Erreur lors de la récupération des groupes :" + e);
            e.printStackTrace();
        }
        return groupSet;
    }

    public NavigableSet<User> getAllUser(){
        NavigableSet<User> userSet = null;
        try{
            userSet = stubServer.getAllUser();
            System.err.println("Tout les utilisateurs ont été obtenus");
        }
        catch(Exception e){
            System.err.println("Erreur lors de la récupération des utilisateurs :" + e);
            e.printStackTrace();
        }
        return userSet;
    }

    public User getUser() {
        return user;
    }

    /* Méthodes utilisées uniquement par le serveur */


    @Override
    public void ping() throws RemoteException {
        try {
            stubServer.pong();
        }
        catch (Exception e){
            System.err.println("Le serveur ne répond pas : " + e);
            e.printStackTrace();
        }
    }

    @Override
    public void messageSendToAllUsers(Message message) throws RemoteException {
        message.setMessageStatus(MessageStatus.RECEIVED_BY_ALL_USERS);
        //TODO update interface graphique
    }

    @Override
    public void messageReadByAllUsers(Message message) throws RemoteException {
        message.setMessageStatus(MessageStatus.READ_BY_ALL_USERS);
        //TODO update interface graphique
    }

    @Override
    public void inCommingMessage(Message message) throws RemoteException {
        // Update local du thread
        long idThread = message.getIdThread();
        clientGUI.updateInterface();
        for(Group group : user.getGroupSet()){
            for(Thread thread : group.getThreadSet()){
                if(thread.getId() == idThread){
                    thread.addMessage(message);
                    break;
                }
            }
        }
    }

    @Override
    public void addToANewGroup(Group group) throws RemoteException {
        user.addGroup(group);
        clientGUI.updateInterface();
    }

    @Override
    public void newThreadCreated(Thread thread) throws RemoteException {
        long idGroup = thread.getIdGroup();

        for(Group g : user.getGroupSet()){
            if (g.getId() == idGroup){
                for(Thread t : g.getThreadSet()){
                    if(t.equals(thread)){
                        t.setMessageList(thread.getMessageList());
                        clientGUI.updateInterface();
                        break;
                    }
                }
            }
        }
    }
}