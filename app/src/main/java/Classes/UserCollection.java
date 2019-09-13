package Classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "User_Collections")
public class UserCollection {

    @PrimaryKey
    @ColumnInfo(name = "ucid")
    private int id;

    private int idUser;

    private int idCard;

    public UserCollection() {
    }

    public UserCollection(int idUser, int idCard) {
        this.idUser = idUser;
        this.idCard = idCard;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdCard() {
        return idCard;
    }

    public void setIdCard(int idCard) {
        this.idCard = idCard;
    }
}
