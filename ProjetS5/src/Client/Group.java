package Client;

import com.sun.source.tree.Tree;

import java.util.NavigableSet;
import java.util.TreeSet;

public class Group implements Comparable<Group>{
    private int id;
    private NavigableSet<User> userList = new TreeSet<>();
    private NavigableSet<Thread> threadList = new TreeSet<>();

    public Group(int id){
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public NavigableSet<User> getUserList() {
        return userList;
    }

    public boolean addUser(User userToAdd){
        return userList.add(userToAdd);
    }

    @Override
    public int compareTo(Group group) {
        if(id < group.getId()){
            return -1;
        }
        return 1;
    }
}