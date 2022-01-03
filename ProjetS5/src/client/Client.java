package client;

import object.*;
import object.Thread;
import server.ConnexionRefusedException;
import server.ServerInterface;

import java.net.Inet4Address;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.NavigableSet;

public class Client implements ClientInterface{
    private static User user = null;
    private static final int clientPort = 5098;
    private static final int serverPort = 5099;
    private static final String serverIp = "192.168.68.102"; // A changer selon le serveur utilisé
    private static ServerInterface stubServer;
    private static ClientInterface stubClient;

    private static void connectingToServer(){
        try {
            // Connexion au serveur
            Registry registry = LocateRegistry.getRegistry(serverIp, serverPort);
            stubServer = (ServerInterface) registry.lookup("ServerInterface");
            user = new CampusUser("tony", "defreitas");
            stubServer.register(user); // TODO pas normal de se connecter avec un user, associé stubClient et user directement
            System.err.println("Client connecté au serveur avec succès" +
                    "\n\tAdresse Ip client : " + Inet4Address.getLocalHost().getHostAddress() +
                    "\n\tAdresse Ip serveur : " + serverIp +
                    "\n\tPort d'entrée client : " + clientPort +
                    "\n\tPort d'entrée serveur : " + serverPort);

            // Connexion au compte de l'utilisateur
            logIn();
        }
        catch (Exception e){
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    private static void bootingClientRegistry(){
        try{
            Registry registryClient = LocateRegistry.createRegistry(clientPort);
            stubClient = (ClientInterface) UnicastRemoteObject.exportObject(new Client(), clientPort);
            registryClient.bind("ClientInterface", stubClient);
            System.err.println("Interface client activée");
        }
        catch (Exception e){
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    private static void logIn(){
        SignInInterface signInInterface= new SignInInterface();
        signInInterface.build();

        String username = " ";
        String password = " ";

        try {
            user = stubServer.logIn(username, password);
            System.err.println("Connexion au compte de l'utilisateur réussi");
        }
        catch (ConnexionRefusedException connexionRefusedException){
            System.err.println(connexionRefusedException + " : Username et/ou mot de passe incorrect(s)");
        }
        catch (Exception e){
            System.err.println("Erreur lors de la tentative de connexion : " + e);
            e.printStackTrace();
        }
    }

    private void logOut(){
        //TODO fermer l'interface graphique du client

        try {
            stubServer.logOut(user);
        }
        catch (Exception e){
            System.err.println("Erreur lors de la deconnexion au serveur : " + e);
            e.printStackTrace();
        }

        user = null;

        //Ré ouverture de l'interface de connexion
        logIn();
    }

    public static void main(String[] args) {
        bootingClientRegistry();
        connectingToServer();
    }

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
        Thread thread = message.getThread();
        Group group = thread.getGroup();

        for(Group g : user.getGroupSet()){
            if(g.equals(group)){
                group = g;
                break;
            }
        }

        group.getThreadSet().remove(thread);
        thread.addMessageList(message);
        group.addThread(thread);
    }

    @Override
    public void update(NavigableSet<Group> groupList) throws RemoteException {
        user.setGroupSet(groupList);
        //TODO update interface graphique
    }
}