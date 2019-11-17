package Classes;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

@Dao
public interface CardDao {
    @Query("SELECT * FROM cards")
    List<Card> getAll();

    @Query("SELECT * FROM cards WHERE cid IN (:cardIds)")
    List<Card> loadAllByIds(int[] cardIds);

    @Query("SELECT * FROM cards WHERE name LIKE :name" +
            " LIMIT 1")
    Card findByName(String name);

    @Query("SELECT users.* FROM users INNER JOIN user_collections ON "+
            "users.uid=user_collections.idUser WHERE "+
            "user_collections.idCard=:idCard")
    List<User> getUsersForCard(final int idCard);

    @Insert
    void insertAll(Card... cards);

    @Delete
    void delete(Card card);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUsers(Card... cards);

    @Insert
    public void insertBothUsers(Card card1, Card card2);
}
