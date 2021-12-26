package server;

import object.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
    void register(User user) throws RemoteException;
    void unregister(User user) throws RemoteException;
    void sendMessage(User sender, String text, int idThread) throws RemoteException;
    /* UNUSED void hasReceived(User user, int idMessage) throws RemoteException; */
    void hasRead(User user, int idMessage) throws RemoteException;
    void pong() throws RemoteException;
}
