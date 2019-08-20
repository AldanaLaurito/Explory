package Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import Classes.User;
import Classes.UserDao;

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}