package wonderful.workouts.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import wonderful.workouts.database.entities.User;
import wonderful.workouts.database.joiners.UserWithMeasurements;
import wonderful.workouts.database.joiners.UserWithWorkouts;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    long insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1;")
    User getFromUsername(String username);

    @Query("SELECT * FROM users WHERE username = :username AND password = :password LIMIT 1;")
    User getUser(String username, String password);

    @Query("SELECT * FROM users WHERE userId = :userId LIMIT 1;")
    User getUser(int userId);

    @Transaction
    @Query("SELECT * FROM users WHERE userId = :userId")
    UserWithWorkouts getUserWorkouts(int userId);

    @Transaction
    @Query("SELECT * FROM users WHERE userId = :userId")
    UserWithMeasurements getUserMeasurements(int userId);
}
