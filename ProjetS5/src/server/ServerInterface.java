package server;

import object.Group;
import object.Message;
import object.User;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {
    void register(User user) throws RemoteException;
    void unregister(User user) throws RemoteException;
    void sendMessage(Message message) throws RemoteException;
    void hasRead(User user, int idMessage) throws RemoteException;
    void pong() throws RemoteException;
    void newThread(String title, Group group) throws RemoteException;
    void closeThread(int idThread) throws RemoteException;
    String createAccount(String firstName, String lastName, String password1, String password2) throws RemoteException;
    void deleteAccount(String userName, String password) throws RemoteException;
    User logIn(String username, String password) throws RemoteException, ConnexionRefusedException;
    void logOut() throws  RemoteException;
    void addToGroup(User user, Group group) throws RemoteException;
    void removeFromGroup(User user, Group group) throws RemoteException;
}
