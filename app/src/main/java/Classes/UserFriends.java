package Classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "User_Friends")
public class UserFriends {

    @PrimaryKey
    @ColumnInfo(name = "rowid")
    private int id;

    private int idUser1;

    private int idUser2;

    private int friendLevel;

    public UserFriends() {
    }

    public UserFriends(int idUser1, int idUser2, int friendLevel) {
        this.idUser1 = idUser1;
        this.idUser2 = idUser2;
        this.friendLevel = friendLevel;
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

    public int getFriendLevel() {
        return friendLevel;
    }

    public void setFriendLevel(int friendLevel) {
        this.friendLevel = friendLevel;
    }
}
