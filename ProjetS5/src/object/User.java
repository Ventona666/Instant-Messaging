package object;

import server.ServerInterface;
import utils.IdGenerator;

import java.io.Serializable;
import java.util.Date;
import java.util.NavigableSet;
import java.util.TreeMap;
import java.util.TreeSet;

public abstract class User implements Serializable, Comparable<User> {
    private final long id;
    private final String firstName;
    private final String lastName;
    private String username;
    protected ServerInterface stubServer;
    private NavigableSet<Group> groupList = new TreeSet<>();
    private NavigableSet<Thread> threadCreated = new TreeSet<>();

    public User(long id, String firstName, String lastName, String username) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setStubServer(ServerInterface stubServer) {
        this.stubServer = stubServer;
    }

    public NavigableSet<Group> getGroupList() {
        return groupList;
    }

    public void setGroupList(NavigableSet<Group> groupList) {
        this.groupList = groupList;
    }

    public void addGroup(Group groupToAdd) {
        groupList.add(groupToAdd);
    }

    public void addThread(Thread thread) {
        threadCreated.add(thread);
    }

    public NavigableSet<Thread> getThread() {
        return threadCreated;
    }

    public TreeMap<Group, TreeSet<Thread>> getAllThread() {
        TreeMap<Group, TreeSet<Thread>> threadList = new TreeMap<>();
        TreeSet<Thread> tempList;
        for (Group g : groupList) {
            if (!threadList.containsKey(g)) {
                tempList = new TreeSet<>();
                threadList.put(g, tempList);
            } else
                tempList = threadList.get(g);
            tempList.addAll(g.getThreadList());
        }
        for (Thread t : threadCreated) {
            if (threadList.containsKey(t.getGroup()))
                tempList = threadList.get(t.getGroup());
            else {
                tempList = new TreeSet<>();
                threadList.put(t.getGroup(), tempList);
            }
            tempList.add(t);
        }
        return threadList;
    }

    public void sendMessage(String text, Thread thread) {
        try {
            Message message = new Message(IdGenerator.idGenerator(text), new Date(), this, text, thread);
            stubServer.sendMessage(message);
            System.err.println("Message envoyé au serveur avec succès");
        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi du message : " + e);
            e.printStackTrace();
        }
    }

    public void newThread(String title, Group group) {
        try {
            stubServer.newThread(title, group);
            System.err.println("Démarrage d'un nouveau thread réussi");
        } catch (Exception e) {
            System.err.println("Erreur lors de la création thread : " + e);
            e.printStackTrace();
        }
    }

    public void closeThread(int idThread) {
        try {
            stubServer.closeThread(idThread);
            System.err.println("Fermeture du thread " + idThread + " réussi");
        } catch (Exception e) {
            System.err.println("Erreur lors de la fermeture du thread " + idThread + " : " + e);
            e.printStackTrace();
        }
    }

    @Override
    public int compareTo(User user) {
        if (id < user.getId()) {
            return -1;
        } else {
            return 1;
        }
    }

    @Override
    public int hashCode() {
        return 31 * firstName.hashCode() * lastName.hashCode();
    }
}