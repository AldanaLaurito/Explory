package Classes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Card")
public class Card {

    @PrimaryKey
    private int id;

    private int idAlbum;

    private String qrCode;

    private String image;

    private String description;

    private String link;
}
