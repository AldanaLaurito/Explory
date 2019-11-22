package Classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "cards",foreignKeys = @ForeignKey(entity = CollectorAlbum.class,
        parentColumns = "caid",
        childColumns = "idAlbum", onDelete = ForeignKey.SET_NULL),indices = {@Index(value = {"cid"},
        unique = true)})
public class Card {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "cid")
    private int id;

    private int idAlbum;

    private String qrCode;

    private String name;

    private String description;

    private String link;


    public Card(int idAlbum, String qrCode, String name, String description, String link) {
        this.idAlbum = idAlbum;
        this.qrCode = qrCode;
        this.name = name;
        this.description = description;
        this.link = link;
    }

    public Card(int idAlbum, String name, String description, String link) {
        this.idAlbum = idAlbum;
        this.name = name;
        this.description = description;
        this.link = link;
    }

    public Card() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAlbum() {
        return idAlbum;
    }

    public void setIdAlbum(int idAlbum) {
        this.idAlbum = idAlbum;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }


}
