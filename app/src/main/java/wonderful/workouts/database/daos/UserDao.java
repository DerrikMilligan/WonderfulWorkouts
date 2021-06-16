package wonderful.workouts.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import wonderful.workouts.database.entities.User;
import wonderful.workouts.database.joiners.UserWithMeasurements;
import wonderful.workouts.database.joiners.UserWithWorkouts;

@Dao
public interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    Completable insert(User user);

    @Update
    Completable update(User user);

    @Delete
    Completable delete(User user);

    @Query("SELECT * FROM users WHERE username = :username LIMIT 1;")
    Maybe<User> getFromUsername(String username);

    @Transaction
    @Query("SELECT * FROM users WHERE id = :userId")
    Maybe<List<UserWithWorkouts>> getUserWorkouts(int userId);

    @Transaction
    @Query("SELECT * FROM users WHERE id = :userId")
    Maybe<List<UserWithMeasurements>> getUserMeasurements(int userId);
}
