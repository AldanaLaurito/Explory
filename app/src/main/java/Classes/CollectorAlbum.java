package Classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Collector_Album")
public class CollectorAlbum {

    @PrimaryKey
    @ColumnInfo(name = "rowid")
    private int id;

    private String image;

    private String imageInactive;

    private String imageCompleted;

    public CollectorAlbum() {
    }

    public CollectorAlbum(String image, String imageInactive, String imageCompleted) {
        this.image = image;
        this.imageInactive = imageInactive;
        this.imageCompleted = imageCompleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImageInactive() {
        return imageInactive;
    }

    public void setImageInactive(String imageInactive) {
        this.imageInactive = imageInactive;
    }

    public String getImageCompleted() {
        return imageCompleted;
    }

    public void setImageCompleted(String imageCompleted) {
        this.imageCompleted = imageCompleted;
    }
}
