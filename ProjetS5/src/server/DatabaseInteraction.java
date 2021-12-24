package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.NavigableSet;

import object.Group;
import object.Message;
import object.User;

public class DatabaseInteraction {

    static final String DB_URL = "jdbc:mysql://51.77.148.156/projet";
    static final String USER = "projet";
    static final String PASS = "ProjetS5!";

    public DatabaseInteraction() {

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
