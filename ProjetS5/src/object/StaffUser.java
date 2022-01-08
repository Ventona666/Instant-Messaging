package object;

import utils.IdGenerator;
import utils.UsernameGenerator;

public class StaffUser extends User {
    public StaffUser(long id, String firstName, String lastName, String username) {
        super(id, firstName, lastName, username);
    }

    public StaffUser(String firstName, String lastName){
        super(IdGenerator.idGenerator(firstName, lastName), firstName, lastName, UsernameGenerator.usernameGenerator(firstName, lastName));
    }

    public Group createGroup(String name){
        Group group = new Group(name);
        try{
            stubServer.createGroup(group);
            System.err.println("Le groupe a été créé avec succès");
        } catch (Exception e){
            System.err.println("Le groupe n'a pas pu être créé : " + e);
            e.printStackTrace();
        }
        return group;
    }

    public void addToGroup(User user, Group group) {
        try {
            stubServer.addToGroup(user, group);
            System.err.println(user.toString() + " ajouté à " + group.toString());
        } catch (Exception e) {
            System.err.println(user.toString() + " n'a pas été ajouté à " + group.toString() + " : " + e);
            e.printStackTrace();
        }
    }

    public void removeFromGroup(User user, Group group) {
        try {
            stubServer.removeFromGroup(user, group);
            System.err.println(user.toString() + " supprimé de " + group.toString());
        } catch (Exception e) {
            System.err.println(user.toString() + " n'a pas été supprimé de " + group.toString() + " : " + e);
            e.printStackTrace();
        }
    }
}