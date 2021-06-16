package wonderful.workouts.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import wonderful.workouts.database.daos.UserDao;
import wonderful.workouts.database.entities.User;

@Database(
    version = 1,
    // Here we list out all the entities we'll be using
    entities = {
        User.class,
    }
)
@TypeConverters({ Converters.class })
abstract class AppDatabase extends RoomDatabase {
    // Here we list out all the DAO and the methods will be built by the @Database wrapper for us
    abstract public UserDao getUserDao();

    // This will be the static instance of the database so we only ever have one connection. This is the singleton pattern
    // The volatile keyword ensure that if this is changed it'll be updated across threads
    volatile private static AppDatabase INSTANCE = null;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            // Yeah the synchronized block ensures that when we create the database that can only ever be done once and
            // to wait until its done if we happen to have race conditions. Read more here: https://www.baeldung.com/java-synchronized
            synchronized (AppDatabase.class) {
                // Create our database and set the static INSTANCE of it so we can return it on subsequent calls
                INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "main_database").build();

            }
        }

        // Return the instance
        return INSTANCE;
    }
}
