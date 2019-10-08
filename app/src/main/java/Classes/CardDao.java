package Classes;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.Date;
import java.util.List;

public interface CardDao {
    @Query("SELECT * FROM Cards")
    List<Card> getAll();

    @Query("SELECT * FROM Cards WHERE cid IN (:cardIds)")
    List<Card> loadAllByIds(int[] cardIds);

    @Query("SELECT * FROM Cards WHERE name LIKE :name" +
            " LIMIT 1")
    Card findByName(String name);

    @Insert
    void insertAll(Card... cards);

    @Delete
    void delete(Card card);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUsers(Card... cards);

    @Insert
    public void insertBothUsers(Card card1, Card card2);
}
