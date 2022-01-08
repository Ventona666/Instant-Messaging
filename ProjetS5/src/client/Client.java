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
import java.util.NavigableSet;
import java.util.Scanner;

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
            System.err.println("Client connecté au serveur avec succès" +
                    "\n\tAdresse Ip client : " + Inet4Address.getLocalHost().getHostAddress() +
                    "\n\tAdresse Ip serveur : " + serverIp +
                    "\n\tPort d'entrée client : " + clientPort +
                    "\n\tPort d'entrée serveur : " + serverPort);

            // Connexion au compte de l'utilisateur
            signIn();
            stubServer.register(user);
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

    private static void signIn(){
        Scanner console = new Scanner(System.in);
        String firstName =  console.nextLine();
        String lastName =  console.nextLine();
        String password1 =  console.nextLine();
        String password2 =  console.nextLine();

        try {
            String username = stubServer.createAccount(firstName, lastName, password1, password2, false);
            System.out.println(username);
            System.err.println("Création du compte réussi");
            user = stubServer.logIn(username, password1);
            System.err.println("Connexion au compte de l'utilisateur réussi");
        }
        catch (Exception e){
            System.err.println("Erreur lors de la création du nouveau compte :" + e);
            e.printStackTrace();
        }
    }

    private static void logIn(){
        //SignInInterface signInInterface = new SignInInterface();
        Scanner console = new Scanner(System.in);
        String username = console.nextLine();
        String password = console.nextLine();

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

    private NavigableSet<Group> getAllGroup(){
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

    private NavigableSet<User> getAllUser(){
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
        long idThread = message.getIdThread();
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
    public void update(NavigableSet<Group> groupList) throws RemoteException {
        user.setGroupSet(groupList);
        //TODO update interface graphique
    }

    @Override
    public void addToANewGroup(Group group) throws RemoteException {
        user.addGroup(group);
    }

    @Override
    public void newThreadCreated(Thread thread) throws RemoteException {
        long idGroup = thread.getIdGroup();

        for(Group g : user.getGroupSet()){
            if (g.getId() == idGroup){
                for(Thread t : g.getThreadSet()){
                    if(t.equals(thread)){
                        t.setMessageList(thread.getMessageList());
                        break;
                    }
                }
            }
        }
    }
}