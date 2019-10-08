package Classes;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

public interface UserCollectionDao {
    @Query("SELECT * FROM User_Collections")
    List<UserCollection> getAll();

    @Query("SELECT * FROM User_Collections WHERE ucid IN (:ucIds)")
    List<UserCollection> loadAllByIds(int[] ucIds);

    @Query("SELECT * FROM User_Collections WHERE ucid LIKE :ucId" +
            " LIMIT 1")
    UserCollection findById(int ucId);

    @Insert
    void insertAll(UserCollection... ucs);

    @Delete
    void delete(UserCollection uc);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUsers(UserCollection... ucs);

    @Insert
    public void insertBothUsers(UserCollection uc1, UserCollection uc2);
}
