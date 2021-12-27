package client;

import object.Message;
import server.ServerInterface;
import object.Thread;
import object.Group;
import object.User;

import java.net.Inet4Address;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.NavigableSet;

public class Client implements ClientInterface{
    private User user;
    private static final int clientPort = 5098;
    private static final int serverPort = 5099;
    private static final String serverIp = "192.168.68.102"; // A changer selon le serveur utilisé
    private static ServerInterface stubServer;
    private static ClientInterface stubClient;

    private static void connectingToServer(){
        try {
            Registry registry = LocateRegistry.getRegistry(serverIp, serverPort);
            stubServer = (ServerInterface) registry.lookup("ServerInterface");
            stubServer.register(new User("tony", "defreitas"));
            System.err.println("Client connecté au serveur avec succès" +
                    "\n\tAdresse Ip client : " + Inet4Address.getLocalHost().getHostAddress() +
                    "\n\tAdresse Ip serveur : " + serverIp +
                    "\n\tPort d'entrée client : " + clientPort +
                    "\n\tPort d'entrée serveur : " + serverPort);
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
    public static void main(String[] args) {
        bootingClientRegistry();
        connectingToServer();
    }

    @Override
    public void ping() throws RemoteException {
        stubServer.pong();
    }

    @Override
    public void messageReceive() throws RemoteException {

    }

    @Override
    public void messageSendToUsers() throws RemoteException {

    }

    @Override
    public void messageReadByUsers() throws RemoteException {

    }

    @Override
    public void inCommingMessage(Message message) throws RemoteException {


    }

    @Override
    public void update(NavigableSet<Group> groupList) throws RemoteException {

    }
}
