package client;

import object.Thread;
import object.Group;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.NavigableSet;

public interface ClientInterface extends Remote {
    void ping() throws RemoteException;
    void messageReceive() throws RemoteException;
    void messageSendToUsers() throws RemoteException;
    void messageReadByUsers() throws RemoteException;
    void inCommingMessage(Thread thread) throws RemoteException;
    void update(NavigableSet<Group> groupList) throws RemoteException;
}