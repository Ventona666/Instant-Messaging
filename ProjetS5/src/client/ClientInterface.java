package client;

import object.Message;
import object.Group;
import object.Thread;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.NavigableSet;

public interface ClientInterface extends Remote {
    void ping() throws RemoteException;
    void messageSendToAllUsers(Message message) throws RemoteException;
    void messageReadByAllUsers(Message message) throws RemoteException;
    void inCommingMessage(Message message) throws RemoteException;
    void update(NavigableSet<Group> groupList) throws RemoteException;
    void addToANewGroup(Group group) throws RemoteException;
    void newThreadCreated(Thread thread) throws RemoteException;
}