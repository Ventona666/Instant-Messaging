package Client;

import communication.ServerInterface;
import communication.Message;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.NavigableSet;

public class Client implements ClientInterface{
    private Client(){}
    private static ServerInterface stubServer;
    private static ClientInterface stubClient;

    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        try {
            Client client = new Client();
            Registry registryClient = LocateRegistry.createRegistry(5098);
            stubClient = (ClientInterface) UnicastRemoteObject.exportObject(client, 5098);
            registryClient.bind("ClientInterface", stubClient);

            Registry registry = LocateRegistry.getRegistry(5099);
            stubServer = (ServerInterface) registry.lookup("Message");
            stubServer.register(new User("tony", "defreitas", "192.168.68.102"));
            stubServer.sendMessage("Salut je m'appelle Omega !", 0);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
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
