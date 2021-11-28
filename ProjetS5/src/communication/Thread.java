package communication;

public class Thread {
    private final int id;
    private String[] messageList;

    public Thread(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String[] getMessageList() {
        return messageList;
    }

    public void setMessageList(String[] messageList) {
        this.messageList = messageList;
    }
}
