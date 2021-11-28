package communication;

import java.io.Serializable;
import java.util.Date;

public class Message implements Serializable {
    private final int id;
    private final Date date;
    private final int idSender;
    private final String text;

    public Message(int id, Date date, int idSender, String text){
        this.id = id;
        this.date = date;
        this.idSender = idSender;
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

    public int getIdSender() {
        return idSender;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", date=" + date +
                ", idSender=" + idSender +
                ", text='" + text + '\'' +
                '}';
    }
}
