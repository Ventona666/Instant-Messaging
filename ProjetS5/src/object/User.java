package object;

import java.io.Serializable;
import java.util.NavigableSet;
import java.util.TreeSet;

public class User implements Serializable, Comparable<User> {
    private int id;
    private final String firstName;
    private final String lastName;
    private String ipAddress;
    private NavigableSet<Group> groupList = new TreeSet<>();

    public User(String firstName, String lastName, String ipAddress){
        this.firstName = firstName;
        this.lastName = lastName;
        this.ipAddress = ipAddress;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getIpAddress() {
        return ipAddress;
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
}