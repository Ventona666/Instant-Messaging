package object;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable, Comparable<Message> {
    private final long id;
    private final Date date;
    private final User sender;
    private final String text;
    private final Thread thread;
    private int numberOfReceptions = 0;
    private int numberOfReads = 0;
    private MessageStatus messageStatus = MessageStatus.DEFAULT;

    public Message(long id, Date date, User sender, String text, Thread thread) {
        this.id = id;
        this.date = date;
        this.sender = sender;
        this.text = text;
        this.thread = thread;
    }

    public Thread getThread() {
        return thread;
    }

    public long getId() {
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
        return text;
    }

    public MessageStatus getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(MessageStatus status) {
        this.messageStatus = status;
    }

    public int getNumberOfReceptions() {
        return numberOfReceptions;
    }

    public void setNumberOfReceptions(int numberOfReceptions) {
        this.numberOfReceptions = numberOfReceptions;
    }

    public int getNumberOfReads() {
        return numberOfReads;
    }

    public void setNumberOfReads(int numberOfReads) {
        this.numberOfReads = numberOfReads;
    }

    public void incrementNumberOfReceptions() {
        numberOfReceptions++;
        if (numberOfReceptions == this.thread.getGroup().getUserList().size() + 1) {
            this.messageStatus = MessageStatus.RECEIVED_BY_ALL_USERS;
        }
    }

    public void incrementNumberOfReads() {
        numberOfReads++;
        if (numberOfReads == this.thread.getGroup().getUserList().size() + 1) {
            this.messageStatus = MessageStatus.READ_BY_ALL_USERS;
        }
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