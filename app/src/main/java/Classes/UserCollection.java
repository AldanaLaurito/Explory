package Classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_collections",primaryKeys = { "idUser", "idCard" }, indices = {@Index(value = {"idUser","idCard"})},
        foreignKeys = {
                @ForeignKey(entity = User.class,
                        parentColumns = "uid",
                        childColumns = "idUser",
                        onDelete = ForeignKey.CASCADE),
                @ForeignKey(entity = Card.class,
                        parentColumns = "cid",
                        childColumns = "idCard",
                        onDelete = ForeignKey.CASCADE)
        })
public class UserCollection {

    private int idUser;

    private int idCard;

    public UserCollection() {
    }

    public UserCollection(int idUser, int idCard) {
        this.idUser = idUser;
        this.idCard = idCard;
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
