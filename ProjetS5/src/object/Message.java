package object;

import utils.IdGenerator;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable, Comparable<Message> {
    private final long id;
    private final Date date;
    private final long idSender;
    private final String text;
    private final long idThread;
    private int numberOfReceptions = 0;
    private int numberOfReads = 0;
    private String nameSender;
    private MessageStatus messageStatus = MessageStatus.DEFAULT;

    public Message(Date date, long idSender, String text, long thread) {
        this.id = IdGenerator.idGenerator(text);
        this.date = date;
        this.idSender = idSender;
        this.text = text;
        this.idThread = thread;
    }

    public Message(long id, Date date, long idSender, String text, long thread){
        this.id = id;
        this.date = date;
        this.idSender = idSender;
        this.text = text;
        this.idThread = thread;
    }

    public long getIdThread() {
        return idThread;
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

    public long getIdSender() {
        return idSender;
    }

    public void setNameSender(String nameSender) {
        this.nameSender = nameSender;
    }


    @Override
    public String toString() {
        return nameSender + "\n\n" + text + "\n\n" + date;
    }

    public MessageStatus getMessageStatus() {
        return messageStatus;
    }

    public void setMessageStatus(MessageStatus messageStatus) {
        this.messageStatus = messageStatus;
    }

    public void incrementNumberOfReceptions(int numberOfMember) {
        numberOfReceptions++;
        if (numberOfReceptions >= numberOfMember) {
            this.messageStatus = MessageStatus.RECEIVED_BY_ALL_USERS;
        }
    }

    public void incrementNumberOfReads(int numberOfMember) {
        numberOfReads++;
        if (numberOfReads >= numberOfMember) {
            this.messageStatus = MessageStatus.READ_BY_ALL_USERS;
        }
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

    @Override
    public int compareTo(Message message) {
        return date.compareTo(message.getDate());
    }


}
