package object;

import utils.IdGenerator;
import utils.UsernameGenerator;

public class StaffUser extends User {
    public String genUsername(String firstName, String lastName) {
        char[] threeChars = new char[3];
        lastName.getChars(0, 3, threeChars, 0);
        return firstName.toLowerCase() + threeChars.toString().toUpperCase();
    }

    public StaffUser(long id, String firstName, String lastName, String username) {
        super(id, firstName, lastName, username);
    }

    public StaffUser(String firstName, String lastName) {
        super(IdGenerator.idGenerator(firstName, lastName), firstName, lastName,
                UsernameGenerator.usernameGenerator(firstName, lastName));
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