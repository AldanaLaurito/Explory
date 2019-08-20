package Classes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "User_Friends")
public class UserFriends {

    @PrimaryKey
    private int id;

    private int idUser1;

    private int idUser2;

    private int friendLevel;
}
