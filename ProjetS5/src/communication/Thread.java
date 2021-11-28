package communication;

import java.util.NavigableSet;
import java.util.TreeSet;

public class Thread {
    private final int id;
    private NavigableSet<Message> messageList = new TreeSet<>();

    public Thread(int id){
        this.id = id;
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
}
