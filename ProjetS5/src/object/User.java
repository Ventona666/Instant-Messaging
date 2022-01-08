package object;

import server.ServerInterface;

import java.io.Serializable;
import java.util.Date;
import java.util.NavigableSet;
import java.util.TreeMap;
import java.util.TreeSet;

public abstract class User implements Serializable, Comparable<User> {
    private final long id;
    private final String firstName;
    private final String lastName;
    private final String username;
    protected ServerInterface stubServer;
    private NavigableSet<Group> groupSet = new TreeSet<>();
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

    public NavigableSet<Group> getGroupSet() {
        return groupSet;
    }

    public void setGroupSet(NavigableSet<Group> groupSet) {
        this.groupSet = groupSet;
    }

    public boolean addGroup(Group groupToAdd) {
        return groupSet.add(groupToAdd);
    }

    public void addThread(Thread thread) {
        threadCreated.add(thread);
    }

    public TreeMap<Group, TreeSet<Thread>> getAllThread() {
        TreeMap<Group, TreeSet<Thread>> threadList = new TreeMap<>();
        TreeSet<Thread> tempList;
        for (Group g : groupSet) {
            if (!threadList.containsKey(g)) {
                tempList = new TreeSet<>();
                threadList.put(g, tempList);
            } else
                tempList = threadList.get(g);
            tempList.addAll(g.getThreadSet());
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
            Message message = new Message(new Date(), this, text, thread);
            stubServer.sendMessage(message);
            System.err.println("Message envoyé au serveur avec succès");
        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi du message : " + e);
            e.printStackTrace();
        }
    }

    public void newThread(String title, Group group) {
        try {
            Thread thread = new Thread(title, this, group);
            stubServer.newThread(thread);
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
        int lastNameDiff = lastName.compareTo(user.lastName);
        if (lastNameDiff != 0)
            return lastNameDiff;
        int firstNameDiff = firstName.compareTo(user.firstName);
        if (firstNameDiff != 0)
            return firstNameDiff;
        if (id < user.id)
            return -1;
        else if (id > user.id)
            return 1;
        else
            return 0;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof User) {
            User u = (User) obj;
            return u.getId() == getId() && u.getFirstName() == getFirstName() && u.getLastName() == getLastName();
        }
        return false;
    }

    @Override
    public int hashCode() {
        return 31 * getFirstName().hashCode() * getLastName().hashCode() * ((Long) getId()).intValue();
    }

    @Override
    public String toString() {
        return lastName + " " + firstName;
    }
}