package server;

import object.Group;
import object.Message;
import object.Thread;
import object.User;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.NavigableSet;

public interface ServerInterface extends Remote {
    void register(User user) throws RemoteException;
    void unregister(long idUser) throws RemoteException;
    void sendMessage(Message message) throws RemoteException;
    void hasRead(long userId, long messageId) throws RemoteException;
    void pong() throws RemoteException;
    void newThread(Thread thread) throws RemoteException;
    void closeThread(int idThread) throws RemoteException;
    User logIn(String username, String password) throws RemoteException, ConnexionRefusedException;
    void logOut(User user) throws  RemoteException;
    Group getGroup(long idGroup) throws RemoteException;
    void createGroup(Group group) throws RemoteException;
    void addToGroup(User user, Group group) throws RemoteException;
    void removeFromGroup(User user, Group group) throws RemoteException;
    NavigableSet<Group> getAllGroup() throws RemoteException;
    NavigableSet<User> getAllUser() throws RemoteException;
}
