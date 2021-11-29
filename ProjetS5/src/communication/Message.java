package communication;

import Client.User;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable, Comparable<Message> {
    private final int id;
    private final Date date;
    private final User sender;
    private final String text;

    public Message(int id, Date date, User sender, String text){
        this.id = id;
        this.date = date;
        this.sender = sender;
        this.text = text;
    }

    public int getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getText() {
        return text;
    }

    public User getSender() {
        return sender;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", date=" + date +
                ", idSender=" + sender +
                ", text='" + text + '\'' +
                '}';
    }

    @Override
    public int compareTo(Message message) {
        if(date.before(message.getDate())){
            return -1;
        }
        else{
            return 1;
        }
    }
}
