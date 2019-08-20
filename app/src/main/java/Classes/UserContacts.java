package Classes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.*;

@Entity(tableName = "User_Contacts")
public class UserContacts {
    @PrimaryKey
    private int id;

    private int idUser1;

    private int idUser2;

    private String description;

    private String message;

    private String card;

    private GregorianCalendar dateTime;
}
