package Classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Fts4;
import androidx.room.PrimaryKey;

import java.util.Date;

@Fts4(languageId = "lid")
@Entity(tableName = "Users")
public class User {
    @PrimaryKey
    private int id;

    @ColumnInfo(name = "firstName")
    private String firstName;

    @ColumnInfo(name = "lastName")
    private String lastName;

    private String nickname;

    private String password;

    private String mail;

    private Date birthdate;

    private int level;

    private int points;

    private String qrCode;
}