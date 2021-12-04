package Client;

import communication.Communication;
import communication.Message;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.NavigableSet;

public class Client implements ClientInterface{
    private Client(){}

    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        try {
            Client client = new Client();
            Registry registryClient = LocateRegistry.createRegistry(5098);
            ClientInterface stubClient = (ClientInterface) UnicastRemoteObject.exportObject(client, 5098);
            registryClient.bind("ClientInterface", stubClient);

            Registry registry = LocateRegistry.getRegistry(5099);
            Communication stub = (Communication) registry.lookup("Message");
            stub.register(new User("tony", "defreitas", "192.168.68.102"));
            stub.sendMessage("Salut je m'appelle Omega !", 0);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }

    @Override
    public void ping() throws RemoteException {
        System.out.println("Ca marche !");
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
