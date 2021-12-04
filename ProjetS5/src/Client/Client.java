package Client;

import communication.Communication;
import communication.Message;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    private Client(){}

    public static void main(String[] args) {

        String host = (args.length < 1) ? null : args[0];
        try {
            Registry registry = LocateRegistry.getRegistry(5099);
            Communication stub = (Communication) registry.lookup("Message");
            stub.sendMessage("Salut je m'appelle Omega !", 0);
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
}
