package Classes;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "collector_albums", indices = {@Index(value = {"caid","name"},
        unique = true)})
public class CollectorAlbum {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "caid")
    private int id;

    private String name;

    private String imageInactive;

    private String imageCompleted;

    public CollectorAlbum() {
    }

    public CollectorAlbum(String name) {
        this.name = name;
    }

    public CollectorAlbum(String name, String imageInactive, String imageCompleted) {
        this.name = name;
        this.imageInactive = imageInactive;
        this.imageCompleted = imageCompleted;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
