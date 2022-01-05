package object;

import java.util.Arrays;
import java.util.HashSet;
import java.util.NavigableSet;
import java.util.TreeSet;

public class Thread implements Comparable<Thread> {
    private final long id;
    private final String title;
    private final User owner;
    private final Group group;
    private NavigableSet<Message> messageList = new TreeSet<>();

    public Thread(long id, String title, User owner, Group group) {
        this.id = id;
        this.title = title;
        this.owner = owner;
        this.group = group;
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

    public Group getGroup() {
        return group;
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
}