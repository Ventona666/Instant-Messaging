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
    private TreeMap<Group, TreeSet<Thread>> groupThreadSetMap = new TreeMap<>();

    public User(long id, String firstName, String lastName, String username){
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

    public void setGroupThreadSetMap(TreeMap<Group, TreeSet<Thread>> groupThreadSetMap){
        this.groupThreadSetMap = groupThreadSetMap;
    }

    public NavigableSet<Group> getGroupSet() {
        return groupSet;
    }

    public void setGroupSet(NavigableSet<Group> groupSet) {
        this.groupSet = groupSet;
    }

    public void addGroup(Group groupToAdd){
        groupSet.add(groupToAdd);
    }

    public TreeMap<Group, TreeSet<Thread>> getAllThread(){
        TreeMap<Group, TreeSet<Thread>> groupThreadTreeMap = new TreeMap<>();
        TreeSet<Thread> threadTreeSet = new TreeSet<>();;
        for(Group g : groupSet){
            threadTreeSet.addAll(g.getThreadSet());
            groupThreadTreeMap.put(g, threadTreeSet);
            threadTreeSet = new TreeSet<>();
        }
        groupThreadTreeMap.putAll(groupThreadSetMap);
        return groupThreadTreeMap;
    }

    public void sendMessage(String text, Thread thread){
        try {
            Message message = new Message(new Date(), this.id, text, thread.getId());
            stubServer.sendMessage(message);
            System.err.println("Message envoyé au serveur avec succès");
        }
        catch (Exception e){
            System.err.println("Erreur lors de l'envoi du message : " + e);
            e.printStackTrace();
        }
    }

    public void newThread(String title, Group group){
        try{
            Thread thread = new Thread(title, this, group.getId());
            stubServer.newThread(thread);

            if(!group.getUserSet().contains(this)){
                TreeSet<Thread> threadTreeSet = groupThreadSetMap.get(group);
                threadTreeSet.add(thread);
                groupThreadSetMap.put(group, threadTreeSet);
            }

            System.err.println("Démarrage d'un nouveau thread réussi");
        }
        catch (Exception e){
            System.err.println("Erreur lors de la création thread : " + e);
            e.printStackTrace();
        }
    }

    public void closeThread(int idThread){
        try{
            stubServer.closeThread(idThread);
            System.err.println("Fermeture du thread " + idThread + " réussi");
        }
        catch (Exception e){
            System.err.println("Erreur lors de la fermeture du thread " + idThread + " : " + e);
            e.printStackTrace();
        }
    }

    @Override
    public int compareTo(User user) {
        if(id < user.getId()){
            return -1;
        }
        else{
            return 1;
        }
    }

    @Override
    public int hashCode(){
        return 31 * firstName.hashCode() * lastName.hashCode();
    }
}