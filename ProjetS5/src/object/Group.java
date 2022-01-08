package object;

import utils.IdGenerator;

import java.util.NavigableSet;
import java.util.TreeSet;

public class Group implements Comparable<Group> {
    private final long id;
    private String name;
    private NavigableSet<User> userSet = new TreeSet<>();
    private NavigableSet<Thread> threadSet = new TreeSet<>();
    private int numberOfMember = 0;

    public Group(long id, String name, int numberOfMember) {
        this.id = id;
        this.name = name;
        this.numberOfMember = numberOfMember;
    }

    public Group(String name){
        this.id = IdGenerator.idGenerator(name);
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumberOfMember() {
        return numberOfMember;
    }

    public void setName(String name) {
        this.name = name;
    }

    public NavigableSet<User> getUserSet() {
        return userSet;
    }

    public boolean addUser(User userToAdd) {
        return userSet.add(userToAdd);
    }

    public boolean removeUser(User userToDelete){
        return userSet.remove(userToDelete);
    }
    public NavigableSet<Thread> getThreadSet() {
        return threadSet;
    }

    public void addThread(Thread thread) {
        threadSet.add(thread);
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