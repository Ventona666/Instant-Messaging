package Client;

import com.sun.source.tree.Tree;

import java.util.NavigableSet;
import java.util.TreeSet;

public class Group implements Comparable<Group>{
    private int id;
    private NavigableSet<User> userList = new TreeSet<>();

    public int getId() {
        return id;
    }

    public NavigableSet<User> getUserList() {
        return userList;
    }

    @Override
    public int compareTo(Group group) {
        if(id < group.getId()){
            return -1;
        }
        return 1;
    }
}
