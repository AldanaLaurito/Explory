package Classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.*;

@Entity(tableName = "User_Contacts")
public class UserContacts {
    @PrimaryKey
    @ColumnInfo(name = "usContid")
    private int id;

    private int idUser1;

    private int idUser2;

    private String description;

    private String message;

    private String card;

    private GregorianCalendar dateTime;

    public UserContacts() {
    }

    public UserContacts(int idUser1, int idUser2, String description, String card, GregorianCalendar dateTime) {
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
        this.description = description;
        this.card = card;
        this.dateTime = dateTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser1() {
        return idUser1;
    }

    public void setIdUser1(int idUser1) {
        this.idUser1 = idUser1;
    }

    public int getIdUser2() {
        return idUser2;
    }

    public void setIdUser2(int idUser2) {
        this.idUser2 = idUser2;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCard() {
        return card;
    }

    public void setCard(String card) {
        this.card = card;
    }

    public GregorianCalendar getDateTime() {
        return dateTime;
    }

    public void setDateTime(GregorianCalendar dateTime) {
        this.dateTime = dateTime;
    }
}
