package object;

import java.util.Arrays;
import java.util.HashSet;
import java.util.NavigableSet;
import java.util.TreeSet;

public class Thread implements Comparable<Thread> {
    private final int id;
    private final String title;
    private HashSet<Group> groupSet = new HashSet<>();
    private NavigableSet<Message> messageList = new TreeSet<>();

    public Thread(int id, String title, Group... groups) {
        this.id = id;
        this.title = title;
        this.groupSet.addAll(Arrays.asList(groups));
    }

    public int getId() {
        return id;
    }

    public HashSet<Group> getGroupSet() {
        return groupSet;
    }

    public NavigableSet<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(NavigableSet<Message> messageList) {
        this.messageList = messageList;
    }

    public void addMessageList(Message message) {
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
    public int hashCode(){
        return 31 * id * title.hashCode();
    }
}
