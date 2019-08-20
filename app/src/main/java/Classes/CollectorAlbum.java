package Classes;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Collector_Album")
public class CollectorAlbum {

    @PrimaryKey
    private int id;

    private String image;

    private String imageInactive;

    private String imageCompleted;
}
