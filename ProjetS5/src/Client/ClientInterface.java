package Client;

import communication.Message;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.NavigableSet;

public interface ClientInterface extends Remote {
    void ping() throws RemoteException;
    void messageReceive() throws RemoteException;
    void messageSendToUsers() throws RemoteException;
    void messageReadByUsers() throws RemoteException;
    void inCommingMessage(Message message) throws RemoteException;
    void update(NavigableSet<Group> groupList) throws RemoteException;
}