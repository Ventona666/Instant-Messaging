package object;

import java.util.NavigableSet;
import java.util.TreeSet;

public class Group implements Comparable<Group> {
    private final long id;
    private String name;
    private NavigableSet<User> userList = new TreeSet<>();
    private NavigableSet<Thread> threadList = new TreeSet<>();

    public Group(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NavigableSet<User> getUserList() {
        return userList;
    }

    public boolean addUser(User userToAdd) {
        return userList.add(userToAdd);
    }

    public NavigableSet<Thread> getThreadList() {
        return threadList;
    }

    public void addThread(Thread thread) {
        threadList.add(thread);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Group) {
            Group group = (Group) obj;
            return name.equals(group.getName());
        }
        return false;
    }

    @Override
    public int compareTo(Group group) {
        return name.compareTo(group.getName());
    }
}