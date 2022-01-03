package object;


import java.util.HashSet;
import static utils.IdGenerator.idGenerator;

public class Group implements Comparable<Group> {
    private final long id;
    private String name;
    private HashSet<User> userSet = new HashSet<>();
    private HashSet<Thread> threadSet = new HashSet<>();

    public Group(String name) {
        this.id = idGenerator(name);
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

    public HashSet<User> getUserSet() {
        return userSet;
    }

    public boolean addUser(User userToAdd) {
        return userSet.add(userToAdd);
    }

    public boolean removeUser(User userToRemove){
        return userSet.remove(userToRemove);
    }

    public boolean addThread(Thread threadToAdd){
        return threadSet.add(threadToAdd);
    }

    public HashSet<Thread> getThreadSet() {
        return threadSet;
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