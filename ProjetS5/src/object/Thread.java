package object;

import java.util.NavigableSet;
import java.util.TreeSet;
import static utils.IdGenerator.idGenerator;

public class Thread implements Comparable<Thread> {
    private final long id;
    private final String title;
    private final User owner;
    private final long idGroup;
    private boolean isClose = false;
    private NavigableSet<Message> messageList = new TreeSet<>();

    public Thread(String title, User owner, long group) {
        this.id = idGenerator(title);
        this.title = title;
        this.owner = owner;
        this.idGroup = group;
    }

    public Thread(long id, String title, User owner, long group) {
        this.id = id;
        this.title = title;
        this.owner = owner;
        this.idGroup = group;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public User getOwner() {
        return owner;
    }

    public long getIdGroup() {
        return idGroup;
    }

    public NavigableSet<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(NavigableSet<Message> messageList) {
        this.messageList = messageList;
    }

    public void addMessage(Message message) {
        this.messageList.add(message);
    }

    public boolean isClose() {
        return isClose;
    }

    public void closeThread() {
        isClose = true;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Thread) {
            Thread t = (Thread) obj;
            return getId() == t.getId();
        }
        return false;
    }

    @Override
    public int compareTo(Thread thread) {
        if (id < thread.getId()) {
            return -1;
        }
        return 1;
    }

    @Override
    public int hashCode() {
        return 31 * title.hashCode();
    }

    @Override
    public String toString() {
        return title;
    }
}