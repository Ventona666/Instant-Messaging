package object;

import java.util.NavigableSet;
import java.util.TreeSet;

public class Thread implements Comparable<Thread> {
    private final int id;
    private Group group;
    private final String title;
    private NavigableSet<Message> messageList = new TreeSet<>();

    public Thread(int id, Group group, String title) {
        this.id = id;
        this.group = group;
        this.title = title;
    }

    public int getId() {
        return id;
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
