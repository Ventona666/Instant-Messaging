package object;

import java.io.Serializable;
import java.util.NavigableSet;
import java.util.TreeSet;

public class User implements Serializable, Comparable<User> {
    private int id;
    private final String firstName;
    private final String lastName;
    private NavigableSet<Group> groupList = new TreeSet<>();

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

    public boolean addGroup(Group groupToAdd){
        return groupList.add(groupToAdd);
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