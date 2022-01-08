package client;

import object.User;

import java.awt.event.WindowListener;

public class ClientManager {
    //Classe permettant de gérer la partie graphique et la partie connexion

    private static final Client client = new Client();

    public static void main(String[] args) {
        // Connexion au serveur
        client.bootingClientRegistry();
        client.connectingToServer();
        // Ouverture de l'interface graphique de connexion
        LogInGUI logInGUI = new LogInGUI(client);
        logInGUI.build();
    }
}
