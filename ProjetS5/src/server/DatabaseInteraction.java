package server;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NavigableSet;

import object.Group;
import object.Message;
import object.User;
import object.Thread;
public class DatabaseInteraction {

    static final String DB_URL = "jdbc:mysql://51.77.148.156/projet";
    static final String USER = "projet";
    static final String PASS = "ProjetS5!";

    public DatabaseInteraction() {

    }

    public User logIn(String username, String password) throws NoSuchAlgorithmException, ConnexionRefusedException {
        // Hashage du mot de passe
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));

        /* TODO Recherche de l'user dans la bdd avec les parametres
        * - Throw a new ConnexionRefusedException s'il n'y a pas de correspondance
        * - Sinon on return un campusUser ou staffUser en fonction du type sauvegardé dans la bdd */

        return null;
    }

    public Thread getThread(int idThread) {
        // méthode qui cherche l'information dans la bdd
        return null;
    }

    public Message getMessage(int idMessage) {
        // méthode qui cherche l'information dans la bdd
        return null;
    }

    public User getUser(int idUser) {
        // méthode qui cherche l'information dans la bdd
        return null;
    }

}
