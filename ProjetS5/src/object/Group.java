package object;

import java.util.HashSet;

public class Group implements Comparable<Group>{
    private int id;
    private HashSet<User> userSet = new HashSet<>();
    private HashSet<Thread> threadSet = new HashSet<>();

    public Group(int id){
        this.id = id;
    }
    public int getId() {
        return id;
    }

    public HashSet<User> getUserSet() {
        return userSet;
    }

    public boolean addUser(User userToAdd){
        return userSet.add(userToAdd);
    }

    public HashSet<Thread> getThreadSet() {
        return threadSet;
    }

    @Override
    public int compareTo(Group group) {
        if(id < group.getId()){
            return -1;
        }
        return 1;
    }
}