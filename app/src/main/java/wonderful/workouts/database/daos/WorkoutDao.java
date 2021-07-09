package wonderful.workouts.database.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

import wonderful.workouts.database.entities.Movement;
import wonderful.workouts.database.entities.Workout;
import wonderful.workouts.database.joiners.WorkoutWithHistory;
import wonderful.workouts.database.joiners.WorkoutWithMovements;

@Dao
public interface WorkoutDao {
    @Insert
    long insert(Workout workout);

    @Update
    void update(Workout workout);

    @Delete
    void delete(Workout workout);

    @Query("SELECT w.* FROM workouts AS w LEFT JOIN users AS u on u.userId = w.workoutId WHERE w.userId = :userId")
    List<Workout> getWorkoutsForUser(int userId);

    @Transaction
    @Query("SELECT * FROM workouts WHERE workoutId = :workoutId")
    List<WorkoutWithMovements> getWorkoutMovements(int workoutId);

    @Transaction
    @Query("SELECT * FROM workouts WHERE workoutId = :workoutId")
    List<WorkoutWithHistory> getWorkoutHistory(int workoutId);

    // Get's a workout whose name matches a string for a given user
    @Transaction
    @Query("SELECT w.* FROM workouts AS w LEFT JOIN users AS u ON u.userId = w.userId WHERE u.userId = :userId AND w.name LIKE :workoutName")
    Workout lookupWorkout(int userId, String workoutName);

    @Transaction
    public default Workout lookupOrCreateWorkout(int userId, String workoutName) {
        Workout lookup = lookupWorkout(userId, workoutName);
        if (lookup != null) {
            return lookup;
        }

        Workout newWorkout = new Workout();
        newWorkout.userId = userId;
        newWorkout.name = workoutName;
        newWorkout.workoutId = (int) insert(newWorkout);

        return newWorkout;
    }
}
