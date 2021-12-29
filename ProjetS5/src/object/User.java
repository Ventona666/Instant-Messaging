package object;

import server.ServerInterface;

import java.io.Serializable;
import java.util.NavigableSet;
import java.util.TreeSet;

public abstract class User implements Serializable, Comparable<User> {
    private final int id;
    private final String firstName;
    private final String lastName;
    protected ServerInterface stubServer;
    private NavigableSet<Group> groupList = new TreeSet<>();

    public User(int id, String firstName, String lastName){
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setStubServer(ServerInterface stubServer) {
        this.stubServer = stubServer;
    }

    public NavigableSet<Group> getGroupList() {
        return groupList;
    }

    public boolean addGroup(Group groupToAdd){
        return groupList.add(groupToAdd);
    }

    public void sendMessage(String text, int idThread){
        try {
            stubServer.sendMessage(this, text, idThread);
            System.err.println("Message envoyé au serveur avec succès");
        }
        catch (Exception e){
            System.err.println("Erreur lors de l'envoi du message : " + e);
            e.printStackTrace();
        }
    }

    public void newThread(String title, Group group){
        try{
            stubServer.newThread(title, group);
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
        return 31 * id * firstName.hashCode() * lastName.hashCode();
    }
}