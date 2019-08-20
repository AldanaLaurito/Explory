package Classes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "User_Collection")
public class UserCollection {

    @PrimaryKey
    private int id;

    private int idUser;

    private int idCard;
}
