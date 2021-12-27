package object;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable, Comparable<Message> {
    private final int id;
    private final Date date;
    private final User sender;
    private final String text;
    private final Thread thread;
    private int numberOfReceptions = 0;
    private int numberOfReads = 0;

    public Message(int id, Date date, User sender, String text, Thread thread) {
        this.id = id;
        this.date = date;
        this.sender = sender;
        this.text = text;
        this.thread = thread;
    }

    public Thread getThread() {
        return thread;
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

    public int getNumberOfReceptions() {
        return numberOfReceptions;
    }

    public int getNumberOfReads() {
        return numberOfReads;
    }

    public void incrementNumberOfReceptions(){
        numberOfReceptions++;
    }

    public void incrementNumberOfReads(){
        numberOfReads++;
    }

    @Override
    public int compareTo(Message message) {
        if (date.before(message.getDate())) {
            return -1;
        } else {
            return 1;
        }
    }
}
