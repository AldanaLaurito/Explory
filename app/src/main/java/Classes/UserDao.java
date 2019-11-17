package Classes;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    List<User> getAll();

    @Query("SELECT * FROM users WHERE uid IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM users WHERE firstName LIKE :first AND " +
            "lastName LIKE :last LIMIT 1")
    User findByName(String first, String last);

    @Query("SELECT * FROM users WHERE nickname LIKE :nick " +
            "LIMIT 1")
    User findByNickname(String nick);

    @Query("SELECT * FROM users WHERE mail LIKE :mail " +
            "LIMIT 1")
    User findByMail(String mail);

    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM Users WHERE birthday BETWEEN :from AND :to")
    List<User> findUsersBornBetweenDates(Date from, Date to);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUsers(User... users);

    @Insert
    public void insertBothUsers(User user1, User user2);
}