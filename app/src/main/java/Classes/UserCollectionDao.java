package Classes;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserCollectionDao {
    @Query("SELECT * FROM user_collections")
    List<UserCollection> getAll();

    @Query("SELECT * FROM user_collections WHERE idUser LIKE :idUser" +
            " LIMIT 1")
    UserCollection findByUserId(int idUser);

    @Query("SELECT * FROM user_collections WHERE idCard LIKE :idCard" +
            " LIMIT 1")
    UserCollection findByCardId(int idCard);

    @Query("SELECT users.* FROM users INNER JOIN user_collections ON "+
            "users.uid=user_collections.idUser WHERE "+
            "user_collections.idCard=:idCard")
            List<User> getUsersForCard(final int idCard);

    @Query("SELECT cards.* FROM cards INNER JOIN user_collections ON "+
            "cards.cid=user_collections.idCard WHERE "+
            "user_collections.idUser=:idUser")
            List<Card> getCardsForUser(final int idUser);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertAll(UserCollection... ucs);

    @Delete
    void delete(UserCollection uc);
}
