package communication;

import Client.Client;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Communication extends Remote {
    void register(Client client) throws RemoteException;
    void unregister(Client client) throws RemoteException;
    void sendMessage(String text, int idThread) throws RemoteException;
    void hasReceived(Client client, int idMessage) throws RemoteException;
    void hasRead(Client client, int idMessage) throws RemoteException;
}
