package Database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import Classes.Card;
import Classes.CardDao;
import Classes.CollectorAlbum;
import Classes.CollectorAlbumDao;
import Classes.Converters;
import Classes.User;
import Classes.UserCollection;
import Classes.UserCollectionDao;
import Classes.UserDao;

@Database(entities = {User.class, Card.class, UserCollection.class, CollectorAlbum.class}, version = 3)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract CardDao cardDao();
    public abstract UserCollectionDao userCollectionDao();
    public abstract CollectorAlbumDao collectorAlbumDao();
}

