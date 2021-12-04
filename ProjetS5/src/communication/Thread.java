package communication;

import Client.Group;

import java.util.NavigableSet;
import java.util.TreeSet;

public class Thread implements Comparable<Thread>{
    private final int id;
    private NavigableSet<Message> messageList = new TreeSet<>();
    private Group group;

    public Thread(int id, Group group){
        this.id = id;
        this.group = group;
    }

    public int getId() {
        return id;
    }

    public NavigableSet<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(NavigableSet<Message> messageList) {
        this.messageList = messageList;
    }

    @Override
    public int compareTo(Thread thread) {
        if(id < thread.getId()){
            return -1;
        }
        return 1;
    }
}
