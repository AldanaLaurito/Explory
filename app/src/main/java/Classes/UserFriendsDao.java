package Classes;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

public interface UserFriendsDao {
    @Query("SELECT * FROM User_Friends")
    List<UserFriends> getAll();

    @Query("SELECT * FROM User_Friends WHERE ufid IN (:ufids)")
    List<UserFriends> loadAllByIds(int[] ufids);

    @Query("SELECT * FROM User_Friends WHERE ufid LIKE :ufid" +
            " LIMIT 1")
    UserFriends findById(int ufid);

    @Insert
    void insertAll(UserFriends... ufs);

    @Delete
    void delete(UserFriends usufCont);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUsers(UserFriends... ufs);

    @Insert
    public void insertBothUsers(UserFriends uf1, UserFriends uf2);
}
