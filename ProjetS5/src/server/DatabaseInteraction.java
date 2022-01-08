package server;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import com.sun.source.tree.Tree;
import object.CampusUser;
import object.Group;
import object.Message;
import object.MessageStatus;
import object.StaffUser;
import object.User;
import object.Thread;

public class DatabaseInteraction {

    private String dbUrl;
    private static final String DB_IP = "51.77.148.156";
    private static final String DB_PROT = "jdbc:mysql:";
    private static final String DB_PORT = "3306";
    private static final String DB_NAME = "projet";
    private static final String DB_USER = "projet";
    private static final String DB_PASS = "ProjetS5!";

    public DatabaseInteraction() {
        dbUrl = DB_PROT + "//" + DB_IP + ":" + DB_PORT + "/" + DB_NAME;
        initialisation();
    }

    public void initialisation() {
        List<String> tablesCreateList = new ArrayList<String>();
        tablesCreateList.add(
                "CREATE TABLE IF NOT EXISTS GroupT (idGroup BIGINT, nameGroup VARCHAR(255), numberOfMember BIGINT, PRIMARY KEY (idGroup));");
        tablesCreateList.add(
                "CREATE TABLE IF NOT EXISTS UserT (idUser BIGINT, passwordUser VARCHAR(255), firstNameUser VARCHAR(255), lastNameUser VARCHAR(255), username VARCHAR(255), typeUser VARCHAR(20), PRIMARY KEY (idUser));");
        tablesCreateList.add(
                "CREATE TABLE IF NOT EXISTS ThreadT (idThread BIGINT, titleThread VARCHAR(255), idUser BIGINT, idGroup BIGINT, PRIMARY KEY (idThread), FOREIGN KEY (idUser) REFERENCES UserT (idUser) ON DELETE CASCADE, FOREIGN KEY (idGroup) REFERENCES GroupT (idGroup) ON DELETE CASCADE);");
        tablesCreateList.add(
                "CREATE TABLE IF NOT EXISTS MessageT (idMessage BIGINT, dateMessage DATETIME, textMessage MEDIUMTEXT, nbReMessage INT, nbRdMessage INT, statusMessage TINYINT, idUser BIGINT, idThread BIGINT, PRIMARY KEY (idMessage), FOREIGN KEY (idUser) REFERENCES UserT (idUser) ON DELETE CASCADE, FOREIGN KEY (idThread) REFERENCES ThreadT (idThread) ON DELETE CASCADE);");
        tablesCreateList.add(
                "CREATE TABLE IF NOT EXISTS MemberT (idUser BIGINT, idGroup BIGINT, PRIMARY KEY (idUser, idGroup), FOREIGN KEY (idUser) REFERENCES UserT (idUser) ON DELETE CASCADE, FOREIGN KEY (idGroup) REFERENCES GroupT (idGroup) ON DELETE CASCADE);");
        tablesCreateList.add(
                "CREATE TABLE IF NOT EXISTS ReadT (idUser BIGINT, idThread BIGINT, idMessage BIGINT, PRIMARY KEY (idUser, idThread), FOREIGN KEY (idUser) REFERENCES UserT (idUser) ON DELETE CASCADE, FOREIGN KEY (idThread) REFERENCES ThreadT (idThread) ON DELETE CASCADE);");
        tablesCreateList.add(
                "CREATE TABLE IF NOT EXISTS ReceiveT (idUser BIGINT, idThread BIGINT, idMessage BIGINT, PRIMARY KEY (idUser, idThread), FOREIGN KEY (idUser) REFERENCES UserT (idUser) ON DELETE CASCADE, FOREIGN KEY (idThread) REFERENCES ThreadT (idThread) ON DELETE CASCADE);");

        for (String sql : tablesCreateList) {
            try (Connection con = DriverManager.getConnection(dbUrl, DB_USER, DB_PASS);
                 Statement stmt = con.createStatement();) {
                stmt.executeUpdate(sql);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public User logIn(String username, String password) throws NoSuchAlgorithmException, ConnexionRefusedException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        String passwordEnter = new String(hash, StandardCharsets.UTF_8);
        String req = "SELECT passwordUser, idUser FROM UserT WHERE username=" + "\"" + username + "\"";
        System.out.println(req);
        try (Connection con = DriverManager.getConnection(dbUrl, DB_USER, DB_PASS);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(req);) {
            rs.next();
            if (passwordEnter.equals(rs.getString("passwordUser")))
                return getUser(rs.getLong("idUser"));
            else
                throw new ConnexionRefusedException("Identifiant ou mot de passe invalide");
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Thread getThread(long idThread) {
        String req = "SELECT * FROM ThreadT WHERE idThread=" + idThread;
        try (Connection con = DriverManager.getConnection(dbUrl, DB_USER, DB_PASS);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(req);) {
            rs.next();
            long idGroup = rs.getLong("idGroup");
            User user = getUser(rs.getLong("idUser"));
            return new Thread(idThread, rs.getString("titleThread"), user, idGroup);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void newThread(Thread thread) {
        String req = "INSERT INTO ThreadT VALUES (" + thread.getId() + ", '" + thread.getTitle() + "', "
                + thread.getOwner().getId() + ", " + thread.getIdGroup() + ")";
        try (Connection con = DriverManager.getConnection(dbUrl, DB_USER, DB_PASS);
             Statement stmt = con.createStatement();) {
            stmt.executeUpdate(req);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Message getMessage(long idMessage) {
        String req = "SELECT * FROM MessageT WHERE id=" + idMessage;
        try (Connection con = DriverManager.getConnection(dbUrl, DB_USER, DB_PASS);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(req);) {
            rs.next();
            long idSender = rs.getLong("idSender");
            long idThread = rs.getLong("idThread");
            Message message = new Message(idMessage, rs.getDate("dateMessage"), idSender, rs.getString("textMessage"), idThread);
            message.setMessageStatus(MessageStatus.values()[rs.getInt("statusMessage")]);
            message.setNumberOfReceptions(rs.getInt("nbReMessage"));
            message.setNumberOfReads(rs.getInt("nbRdMessage"));
            return message;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void newMessage(Message message) {
        String sql = "INSERT INTO MessageT VALUES (" + message.getId() + ", '" + message.getDate() + "', "
                + message.getIdSender() + ", '" + message.getText() + "', " + message.getNumberOfReads()
                + ", " + message.getNumberOfReceptions() + ", " + message.getMessageStatus().ordinal()
                + ", " + message.getIdThread() + ")";
        try (Connection con = DriverManager.getConnection(dbUrl, DB_USER, DB_PASS);
             Statement stmt = con.createStatement();) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateMessage(Message message) {
        String sql = "UPDATE MessageT WHERE idMessage=" + message.getId() + " SET nbReMessage="
                + message.getNumberOfReceptions() + ", nbRdMessage=" + message.getNumberOfReads() + ", statusMessage="
                + message.getMessageStatus().ordinal();
        try (Connection con = DriverManager.getConnection(dbUrl, DB_USER, DB_PASS);
             Statement stmt = con.createStatement();) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Renvoie le dernier message lu
    public Message getRead(long idUser, long idThread) {
        String req = "SELECT idMessage FROM ReadT WHERE idUser=" + idUser + " AND idThread=" + idThread;
        try (Connection con = DriverManager.getConnection(dbUrl, DB_USER, DB_PASS);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(req);) {
            rs.next();
            return getMessage(rs.getLong("idMessage"));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateRead(long idUser, long idMessage) {
        long idThread = getMessage(idMessage).getIdThread();
        String sql = "UPDATE ReadT WHERE idUser=" + idUser + " AND idThread=" + idThread + " SET idMessage="
                + idMessage;
        try (Connection con = DriverManager.getConnection(dbUrl, DB_USER, DB_PASS);
             Statement stmt = con.createStatement();) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void newRead(long idUser, long idThread, long idMessage) {
        String sql = "INSERT INTO ReadT VALUES (" + idUser + "," + idThread + "," + idMessage + ")";
        try (Connection con = DriverManager.getConnection(dbUrl, DB_USER, DB_PASS);
             Statement stmt = con.createStatement();) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Message getReceive(long idUser, long idThread) {
        String req = "SELECT idMessage FROM ReceiveT WHERE idUser=" + idUser + " AND idThread=" + idThread;
        try (Connection con = DriverManager.getConnection(dbUrl, DB_USER, DB_PASS);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(req);) {
            return getMessage(rs.getLong("idMessage"));
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void updateReceive(User user, Thread thread, Message message) {
        String sql = "UPDATE ReceiveT WHERE idUser=" + user.getId() + " AND idThread=" + thread.getId()
                + " SET idMessage="
                + message.getId();
        try (Connection con = DriverManager.getConnection(dbUrl, DB_USER, DB_PASS);
             Statement stmt = con.createStatement();) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void newReceive(long idUser, long idThread, long idMessage) {
        String sql = "INSERT INTO ReceiveT VALUES (" + idUser + "," + idThread + "," + idMessage + ")";
        try (Connection con = DriverManager.getConnection(dbUrl, DB_USER, DB_PASS);
             Statement stmt = con.createStatement();) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUser(long idUser) {
        User user;
        String req = "SELECT * FROM UserT WHERE idUser=" + idUser;
        try (Connection con = DriverManager.getConnection(dbUrl, DB_USER, DB_PASS);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(req);) {
            rs.next();
            String firstName = rs.getString("firstNameUser");
            String lastName = rs.getString("lastNameUser");
            String username = rs.getString("username");
            if (rs.getString("typeUser").equals("campus"))
                user = new CampusUser(idUser, firstName, lastName, username);
            else
                user = new StaffUser(idUser, firstName, lastName, username);
            user.setGroupSet(getGroupUser(idUser));
            user.setGroupThreadSetMap(getThreadOwner(idUser));
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void newUser(User user, String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        password = new String(hash, StandardCharsets.UTF_8);

        String typeUser = user instanceof CampusUser ? "campus" : "staff";
        String req = "INSERT INTO UserT VALUES (" + user.getId() + ", '" + password + "', '" + user.getFirstName()
                + "', '"
                + user.getLastName() + "', '" + user.getUsername() + "', '"
                + typeUser + "')";
        try (Connection con = DriverManager.getConnection(dbUrl, DB_USER, DB_PASS);
             Statement stmt = con.createStatement();) {
            stmt.executeUpdate(req);
            for (Group group : user.getGroupSet()) {
                newMember(user, group);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void newMember(User user, Group group) {
        String req = "INSERT INTO MemberT VALUES (" + user.getId() + ", " + group.getId() + ")";
        try (Connection con = DriverManager.getConnection(dbUrl, DB_USER, DB_PASS);
             Statement stmt = con.createStatement();) {
            stmt.executeUpdate(req);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void newGroup(Group group){
        String req = "INSERT INTO GroupT VALUES (" + group.getId() + ", '" + group.getName() + "')";
        try (Connection con = DriverManager.getConnection(dbUrl, DB_USER, DB_PASS);
             Statement stmt = con.createStatement();) {
            stmt.executeUpdate(req);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private NavigableSet<Group> getGroupUser(long idUser) {
        NavigableSet<Group> listGroup = new TreeSet<>();
        String req = "SELECT idGroup FROM MemberT WHERE idUser=" + idUser;
        try (Connection con = DriverManager.getConnection(dbUrl, DB_USER, DB_PASS);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(req);) {
            while (rs.next()) {
                listGroup.add(getGroup(rs.getLong("idGroup")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listGroup;
    }

    public NavigableSet<Group> getAllGroup() {
        NavigableSet<Group> listGroup = new TreeSet<>();
        String req = "SELECT idGroup FROM GroupT";
        try (Connection con = DriverManager.getConnection(dbUrl, DB_USER, DB_PASS);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(req);) {
            while (rs.next()) {
                listGroup.add(getGroup(rs.getLong("idGroup")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listGroup;
    }

    public Group getGroup(long idGroup) {
        String req = "SELECT * FROM GroupT WHERE id=" + idGroup;
        // id name user thread
        try (Connection con = DriverManager.getConnection(dbUrl, DB_USER, DB_PASS);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(req);) {
            rs.next();
            String name = rs.getString("nameGroup");
            int numberOfMember = rs.getInt("numberOfMember");
            Group group = new Group(idGroup, name, numberOfMember);
            for (User u : getUserGroup(idGroup))
                group.addUser(u);
            for (Thread t : getThreadGroup(idGroup))
                group.addThread(t);
            return group;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    private NavigableSet<User> getUserGroup(long idGroup) {
        String req = "SELECT idUser FROM MemberT WHERE idGroup=" + idGroup;
        NavigableSet<User> listUser = new TreeSet<>();
        try (Connection con = DriverManager.getConnection(dbUrl, DB_USER, DB_PASS);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(req);) {
            while (rs.next())
                listUser.add(getUser(rs.getLong("idUser")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listUser;
    }

    private int getNumberOfMember(long idGroup){
        String req = "SELECT numberOfMember FROM GroupT WHERE idGroup=" + idGroup;
        try (Connection con = DriverManager.getConnection(dbUrl, DB_USER, DB_PASS);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(req);) {
            rs.next();
            return rs.getInt("numberOfMember");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private NavigableSet<Thread> getThreadGroup(long idGroup) {
        String req = "SELECT idThread FROM ThreadT WHERE idGroup=" + idGroup;
        NavigableSet<Thread> listThread = new TreeSet<>();
        try (Connection con = DriverManager.getConnection(dbUrl, DB_USER, DB_PASS);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(req);) {
            while (rs.next())
                listThread.add(getThread(rs.getLong("idGroup")));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listThread;
    }

    private TreeMap<Group, TreeSet<Thread>> getThreadOwner(long idUser){
        //Permet de récuperer tout les threads créé par un user
        String req = "SELECT idThread FROM ThreadT WHERE idUser=" + idUser;
        TreeMap<Group, TreeSet<Thread>> groupTreeSetTreeMap = new TreeMap<>();
        TreeSet<Thread> threadTreeSet;
        try (Connection con = DriverManager.getConnection(dbUrl, DB_USER, DB_PASS);
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(req);) {
            while (rs.next()) {
                Thread thread = getThread(rs.getLong("idGroup"));
                Group group = getGroup(thread.getIdGroup());
                if(!groupTreeSetTreeMap.containsKey(group)){
                    threadTreeSet = new TreeSet<>();
                }
                else{
                    threadTreeSet = groupTreeSetTreeMap.get(group);
                }
                threadTreeSet.add(thread);
                groupTreeSetTreeMap.put(group, threadTreeSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return groupTreeSetTreeMap;
    }

    public boolean uniqueIdUser(long idUser) {
        return getUser(idUser) == null;
    }

    public boolean uniqueIdGroup(long idGroup) {
        return getGroup(idGroup) == null;
    }

    public boolean uniqueIdThread(long idThread) {
        return getThread(idThread) == null;
    }

    public boolean uniqueIdMessage(long idMessage) {
        return getMessage(idMessage) == null;
    }

    public boolean uniqueIdRead(long idUser, long idThread) {
        return getRead(idUser, idThread) == null;
    }

    public static void main(String[] args) {
        new DatabaseInteraction();
    }

}