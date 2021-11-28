package communication;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Communication extends Remote {
    Message sendMessage(String text, int idThread) throws RemoteException;
}
