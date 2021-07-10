package wonderful.workouts.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import wonderful.workouts.database.daos.MeasurementDao;
import wonderful.workouts.database.daos.MovementDao;
import wonderful.workouts.database.daos.UserDao;
import wonderful.workouts.database.daos.WorkoutDao;
import wonderful.workouts.database.daos.WorkoutHistoryDao;
import wonderful.workouts.database.daos.WorkoutMovementCrossRefDao;
import wonderful.workouts.database.daos.WorkoutMovementHistoryDao;
import wonderful.workouts.database.entities.Measurement;
import wonderful.workouts.database.entities.Movement;
import wonderful.workouts.database.entities.User;
import wonderful.workouts.database.entities.Workout;
import wonderful.workouts.database.entities.WorkoutHistory;
import wonderful.workouts.database.entities.WorkoutMovementCrossRef;
import wonderful.workouts.database.entities.WorkoutMovementHistory;

@Database(
    version = 7,
    // Here we list out all the entities we'll be using
    entities = {
        Measurement.class,
        Movement.class,
        User.class,
        Workout.class,
        WorkoutHistory.class,
        WorkoutMovementCrossRef.class,
        WorkoutMovementHistory.class,
    },
    exportSchema = false
)
// The TypeConverts help us convert the Java date type to something the database can handle and back
@TypeConverters({ Converters.class })
public abstract class AppDatabase extends RoomDatabase {
    // Here we list out all the DAO and the methods will be built by the @Database wrapper for us
    abstract public MeasurementDao getMeasurementDao();
    abstract public MovementDao getMovementDao();
    abstract public UserDao getUserDao();
    abstract public WorkoutDao getWorkoutDao();
    abstract public WorkoutHistoryDao getWorkoutHistoryDao();
    abstract public WorkoutMovementCrossRefDao getWorkoutMovementCrossRefDao();
    abstract public WorkoutMovementHistoryDao getWorkoutMovementHistoryDao();

    // This will be the static instance of the database so we only ever have one connection. This follows the singleton pattern
    private static AppDatabase INSTANCE = null;

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            // Yeah the synchronized block ensures that when we create the database that can only ever be done once and
            // to wait until its done if we happen to have race conditions. Read more here: https://www.baeldung.com/java-synchronized
            synchronized (AppDatabase.class) {
                // Create our database and set the static INSTANCE of it so we can return it on subsequent calls
                INSTANCE = Room
                    .databaseBuilder(context.getApplicationContext(), AppDatabase.class, "main_database")
                    .fallbackToDestructiveMigration()
                    .build();

            }
        }

        // Return the instance
        return INSTANCE;
    }

}
