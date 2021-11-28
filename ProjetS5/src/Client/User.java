package Client;

import java.util.NavigableSet;
import java.util.TreeSet;

public class User implements Comparable<User> {
    private int id;
    private final String firstName;
    private final String lastName;
    private NavigableSet<Group> groupList = new TreeSet<Group>();

    public User(String firstName, String lastName){
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

    public NavigableSet<Group> getGroupList() {
        return groupList;
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
}
