package Classes;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

public interface CollectorAlbumDao {
    @Query("SELECT * FROM Collector_Albums")
    List<CollectorAlbum> getAll();

    @Query("SELECT * FROM Collector_Albums WHERE caid IN (:caIds)")
    List<CollectorAlbum> loadAllByIds(int[] caIds);

    @Query("SELECT * FROM Collector_Albums WHERE name LIKE :name" +
            " LIMIT 1")
    CollectorAlbum findByName(String name);

    @Insert
    void insertAll(CollectorAlbum... cas);

    @Delete
    void delete(CollectorAlbum ca);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUsers(CollectorAlbum... cas);

    @Insert
    public void insertBothUsers(CollectorAlbum ca1, CollectorAlbum ca2);
}
