package Classes;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

public interface UserContactsDao {
    @Query("SELECT * FROM User_Contacts")
    List<UserContacts> getAll();

    @Query("SELECT * FROM User_Contacts WHERE usContid IN (:usContids)")
    List<UserContacts> loadAllByIds(int[] usContids);

    @Query("SELECT * FROM User_Contacts WHERE usContid LIKE :usContid" +
            " LIMIT 1")
    UserContacts findById(int usContid);

    @Insert
    void insertAll(UserContacts... usConts);

    @Delete
    void delete(UserContacts usCont);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insertUsers(UserContacts... usConts);

    @Insert
    public void insertBothUsers(UserContacts usCont1, UserContacts usCont2);
}
